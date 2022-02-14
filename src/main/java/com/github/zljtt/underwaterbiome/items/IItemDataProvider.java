package com.github.zljtt.underwaterbiome.items;

import com.github.zljtt.underwaterbiome.datagen.ItemModels;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.client.model.generators.ModelProvider;

public interface IItemDataProvider {

	default void generateModel(ItemModels dataGenerator, String name, Item item) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("generated"), "layer0",
				ItemModels.getTexture(item, ModelProvider.ITEM_FOLDER));
	}

	default INamedTag<Item> getItemTags() {
		return null;
	}
}
