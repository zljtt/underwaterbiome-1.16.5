package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.blocks.Lime;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class RandomLime extends Feature<NoFeatureConfig> {
	public RandomLime() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_,
			NoFeatureConfig p_241855_5_) {
		if ((p_241855_1_.getBlockState(p_241855_4_.below()).getBlock() == Blocks.STONE
				|| p_241855_1_.getBlockState(p_241855_4_.below()).getBlock() == Blocks.SAND)
				&& p_241855_1_.getBlockState(p_241855_4_).getBlock() == Blocks.WATER) {
			p_241855_1_.setBlock(p_241855_4_, BlockRegistry.LIME.get().defaultBlockState()
					.setValue(Lime.FACING, Direction.from2DDataValue(p_241855_3_.nextInt(4))).setValue(Lime.WATERLOGGED, true),
					2);
			return true;
		} else {
			return false;
		}
	}
}