package com.github.zljtt.underwaterbiome.entities;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class LavaFish extends FishBase {

	private static final DataParameter<Boolean> LAVA = EntityDataManager.defineId(LavaFish.class, DataSerializers.BOOLEAN);
	private AttributeModifier lavaForm = new AttributeModifier("lava_form", -1, AttributeModifier.Operation.ADDITION);

	public LavaFish(EntityType<? extends FishBase> entityType, World world) {
		super(entityType, world);
	}

	public static Builder getLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(Blocks.OBSIDIAN)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))
								.when(RandomChance.randomChance(0.33F))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FIRE_ELEMENT.get())
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, false));
		this.goalSelector.addGoal(3, new GoToMagmaBlockGoal(this, 1.5D));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 2.0D).add(Attributes.ARMOR_TOUGHNESS, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.ATTACK_KNOCKBACK, 0).add(Attributes.FOLLOW_RANGE, 16)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1F).add(Attributes.MAX_HEALTH, 30).add(Attributes.MOVEMENT_SPEED, 0.05D);
	}

	@Override
	public void tick() {
		if (this.getHealth() <= this.getMaxHealth() / 2F) {
			this.setLavaForm(true);
		}
		if (this.isLavaForm()) {
			if (!this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(lavaForm)) {
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(lavaForm);
			}

			if (this.level.getBlockState(this.blockPosition()).getBlock() == Blocks.BUBBLE_COLUMN) {
				this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 1));
				this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 1));
				this.wasTouchingWater = true;
			}

		} else {
			if (this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(lavaForm)) {
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(lavaForm);
			}
		}
		super.tick();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() instanceof PlayerEntity) {
			if (!(((PlayerEntity) source.getEntity()).getMainHandItem().getItem() instanceof PickaxeItem) && !this.isLavaForm()) {
				return false;
			}
		}
		if (source.isFire()) {
			return false;
		}
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) target).getMobType());
			f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
		}

		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setSecondsOnFire(i * 4);
		}
		DamageSource damage = DamageSource.mobAttack(this);
		boolean flag = target.hurt(this.isLavaForm() ? damage.setIsFire() : damage, this.isLavaForm() ? f * 5f : f);
		if (flag) {
			if (f1 > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).knockback(f1 * 0.5F, (double) MathHelper.sin(this.yRot * ((float) Math.PI / 180F)),
						(double) (-MathHelper.cos(this.yRot * ((float) Math.PI / 180F))));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}

			if (target instanceof PlayerEntity) {
				PlayerEntity playerentity = (PlayerEntity) target;
				ItemStack useItem = playerentity.isUsingItem() ? playerentity.getUseItem() : ItemStack.EMPTY;
				if (!this.getMainHandItem().isEmpty() && !useItem.isEmpty() && this.getMainHandItem().getItem() instanceof AxeItem
						&& useItem.getItem() == Items.SHIELD) {
					float g = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
					if (this.random.nextFloat() < g) {
						playerentity.getCooldowns().addCooldown(Items.SHIELD, 100);
						this.level.broadcastEntityEvent(playerentity, (byte) 30);
					}
				}
			}

			this.doEnchantDamageEffects(this, target);
			this.setLastHurtMob(target);
		}

		return flag;
	}

	static class GoToMagmaBlockGoal extends MoveToBlockGoal {

		public GoToMagmaBlockGoal(LavaFish entity, double speed) {
			super(entity, speed, 50);
		}

		@Override
		public boolean canUse() {
			if (mob instanceof LavaFish) {
				return ((LavaFish) mob).isLavaForm() && super.canUse();
			}
			return false;
		}

		@Override
		protected boolean isValidTarget(IWorldReader reader, BlockPos pos) {
			return reader.getBlockState(pos).is(Blocks.MAGMA_BLOCK);
		}
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}

	public boolean isLavaForm() {
		return this.entityData.get(LAVA);
	}

	public void setLavaForm(boolean lava) {
		this.entityData.set(LAVA, lava);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(LAVA, false);
	}

}
