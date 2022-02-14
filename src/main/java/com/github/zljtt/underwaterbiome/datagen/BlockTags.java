package com.github.zljtt.underwaterbiome.datagen;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockTags extends BlockTagsProvider {

	public BlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, UnderwaterBiome.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		for (RegistryObject<Block> block : BlockRegistry.BLOCKS.getEntries()) {
			if (block.get() instanceof IBlockDataProvider) {
				IBlockDataProvider blockData = (IBlockDataProvider) block.get();
				if (blockData.getBlockTags() != null) {
					UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Block Tags : " + block.get().getRegistryName());
					this.tag(blockData.getBlockTags()).add(block.get());
				}

			}
		}
	}
}
