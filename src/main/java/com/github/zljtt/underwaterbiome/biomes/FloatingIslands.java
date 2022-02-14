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
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class FloatingIslands {

	public static OceanBiome get() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(10);
		float depth = -1F;
		float scale = 0.3F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x53A1FC;
		int fogColor = 0x53A1FC;
		int skyColor = 0x53A1FC;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.COD, 10, 3, 6);
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.SALMON, 5, 3, 6);
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityRegistry.RAY, 3, 1, 2);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.STONE.defaultBlockState(),
						Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState())));
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.1F, 0.05F);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.01F);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
				FeatureRegistry.FLOATING_ISLAND.configured(IFeatureConfig.NONE).chance(2)
						.decorated(Placement.COUNT.configured(new FeatureSpreadConfig(FeatureSpread.of(1, 2)))));
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
