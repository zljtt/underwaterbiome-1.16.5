package com.github.zljtt.underwaterbiome.datagen;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

public class GenericLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> t) {

	}

}
