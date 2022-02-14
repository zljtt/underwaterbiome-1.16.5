package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;

public class SingleBlock extends Feature<BlockStateFeatureConfig> {
	RegistryObject<Block> block;

	public SingleBlock(RegistryObject<Block> block) {
		super(BlockStateFeatureConfig.CODEC);
		this.block = block;
	}

	public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_,
			BlockStateFeatureConfig p_241855_5_) {
		if (p_241855_1_.getBlockState(p_241855_4_.below()).getBlock() == p_241855_5_.state.getBlock()
				&& p_241855_1_.getBlockState(p_241855_4_).getBlock() == Blocks.WATER) {
			p_241855_1_.setBlock(p_241855_4_, block.get().defaultBlockState(), 2);
			return true;
		} else {
			return false;
		}
	}
}