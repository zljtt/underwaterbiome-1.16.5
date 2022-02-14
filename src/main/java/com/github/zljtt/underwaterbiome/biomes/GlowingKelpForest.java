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
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class GlowingKelpForest {

	public static OceanBiome getNormal() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(4);
		float depth = 0.0F;
		float scale = 0.1F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x0AAA8E;
		int fogColor = 0x0AAA8E;
		int skyColor = 0x0AAA8E;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 5, 3, 6);
		biome.addSpawner(EntityClassification.CREATURE, EntityRegistry.CONCH, 5, 1, 1);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addDecorativeKelps(biomeGenerationSettingBuilder, 20);
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.2F, 0.1F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.3F, 0.1F, 30);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.GLOWING_KELP.configured(IFeatureConfig.NONE).squared().count(4)
						.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
		OceanBiomeFeature.addTopLayerLime(biomeGenerationSettingBuilder, 20);
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

	public static OceanBiome getEdge() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(3);
		float depth = 0.0F;
		float scale = 0.3F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x0AAA8E;
		int fogColor = 0x0AAA8E;
		int skyColor = 0x0AAA8E;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 5, 3, 6);
		biome.addSpawner(EntityClassification.CREATURE, EntityRegistry.CONCH, 10, 1, 1);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addDecorativeKelps(biomeGenerationSettingBuilder, 30);
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.1F, 0.2F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.3F, 0.1F, 30);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.GLOWING_KELP.configured(IFeatureConfig.NONE).squared().count(4)
						.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.01F);
		OceanBiomeFeature.addTopLayerLime(biomeGenerationSettingBuilder, 20);
		// build
		return biome.setBiome(() -> new Biome.Builder().precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.OCEAN)
				.depth(depth).scale(scale).temperature(temperature).downfall(downfall)
				.specialEffects(new BiomeAmbience.Builder().waterColor(waterColor).waterFogColor(fogColor).fogColor(fogColor)
						.skyColor(skyColor).ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0D))
						.build())
				.mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings(biomeGenerationSettingBuilder.build())
				.build());
	}

	public static OceanBiome getAdvanced() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(3);
		float depth = -0.5F;
		float scale = 0.4F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x0AAA70;
		int fogColor = 0x0AAA70;
		int skyColor = 0x0AAA70;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 5, 3, 6);
		biome.addSpawner(EntityClassification.CREATURE, EntityRegistry.CONCH, 3, 1, 1);
		biome.addSpawner(EntityClassification.CREATURE, EntityRegistry.CONCH_SEA_GRASS, 8, 1, 1);
		biome.addSpawner(EntityClassification.CREATURE, EntityRegistry.CREEPER_FISH, 3, 1, 1);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addDecorativeKelps(biomeGenerationSettingBuilder, 5);
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.2F, 0.1F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.6F, 0.1F, 50);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		OceanBiomeFeature.addTopLayerLime(biomeGenerationSettingBuilder, 30);
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.01F);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.GLOWING_KELP_TALL.configured(IFeatureConfig.NONE).squared().count(3)
						.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.GLOWING_KELP.configured(IFeatureConfig.NONE).squared().count(2)
						.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
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
