package com.github.zljtt.underwaterbiome.biomes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.registries.EntityRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;

public class CoralReef {

	public static OceanBiome getNormal() {
		OceanBiome biome = new OceanBiome();
		// setting
		biome.setWeight(10);
		float depth = 1F;
		float scale = 0.1F;
		float temperature = 1.0F;
		float downfall = 1F;
		int waterColor = 0x12c3dd;
		int fogColor = 0x12c3dd;
		int skyColor = 0x12c3dd;

		// mob spawning
		biome.addSpawner(EntityClassification.WATER_AMBIENT, EntityType.TROPICAL_FISH, 10, 3, 6);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityType.PUFFERFISH, 3, 1, 1);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityType.DOLPHIN, 3, 1, 1);
		biome.addSpawner(EntityClassification.WATER_CREATURE, EntityRegistry.STURGEON, 3, 1, 1);
		// generation
		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(),
						Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
		OceanBiomeFeature.addCanyonAndCave(biomeGenerationSettingBuilder, 0.01F, 0.2F);
		OceanBiomeFeature.addBasicSeaGrass(biomeGenerationSettingBuilder, 0.3F, 0.1F, 40);
		OceanBiomeFeature.addDefaultOres(biomeGenerationSettingBuilder);
		OceanBiomeFeature.addRandomLime(biomeGenerationSettingBuilder, 5, 0.02F);
		OceanBiomeFeature.addReef(biomeGenerationSettingBuilder, FeatureSpread.of(1, 2));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.SEA_PICKLE);
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
				Feature.CORAL_MUSHROOM.configured(IFeatureConfig.NONE)
						.decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(FeatureSpread.of(2, 6)))));
		biomeGenerationSettingBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
				Feature.CORAL_CLAW.configured(IFeatureConfig.NONE).decorated(Placements.TOP_SOLID_HEIGHTMAP_SQUARE));

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
