package com.github.zljtt.underwaterbiome.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FishBase extends WaterMobEntity {

	public FishBase(EntityType<? extends WaterMobEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new MoveHelperController(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FindWaterGoal(this));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 40));

	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new SwimmerPathNavigator(this, world);
	}

	@Override
	public void tick() {
		if (!this.isInWater() && this.onGround && this.verticalCollision) {
			this.setDeltaMovement(this.getDeltaMovement().add((double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F),
					(double) 0.4F, (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			this.onGround = false;
//			this.isAirBorne = true;
		}
		super.tick();
	}

	@Override
	public boolean canAttack(LivingEntity entity) {
		if (entity != null) {
			return entity.isInWater();
		} else {
			return false;
		}
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
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

	static class MoveHelperController extends MovementController {
		private final FishBase fish;

		MoveHelperController(FishBase fishBase) {
			super(fishBase);
			this.fish = fishBase;
		}

		public void tick() {
			if (this.fish.isEyeInFluid(FluidTags.WATER)) {
				this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
			}

			if (this.operation == MovementController.Action.MOVE_TO && !this.fish.getNavigation().isDone()) {
				float f = (float) (this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.fish.setSpeed(MathHelper.lerp(0.125F, this.fish.getSpeed(), f));
				double d0 = this.wantedX - this.fish.getX();
				double d1 = this.wantedY - this.fish.getY();
				double d2 = this.wantedZ - this.fish.getZ();
				if (d1 != 0.0D) {
					double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
					this.fish.setDeltaMovement(
							this.fish.getDeltaMovement().add(0.0D, (double) this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
				}

				if (d0 != 0.0D || d2 != 0.0D) {
					float f1 = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.fish.yRot = this.rotlerp(this.fish.yRot, f1, 90.0F);
					this.fish.yBodyRot = this.fish.yRot;
				}

			} else {
				this.fish.setSpeed(0.0F);
			}
		}
	}

	static class FishMeleeAttackGoal extends MeleeAttackGoal {

		public FishMeleeAttackGoal(CreatureEntity creature, double speed, boolean seen) {
			super(creature, speed, seen);
		}

		@Override
		protected double getAttackReachSqr(LivingEntity target) {
			return (double) (this.mob.getBbWidth() * 4 + target.getBbWidth());
		}
	}

}
