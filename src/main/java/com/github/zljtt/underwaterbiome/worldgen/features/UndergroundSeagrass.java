package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class UndergroundSeagrass extends Feature<ProbabilityConfig> {
	public UndergroundSeagrass() {
		super(ProbabilityConfig.CODEC);
	}

	public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos blockpos,
			ProbabilityConfig p_241855_5_) {
		boolean flag = false;
		if (p_241855_1_.getBlockState(blockpos).is(Blocks.WATER)) {
			boolean flag1 = p_241855_3_.nextDouble() < (double) p_241855_5_.probability;
			BlockState blockstate = flag1 ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
			if (blockstate.canSurvive(p_241855_1_, blockpos)) {
				if (flag1) {
					BlockState blockstate1 = blockstate.setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
					BlockPos blockpos1 = blockpos.above();
					if (p_241855_1_.getBlockState(blockpos1).is(Blocks.WATER)) {
						p_241855_1_.setBlock(blockpos, blockstate, 2);
						p_241855_1_.setBlock(blockpos1, blockstate1, 2);
					}
				} else {
					p_241855_1_.setBlock(blockpos, blockstate, 2);
				}

				flag = true;
			}
		}

		return flag;
	}
}
