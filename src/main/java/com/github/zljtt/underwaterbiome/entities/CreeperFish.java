package com.github.zljtt.underwaterbiome.entities;

import java.util.Collection;
import java.util.EnumSet;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CreeperFish extends AbstractGroupFishEntity {

	private static final DataParameter<Integer> STATE0 = EntityDataManager.defineId(CreeperFish.class, DataSerializers.INT);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.defineId(CreeperFish.class, DataSerializers.BOOLEAN);

	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 2;

	public CreeperFish(EntityType<? extends AbstractGroupFishEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingMalus(PathNodeType.WATER, 0);

	}

	public static Builder getLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(Items.GUNPOWDER).apply(SetCount.setCount(RandomValueRange.between(0, 2)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
//				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
//						.add(ItemLootEntry.lootTableItem(ItemRegistry.CREEPER_FISH.get())
//								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FindWaterGoal(this));
		this.goalSelector.addGoal(2, new SwellGoal(this));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));

		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, false));

		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 2, true, true, this::canAttack));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 10.0D).add(Attributes.ARMOR_TOUGHNESS, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 0.5).add(Attributes.FOLLOW_RANGE, 10)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1F).add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	@Override
	public void travel(Vector3d vector) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(vector);
		}

	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new SwimmerPathNavigator(this, world);
	}

	@Override
	public boolean canAttack(LivingEntity entity) {
		if (entity != null) {
			return entity.isInWater();
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hurt(DamageSource source, float amount) {
		Boolean can = super.hurt(source, amount);
		if (!this.dead && this.isAlive() && !this.removed) {
			this.entityData.set(IGNITED, true);
		}
		return can;
	}

	@OnlyIn(Dist.CLIENT)
	public float getCreeperFlashIntensity(float partialTicks) {
		return MathHelper.lerp(partialTicks, (float) this.lastActiveTime, (float) this.timeSinceIgnited)
				/ (float) (this.fuseTime - 2);
	}

	@Override
	protected ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		return ActionResultType.FAIL;
	}

	public void tick() {
		if (this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;

			int i = this.getCreeperState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
			}

			if (this.entityData.get(IGNITED)) {
				this.timeSinceIgnited += 2;
			} else {
				this.timeSinceIgnited += i;
			}
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

		super.tick();
	}

	private void explode() {
		if (!this.level.isClientSide) {
			Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)
					? Explosion.Mode.DESTROY
					: Explosion.Mode.NONE;
			float f = 1.0F;
			this.dead = true;
			this.level.explode(this, this.position().x, this.position().y, this.position().z, (float) this.explosionRadius * f,
					explosion$mode);
			this.remove();
			this.spawnLingeringCloud();
		}

	}

	private void spawnLingeringCloud() {
		Collection<EffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.position().x,
					this.position().y, this.position().z);
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity
					.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

			for (EffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
			}

			this.level.addFreshEntity(areaeffectcloudentity);
		}

	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("Fuse", (short) this.fuseTime);
		compound.putByte("ExplosionRadius", (byte) this.explosionRadius);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Fuse", 99)) {
			this.fuseTime = compound.getShort("Fuse");
		}

		if (compound.contains("ExplosionRadius", 99)) {
			this.explosionRadius = compound.getByte("ExplosionRadius");
		}
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STATE0, -1);
		this.entityData.define(IGNITED, false);

	}

	public int getCreeperState() {
		return this.entityData.get(STATE0);
	}

	public void setCreeperState(int state) {
		this.entityData.set(STATE0, state);
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return null;
	}

	static class SwellGoal extends Goal {
		private final CreeperFish creeper;
		private LivingEntity target;

		public SwellGoal(CreeperFish p_i1655_1_) {
			this.creeper = p_i1655_1_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.creeper.getTarget();
			return this.creeper.getCreeperState() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 9.0D;
		}

		public void start() {
			this.creeper.getNavigation().stop();
			this.target = this.creeper.getTarget();
		}

		public void stop() {
			this.target = null;
		}

		public void tick() {
			if (this.target == null) {
				this.creeper.setCreeperState(-1);
			} else if (this.creeper.distanceToSqr(this.target) > 49.0D) {
				this.creeper.setCreeperState(-1);
			} else if (!this.creeper.getSensing().canSee(this.target)) {
				this.creeper.setCreeperState(-1);
			} else {
				this.creeper.setCreeperState(1);
			}
		}
	}
}
