package com.github.zljtt.underwaterbiome.biomes;

import com.github.zljtt.underwaterbiome.registries.EntityRegistry;
import com.github.zljtt.underwaterbiome.registries.FeatureRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BrokenCanyon {

	public static OceanBiome get() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(10);
		float depth = 0.5F;
		float scale = 0.2F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x125C89;
		int fogColor = 0x125C89;
		int skyColor = 0x125C89;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 5, 3, 6);
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.SALMON, 5, 3, 6);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityRegistry.SHARK, 1, 1, 1);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.STONE.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.35F, 0.05F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.1F, 0.1F, 40);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		OceanBiomeFeature.addUndergroundBlock(biomeGenerationSettingBuilder, FeatureRegistry.DEPTH_GRASS, 10, 0.1F);
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.01F);
		OceanBiomeFeature.addGlowshroom(biomeGenerationSettingBuilder, 1, 0.001F);
		OceanBiomeFeature.addReef(biomeGenerationSettingBuilder, FeatureSpread.of(1, 4));

		// build
		return biome.setBiome(() -> new Biome.Builder().precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.OCEAN)
				.depth(depth).scale(scale).temperature(temperature).downfall(downfall)
				.specialEffects(new BiomeAmbience.Builder().waterColor(waterColor).waterFogColor(fogColor).fogColor(fogColor)
						.skyColor(skyColor).ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0D))
						.build())
				.mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings(biomeGenerationSettingBuilder.build())
				.build());
	}
}
