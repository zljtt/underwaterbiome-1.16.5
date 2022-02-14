package com.github.zljtt.underwaterbiome.entities;

import java.util.Random;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.network.NetworkHandler;
import com.github.zljtt.underwaterbiome.network.messages.RaySize;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Ray extends FishBase {
	private static final DataParameter<Float> SIZE = EntityDataManager.defineId(Ray.class, DataSerializers.FLOAT);

	public Ray(EntityType<? extends FishBase> entityType, World world) {
		super(entityType, world);
	}

//	@SubscribeEvent
//	public static void decideSize(EntityEvent.Size event) {
//		if (event.getEntity() instanceof Ray) {
//			Ray ray = (Ray) event.getEntity();
//			float size = ray.getSize();
//			if (!ray.level.isClientSide && ray.level instanceof ServerWorld) {
//				int i = 0;
//				int id = 0;
//				for(Entity entity : ((ServerWorld) ray.level).getAllEntities()) {
//					if (entity.getUUID().equals(ray.getUUID())) {
//						id = i;
//					}
//					i++;
//				}
//				NetworkHandler.sendToDimension(new RaySize(size, id), (ServerWorld) ray.level, UnderwaterBiome.WATERWORLD);
//			}
////			System.out.println("decide " + ((Ray) event.getEntity()).getSize());
//			event.setNewSize(event.getOldSize().scale(((Ray) event.getEntity()).getSize()), true);
//		}
//	}
//
//	@Override
//	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_,
//			SpawnReason p_213386_3_, ILivingEntityData p_213386_4_, CompoundNBT p_213386_5_) {
////		System.out.println("finalize " + this.getSize());
//		this.refreshDimensions();
//		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
//	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FishMeleeAttackGoal(this, 1D, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 2.0D).add(Attributes.ARMOR_TOUGHNESS, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.ATTACK_KNOCKBACK, 0.5).add(Attributes.FOLLOW_RANGE, 16)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.3F).add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.17D);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putFloat("Size", getSize());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Size", 99)) {
			this.setSize(compound.getFloat("Size"));
		}
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PUFFER_FISH_DEATH;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}

	public Float getSize() {
		return this.entityData.get(SIZE);
	}

	public void setSize(Float s) {
		this.entityData.set(SIZE, s);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SIZE, 1F);
	}

	public static Builder getSmallLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1, 2))
						.add(ItemLootEntry.lootTableItem(Items.SALMON)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1))))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.CHAMELEON_SKIN.get())
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FISH_SKIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(1, 2)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	public static Builder getMiddleLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1, 3))
						.add(ItemLootEntry.lootTableItem(Items.SALMON)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1))))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.CHAMELEON_SKIN.get())
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FISH_SKIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(1, 3)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	public static Builder getLargeLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(RandomValueRange.between(2, 3))
						.add(ItemLootEntry.lootTableItem(Items.SALMON)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1))))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.CHAMELEON_SKIN.get())
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FISH_SKIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(2, 3)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

}
