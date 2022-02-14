package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.datagen.AllLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.BlockStates;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public interface IBlockDataProvider {
	default LootTable.Builder getLoottableBuilder(Block block) {
		return AllLootTables.dropping(block);
	}

	default void generateBlockState(BlockStates dataGenerator, Block block) {
		dataGenerator.variantBlock(block, dataGenerator.existingModel(block));
	}

	/***
	 * default generate block model for block
	 * 
	 * @param dataGenerator
	 * @param name
	 * @param block
	 */
	default void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.cubeAll(name, BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

	default RenderType getRenderType() {
		return RenderType.solid();
	}

	/***
	 * default generate item model for block
	 * 
	 * @param dataGenerator
	 * @param name
	 * @param block
	 */
	default void generateModel(ItemModels dataGenerator, String name, Item item) {
		dataGenerator.withExistingParent(name, new ResourceLocation(UnderwaterBiome.MODID, "block/" + name));
	}

	default INamedTag<Block> getBlockTags() {
		return null;
	}

}
