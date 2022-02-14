package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class LavaBlocks extends Feature<NoFeatureConfig> {

	public LavaBlocks() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos startPos, NoFeatureConfig config) {
		int amount = 3 + random.nextInt(5);
		for (int i = 0; i < amount; i++) {
			int x = startPos.getX() + random.nextInt(8) - random.nextInt(8);
			int z = startPos.getZ() + random.nextInt(8) - random.nextInt(8);
			BlockPos blockpos = new BlockPos(x, reader.getHeight(Heightmap.Type.OCEAN_FLOOR, x, z), z).below();
			if (reader.getBlockState(blockpos).is(Blocks.OBSIDIAN)) {
				if (random.nextInt(10) < 1) {
					reader.setBlock(blockpos, BlockRegistry.FIRE_CORE.get().defaultBlockState(), 3);
				} else {
					reader.setBlock(blockpos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
				}
			}
		}

		return true;
	}

}
