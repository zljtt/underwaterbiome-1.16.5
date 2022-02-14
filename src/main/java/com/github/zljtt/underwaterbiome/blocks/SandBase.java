package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.datagen.BlockModels;

import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;

public class SandBase extends FallingBlock implements IBlockDataProvider {

	public SandBase(Properties properties) {
		super(properties);
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.cubeBottomTop(name, BlockModels.getTexture(name + "_side", BlockModels.BLOCK_FOLDER),
				BlockModels.getTexture("sand", BlockModels.BLOCK_FOLDER),
				BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

}
