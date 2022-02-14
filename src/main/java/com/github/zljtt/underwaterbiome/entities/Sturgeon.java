package com.github.zljtt.underwaterbiome.entities;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Sturgeon extends FishBase {

	public Sturgeon(EntityType<? extends FishBase> entityType, World world) {
		super(entityType, world);
	}

	public static Builder getLootTableBuilder() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1, 2))
						.add(ItemLootEntry.lootTableItem(Items.SALMON)
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.FISH_SKIN.get())
								.apply(SetCount.setCount(RandomValueRange.between(1, 2)))
								.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0, 1)))));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FishMeleeAttackGoal(this, 1D, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 5.0D).add(Attributes.ARMOR_TOUGHNESS, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ATTACK_KNOCKBACK, 0.1).add(Attributes.FOLLOW_RANGE, 10)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.2F).add(Attributes.MAX_HEALTH, 25).add(Attributes.MOVEMENT_SPEED, 0.2D);
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}
}
