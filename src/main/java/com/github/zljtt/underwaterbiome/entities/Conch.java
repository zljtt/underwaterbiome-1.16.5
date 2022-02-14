package com.github.zljtt.underwaterbiome.entities;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class Conch extends CreatureEntity {

	public Conch(EntityType<? extends CreatureEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingMalus(PathNodeType.WATER, 0);

	}

	public static Builder getLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(Items.NAUTILUS_SHELL).setWeight(1)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1))))
						.add(ItemLootEntry.lootTableItem(Items.KELP).setWeight(3)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.SILT.get())
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))
								.when(RandomChance.randomChance(0.5F))));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FindWaterGoal(this));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.5));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));

		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 2, true, true, this::canAttack));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 10.0D).add(Attributes.ARMOR_TOUGHNESS, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 0.5).add(Attributes.FOLLOW_RANGE, 10)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1F).add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.8D);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void travel(Vector3d p_213352_1_) {
		if (!this.level.isClientSide && this.isInWater() && this.getTarget() != null && this.getTarget().isInWater()) {
			this.moveRelative(0.01F, p_213352_1_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		} else {
			super.travel(p_213352_1_);
		}

	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new ConchPathNavigator(this, world);
	}

	@Override
	public void tick() {
		if (this.jumping) {
			this.level.addParticle(ParticleTypes.BUBBLE,
					this.position().x + this.random.nextDouble() / 2 - this.random.nextDouble() / 2, this.position().y,
					this.position().z + this.random.nextDouble() / 2 - this.random.nextDouble() / 2, 0.0D, 0.0D, 0.0D);
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

	public class ConchPathNavigator extends SwimmerPathNavigator {
		public ConchPathNavigator(Conch entityIn, World worldIn) {
			super(entityIn, worldIn);
		}

		@Override
		public void tick() {
			++this.tick;
			if (this.canUpdatePath()) {
				this.followThePath();
				;
			}

			if (!this.isDone()) {
				if (this.canUpdatePath()) {
					this.followThePath();
				} else if (this.path != null && !this.path.isDone()) {
					Vector3d vector3d = this.path.getNextEntityPos(this.mob);
					if (MathHelper.floor(this.mob.getX()) == MathHelper.floor(vector3d.x)
							&& MathHelper.floor(this.mob.getY()) == MathHelper.floor(vector3d.y)
							&& MathHelper.floor(this.mob.getZ()) == MathHelper.floor(vector3d.z)) {
						this.path.advance();
					}
				}

				DebugPacketSender.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
				if (!this.isDone()) {
					Vector3d vector3d1 = this.path.getNextEntityPos(this.mob);
					this.mob.getMoveControl()
							.setWantedPosition(vector3d1.x, this.level.getHeight(Heightmap.Type.OCEAN_FLOOR,
									(int) Math.round(vector3d1.x), (int) Math.round(vector3d1.z)), vector3d1.z,
									this.speedModifier);
				}
			}

		}

	}
}
