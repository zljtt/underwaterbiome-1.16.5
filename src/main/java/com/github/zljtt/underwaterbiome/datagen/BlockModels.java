package com.github.zljtt.underwaterbiome.datagen;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockModels extends BlockModelProvider {

	public BlockModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, UnderwaterBiome.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for (RegistryObject<Block> block : BlockRegistry.BLOCKS.getEntries()) {
			if (block.get() instanceof IBlockDataProvider) {
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Block Model : " + block.get().getRegistryName());
				IBlockDataProvider blockData = (IBlockDataProvider) block.get();
				blockData.generateModel(this, block.get().getRegistryName().getPath(), block.get());
			}
		}
	}

	public static ResourceLocation getTexture(final Block block, String folder) {
		return new ResourceLocation(UnderwaterBiome.MODID, folder + "/" + block.getRegistryName().getPath());
	}

	public static ResourceLocation getTexture(String name, String folder) {
		return new ResourceLocation(UnderwaterBiome.MODID, folder + "/" + name);
	}

}
