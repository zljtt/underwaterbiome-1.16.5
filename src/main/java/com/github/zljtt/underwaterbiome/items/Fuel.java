package com.github.zljtt.underwaterbiome.items;

import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;

public class Fuel extends Item implements IItemDataProvider {

	public Fuel(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack) {
		return 1600;
	}

}
