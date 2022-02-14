package com.github.zljtt.underwaterbiome.datagen;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

	public static final Map<ResourceLocation, Supplier<LootTable.Builder>> ENTITY_LOOTS = Maps.newHashMap();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> map) {

//		map.accept(new ResourceLocation(UnderwaterBiome.MODID, "entities/shark"), Shark.getLoottableBuilder());
		Set<ResourceLocation> set = Sets.newHashSet();
		for (EntityType<?> entity : StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false).filter(
				entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(UnderwaterBiome.MODID))
				.collect(Collectors.toSet())) {
			ResourceLocation resourcelocation = entity.getDefaultLootTable();
			if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
				LootTable.Builder builder = ENTITY_LOOTS.remove(resourcelocation).get();
				if (builder == null) {
					throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation,
							BlockRegistry.get(resourcelocation)));
				}

				UnderwaterBiome.LOGGER
						.info("[UnderwaterBiome] Generating Entity LootTable : " + entity.getRegistryName().getPath());
				map.accept(resourcelocation, builder);
			}
		}
	}

}
