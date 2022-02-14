package com.github.zljtt.underwaterbiome.datagen;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.items.IItemDataProvider;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {

	public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, UnderwaterBiome.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

		for (RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof IItemDataProvider) {
				IItemDataProvider itemData = (IItemDataProvider) item.get();
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Item Model : " + item.get().getRegistryName());
				itemData.generateModel(this, item.get().getRegistryName().getPath(), item.get());
			} else if (item.get() instanceof BlockItem && ((BlockItem) item.get()).getBlock() instanceof IBlockDataProvider) {
				IBlockDataProvider itemData = (IBlockDataProvider) ((BlockItem) item.get()).getBlock();
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Item Model : " + item.get().getRegistryName());
				itemData.generateModel(this, item.get().getRegistryName().getPath(), item.get());

			}
		}
	}

	public static ResourceLocation getTexture(final Item item, String folder) {
		return new ResourceLocation(item.getRegistryName().getNamespace(), folder + "/" + item.getRegistryName().getPath());
	}

	public static ResourceLocation getTexture(String name, String folder) {
		return new ResourceLocation(UnderwaterBiome.MODID, folder + "/" + name);
	}

}
