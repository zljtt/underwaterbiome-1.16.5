package com.github.zljtt.underwaterbiome.datagen;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStates extends BlockStateProvider {

	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, UnderwaterBiome.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (RegistryObject<Block> block : BlockRegistry.BLOCKS.getEntries()) {
			if (block.get() instanceof IBlockDataProvider) {
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating BlockState : " + block.get().getRegistryName());
				IBlockDataProvider blockData = (IBlockDataProvider) block.get();
				blockData.generateBlockState(this, block.get());
			}
		}
	}

	public void simpleBlockItem(final Block block) {
		this.simpleBlockItem(block, this.existingModel(block));
	}

	public void variantBlock(Block block, ModelFile model) {
		this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
	}

	public void simpleBlockWithExistingParent(final Block block, final ModelFile parentModel) {
		this.simpleBlock(block, models().getBuilder(block.getRegistryName().getPath()).parent(parentModel));
	}

	public ModelFile existingModel(final Block block) {
		return models().getExistingFile(block.getRegistryName());
	}

	@Override
	public String getName() {
		return "UnderwaterBiome Blockstates";
	}
}