package com.github.zljtt.underwaterbiome.biomes;

import com.github.zljtt.underwaterbiome.registries.CaverRegistry;
import com.github.zljtt.underwaterbiome.registries.FeatureRegistry;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class OceanBiomeFeature {

	protected static void addUndergroundBlock(BiomeGenerationSettings.Builder setting,
			Feature<BlockStateFeatureConfig> singleBlockFeature, int count, float probability) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, singleBlockFeature
				.configured(new BlockStateFeatureConfig(Blocks.STONE.defaultBlockState())).count(count)
				.decorated(Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
	}

//	protected static void addUndergroundHangingBlock(BiomeGenerationSettings.Builder setting,
//			Feature<BlockStateFeatureConfig> singleBlockFeature, int count, float probability) {
//		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
//				singleBlockFeature.configured(new BlockStateFeatureConfig(Blocks.STONE.defaultBlockState()))
//						.count(count).decorated(Placement.CARVING_MASK
//								.configured(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
//	}

	protected static void addWaterPlant(BiomeGenerationSettings.Builder setting,
			Feature<BlockStateFeatureConfig> singleBlockFeature, int count) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
				singleBlockFeature.configured(new BlockStateFeatureConfig(Blocks.SAND.defaultBlockState())).count(count)
						.decorated(Placements.TOP_SOLID_HEIGHTMAP));
	}

	protected static void addGlowshroom(BiomeGenerationSettings.Builder setting, int count, float probability) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
				FeatureRegistry.GLOWSHROOM.configured(IFeatureConfig.NONE).decorated(
						Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
	}

	protected static void addReef(BiomeGenerationSettings.Builder setting, FeatureSpread count) {
		setting.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
				FeatureRegistry.REEF.configured(IFeatureConfig.NONE).count(count).decorated(Placements.TOP_SOLID_HEIGHTMAP));
	}

	protected static void addRandomLime(BiomeGenerationSettings.Builder setting, int count, float probability) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
				FeatureRegistry.RANDOM_LIME.configured(IFeatureConfig.NONE).count(count).decorated(
						Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
	}

	protected static void addTopLayerLime(BiomeGenerationSettings.Builder setting, int count) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, FeatureRegistry.RANDOM_LIME
				.configured(IFeatureConfig.NONE).count(count).decorated(Placements.TOP_SOLID_HEIGHTMAP));
	}

	protected static void addBasicSeaGrass(BiomeGenerationSettings.Builder setting, float highGrass, float probability,
			int count) {
		setting.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.SEAGRASS
				.configured(new ProbabilityConfig(highGrass)).count(count).decorated(Placements.HEIGHTMAP_DOUBLE_SQUARE));
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
				Feature.SIMPLE_BLOCK.configured(new BlockWithContextConfig(Blocks.SEAGRASS.defaultBlockState(),
						ImmutableList.of(Blocks.STONE.defaultBlockState()), ImmutableList.of(Blocks.WATER.defaultBlockState()),
						ImmutableList.of(Blocks.WATER.defaultBlockState()))).count(count)
						.decorated(Placement.CARVING_MASK
								.configured(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
	}

	protected static void addDecorativeKelps(BiomeGenerationSettings.Builder setting, int count) {
		setting.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				FeatureRegistry.DECORATIVE_KELP.configured(IFeatureConfig.NONE).squared()
						.decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(count, 60.0D, 0.0D))));
	}

	protected static void addCanyonAndCave(BiomeGenerationSettings.Builder setting, float canyon, float cave) {
		setting.addCarver(GenerationStage.Carving.LIQUID, CaverRegistry.BROKEN_CANYON.configured(new ProbabilityConfig(canyon)));
		setting.addCarver(GenerationStage.Carving.LIQUID,
				CaverRegistry.BROKEN_CANYON_CAVE.configured(new ProbabilityConfig(cave)));
	}

	protected static void addDefaultOres(BiomeGenerationSettings.Builder setting) {
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.defaultBlockState(), 17))
				.range(128).squared().count(20));
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.defaultBlockState(), 9))
				.range(96).squared().count(20));
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.defaultBlockState(), 9))
				.range(48).squared().count(2));
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.defaultBlockState(), 8))
				.range(24).squared().count(8));
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.defaultBlockState(), 8))
				.range(24).squared());
		setting.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE
						.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
								Blocks.LAPIS_ORE.defaultBlockState(), 7))
						.decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared());
	}

}
