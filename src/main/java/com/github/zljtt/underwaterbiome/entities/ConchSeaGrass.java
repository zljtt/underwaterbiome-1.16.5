package com.github.zljtt.underwaterbiome.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ConchSeaGrass extends Conch {

	public ConchSeaGrass(EntityType<? extends CreatureEntity> entityType, World world) {
		super(entityType, world);
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ARMOR, 10.0D).add(Attributes.ARMOR_TOUGHNESS, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ATTACK_KNOCKBACK, 0.5).add(Attributes.FOLLOW_RANGE, 10)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1F).add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.6D);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (target != null && target instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) target;
			entity.addEffect(new EffectInstance(Effects.POISON, 60));
		}
		return super.doHurtTarget(target);
	}

}
