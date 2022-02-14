package com.github.zljtt.underwaterbiome.biomes;

import com.github.zljtt.underwaterbiome.registries.FeatureRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class MangroveForest {

	public static OceanBiome get() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(10);
		float depth = 1.45F;
		float scale = 0.001F;
		float temperature = 2.0F;
		float downfall = 1F;
		int waterColor = 0x12DDCD;
		int fogColor = 0x12DDCD;
		int skyColor = 0x12DDCD;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.TROPICAL_FISH, 6, 2, 4);
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 10, 2, 5);
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.SALMON, 10, 2, 5);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityType.PUFFERFISH, 5, 1, 3);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.01F, 0.2F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.4F, 0.1F, 40);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
				FeatureRegistry.MANGROVE_GRASS.configured(IFeatureConfig.NONE).squared()
						.decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(FeatureSpread.of(3, 13)))));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.MANGROVE_TREE.configured(IFeatureConfig.NONE).squared()
						.decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(FeatureSpread.of(4, 8)))));
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.01F);

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
