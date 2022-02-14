package com.github.zljtt.underwaterbiome.registries;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.worldgen.features.DecorativeKelp;
import com.github.zljtt.underwaterbiome.worldgen.features.ElementalDisk;
import com.github.zljtt.underwaterbiome.worldgen.features.FloatingIsland;
import com.github.zljtt.underwaterbiome.worldgen.features.GlowingKelp;
import com.github.zljtt.underwaterbiome.worldgen.features.GlowingKelpTall;
import com.github.zljtt.underwaterbiome.worldgen.features.Glowshroom;
import com.github.zljtt.underwaterbiome.worldgen.features.LavaBlocks;
import com.github.zljtt.underwaterbiome.worldgen.features.MangroveGrass;
import com.github.zljtt.underwaterbiome.worldgen.features.MangroveTree;
import com.github.zljtt.underwaterbiome.worldgen.features.RandomLime;
import com.github.zljtt.underwaterbiome.worldgen.features.Reef;
import com.github.zljtt.underwaterbiome.worldgen.features.SingleBlock;
import com.github.zljtt.underwaterbiome.worldgen.features.UndergroundSeagrass;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry {
	public static final Feature<NoFeatureConfig> MANGROVE_TREE = FeatureRegistry.register("mangrove_tree", new MangroveTree());
	public static final Feature<NoFeatureConfig> MANGROVE_GRASS = FeatureRegistry.register("mangrove_grass", new MangroveGrass());
	public static final Feature<ProbabilityConfig> UNDERGROUND_SEAGRASS = FeatureRegistry.register("underground_seagrass",
			new UndergroundSeagrass());
	public static final Feature<NoFeatureConfig> GLOWING_KELP = FeatureRegistry.register("glowing_kelp", new GlowingKelp());
	public static final Feature<NoFeatureConfig> GLOWING_KELP_TALL = FeatureRegistry.register("glowing_kelp_tall",
			new GlowingKelpTall());
	public static final Feature<NoFeatureConfig> DECORATIVE_KELP = FeatureRegistry.register("decorative_kelp",
			new DecorativeKelp());
	public static final Feature<NoFeatureConfig> RANDOM_LIME = FeatureRegistry.register("random_lime", new RandomLime());
	public static final Feature<NoFeatureConfig> REEF = FeatureRegistry.register("reef", new Reef());
	public static final Feature<NoFeatureConfig> GLOWSHROOM = FeatureRegistry.register("glowshroom", new Glowshroom());
	public static final Feature<NoFeatureConfig> FLOATING_ISLAND = FeatureRegistry.register("floating_island",
			new FloatingIsland());
	public static final Feature<NoFeatureConfig> LAVA_BLOCKS = FeatureRegistry.register("lava_blocks", new LavaBlocks());

	public static final Feature<NoFeatureConfig> LAVA_DISK = FeatureRegistry.register("lava_disk",
			new ElementalDisk(BlockRegistry.FIRE_SAND, FeatureSpread.of(1, 3), 2, Lists.newArrayList((Blocks.OBSIDIAN.defaultBlockState()))));

	public static final Feature<BlockStateFeatureConfig> DEPTH_GRASS = FeatureRegistry.register("depth_grass",
			new SingleBlock(BlockRegistry.DEPTH_GRASS));

	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
		value.setRegistryName(new ResourceLocation(UnderwaterBiome.MODID, key));
		ForgeRegistries.FEATURES.register(value);
		return value;
	}

}
