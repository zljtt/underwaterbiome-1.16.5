package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.datagen.AllLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable.Builder;

public class GlowingKelpTop extends KelpTopBlock implements IBlockDataProvider {

	public GlowingKelpTop() {
		super(Properties.of(Material.WATER_PLANT).strength(0).noCollission().randomTicks().instabreak()
				.sound(SoundType.WET_GRASS));
	}

	@Override
	protected Block getBodyBlock() {
		return BlockRegistry.GLOWING_KELP.get();
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("tinted_cross"), "cross",
				BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

	@Override
	public void generateModel(ItemModels dataGenerator, String name, Item item) {
		return;
	}

	@Override
	public Builder getLoottableBuilder(Block block) {
		return AllLootTables.droppingWithChance(Items.KELP, 0.3F);
	}

}
