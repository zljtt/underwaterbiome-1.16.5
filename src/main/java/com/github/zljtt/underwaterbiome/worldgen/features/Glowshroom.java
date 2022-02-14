package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Glowshroom extends Feature<NoFeatureConfig> {
	public Glowshroom() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader reader, ChunkGenerator p_241855_2_, Random random, BlockPos pos, NoFeatureConfig cofig) {
		if (pos.getY() < 64) {
			int c = 0;
			int xOffset = -8 + random.nextInt(16);
			int zOffset = -8 + random.nextInt(16);

			for (int j = 0; j < 10; j++) {
				int x = pos.getX() - 4 + random.nextInt(8) + xOffset;
				int y = pos.getY() + 2;
				int z = pos.getZ() - 4 + random.nextInt(8) + zOffset;
				BlockPos blockpos = new BlockPos(x, y, z);
				if (reader.getBlockState(blockpos).getBlock() == Blocks.WATER) {
					for (int i = 0; i < 5; i++) {
						if (reader.getBlockState(blockpos.below(i + 1)).getBlock() == Blocks.STONE
								&& reader.getBlockState(blockpos.below(i)).getBlock() == Blocks.WATER) {
							if (random.nextFloat() < 0.3F) {
								reader.setBlock(blockpos.below(i), BlockRegistry.GLOWSHROOM_LITTLE.get().defaultBlockState(), 2);
							} else {
								reader.setBlock(blockpos.below(i), BlockRegistry.DEPTH_GRASS.get().defaultBlockState(), 2);
							}
							c++;
							break;
						}
					}
				}
			}
			return c > 0;
		}
		return false;
	}
}