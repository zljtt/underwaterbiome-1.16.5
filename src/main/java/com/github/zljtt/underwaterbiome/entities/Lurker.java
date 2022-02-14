package com.github.zljtt.underwaterbiome.entities;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Lurker extends FishBase {

	public Lurker(EntityType<? extends FishBase> entityType, World world) {
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3D, false));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 16, false, true, this::canAttack));
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 0.0D).add(Attributes.ARMOR_TOUGHNESS, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.ATTACK_KNOCKBACK, 0.2).add(Attributes.FOLLOW_RANGE, 16)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0F).add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PUFFER_FISH_DEATH;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}
}
