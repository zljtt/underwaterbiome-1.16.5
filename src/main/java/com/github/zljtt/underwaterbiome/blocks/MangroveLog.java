package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.datagen.BlockModels;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.Direction;

public class MangroveLog extends RotatedPillarBlock implements IBlockDataProvider {

	public MangroveLog() {
		super(Properties.of(Material.WOOD, (state) -> {
			return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.DIRT : MaterialColor.COLOR_GRAY;
		}).strength(2.0F).sound(SoundType.WOOD));
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.cubeColumn(name, BlockModels.getTexture(name + "_side", BlockModels.BLOCK_FOLDER),
				BlockModels.getTexture(name + "_end", BlockModels.BLOCK_FOLDER));
	}

	@Override
	public INamedTag<Block> getBlockTags() {
		return BlockTags.LOGS;
	}

}
