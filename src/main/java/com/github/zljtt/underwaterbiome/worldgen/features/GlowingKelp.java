package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.blocks.GlowingKelpTop;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GlowingKelp extends Feature<NoFeatureConfig> {

	public GlowingKelp() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos startPos, NoFeatureConfig config) {
		int i = 0;
		int j = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, startPos.getX(), startPos.getZ());
		BlockPos blockpos = new BlockPos(startPos.getX(), j, startPos.getZ());
		if (reader.getBlockState(blockpos).is(Blocks.WATER)) {
			BlockState kelpTop = BlockRegistry.GLOWING_KELP_TOP.get().defaultBlockState();
			BlockState kelpBody = BlockRegistry.GLOWING_KELP.get().defaultBlockState();
			int k = 1 + random.nextInt(20);
			for (int l = 0; l <= k; ++l) {
				if (reader.getBlockState(blockpos).is(Blocks.WATER) && reader.getBlockState(blockpos.above()).is(Blocks.WATER)
						&& kelpBody.canSurvive(reader, blockpos)) {
					if (l == k) {
						reader.setBlock(blockpos, kelpTop.setValue(GlowingKelpTop.AGE, Integer.valueOf(random.nextInt(4) + 20)),
								2);
						++i;
					} else {
						boolean chance = random.nextFloat() < 0.1F;
						if (chance && l > 0 && l < k) {
							reader.setBlock(blockpos,
									kelpBody.setValue(com.github.zljtt.underwaterbiome.blocks.GlowingKelp.FRUIT, true), 2);
						} else {
							reader.setBlock(blockpos, kelpBody, 2);
						}

					}
				} else if (l > 0) {
					BlockPos blockpos1 = blockpos.below();
					if (kelpTop.canSurvive(reader, blockpos1)
							&& !reader.getBlockState(blockpos1.below()).is(kelpTop.getBlock())) {
						reader.setBlock(blockpos1, kelpTop.setValue(GlowingKelpTop.AGE, Integer.valueOf(random.nextInt(4) + 20)),
								2);
						++i;
					}
					break;
				}

				blockpos = blockpos.above();
			}
		}

		return i > 0;
	}

}
