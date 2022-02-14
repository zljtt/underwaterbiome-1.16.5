package com.github.zljtt.underwaterbiome.datagen;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.items.IItemDataProvider;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemTags extends ItemTagsProvider {

	public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, UnderwaterBiome.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		for (RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof IItemDataProvider) {
				IItemDataProvider itemData = (IItemDataProvider) item.get();
				if (itemData.getItemTags() != null) {
					UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Item Tags : " + item.get().getRegistryName());
					this.tag(itemData.getItemTags()).add(item.get());
				}

			}
		}

	}
}
