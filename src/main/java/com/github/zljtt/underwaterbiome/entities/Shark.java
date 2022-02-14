package com.github.zljtt.underwaterbiome.entities;

import com.github.zljtt.underwaterbiome.datagen.AllLootTables;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Shark extends FishBase {

	private static final DataParameter<Integer> HUNGER = EntityDataManager.defineId(Shark.class, DataSerializers.INT);

	public Shark(EntityType<? extends FishBase> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FishMeleeAttackGoal(this, 1.4D, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, false, true, this::canAttack));
		this.targetSelector.addGoal(3, new EatFishGoal<>(this, AbstractFishEntity.class, false));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 4.0D).add(Attributes.ARMOR_TOUGHNESS, 2.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ATTACK_KNOCKBACK, 1).add(Attributes.FOLLOW_RANGE, 30)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.9F).add(Attributes.MAX_HEALTH, 30).add(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	public static Builder getLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.SHARK_FIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(1, 4)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FISH_SKIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(3, 7)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		this.setHunger(this.getHunger() + 1);
		if (!this.level.isClientSide && this.isAlive() && !this.dead
				&& net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
			for (ItemEntity itementity : this.level.getEntitiesOfClass(ItemEntity.class,
					this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D))) {
				if (!itementity.removed && !itementity.getItem().isEmpty() && itementity.isPickable()
						&& (itementity.getItem().getItem() == Items.PUFFERFISH
								|| itementity.getItem().getItem() == Items.TROPICAL_FISH
								|| itementity.getItem().getItem() == Items.COD
								|| itementity.getItem().getItem() == Items.SALMON)) {
					this.setHunger(0);
					itementity.remove();
				}
			}
		}
		this.level.getProfiler().pop();

		super.tick();
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}

	public int getHunger() {
		return this.entityData.get(HUNGER);
	}

	public void setHunger(int hunger) {
		this.entityData.set(HUNGER, hunger);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HUNGER, 0);
	}

	static class EatFishGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		private final Shark shark;

		public EatFishGoal(MobEntity mob, Class<T> c, boolean canAttack) {
			super(mob, c, canAttack);
			this.shark = (Shark) mob;
		}

		@Override
		public boolean canUse() {
			return shark.getEntityData().get(HUNGER) > 600 && super.canUse();
		}
	}
}
