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

public class LavaBasin {

	public static OceanBiome get() {
		OceanBiome biome = new OceanBiome();

		// setting
		biome.setWeight(10);
		float depth = -1.5F;
		float scale = 0F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x65599A;
		int fogColor = 0x65599A;
		int skyColor = 0x65599A;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.SALMON, 5, 1, 2);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityType.SQUID, 3, 1, 2);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityRegistry.LAVA_FISH, 4, 1, 1);

		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.OBSIDIAN.defaultBlockState(),
						Blocks.OBSIDIAN.defaultBlockState(), Blocks.OBSIDIAN.defaultBlockState())));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
				FeatureRegistry.LAVA_BLOCKS.configured(IFeatureConfig.NONE).squared()
						.decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(FeatureSpread.of(8, 16)))));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.LAVA_DISK.configured(IFeatureConfig.NONE).squared()
						.decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(FeatureSpread.of(2, 6)))));
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.01F, 0.01F);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
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
