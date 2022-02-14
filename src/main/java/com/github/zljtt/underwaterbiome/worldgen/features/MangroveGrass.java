package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class MangroveGrass extends Feature<NoFeatureConfig> {

	public MangroveGrass() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos blockpos,
			NoFeatureConfig p_241855_5_) {
		if (p_241855_1_.getBlockState(blockpos.below()).getBlock().equals(BlockRegistry.MANGROVE_LEAVES.get())
				|| p_241855_1_.getBlockState(blockpos.below()).getBlock().equals(BlockRegistry.MANGROVE_LOG.get())
						&& p_241855_1_.getBlockState(blockpos).getBlock().equals(Blocks.WATER)) {
			boolean flag1 = p_241855_3_.nextDouble() < (double) 0.3;
			BlockState blockstate = flag1 ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
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
			return true;
		} else {
			return false;
		}
	}
}