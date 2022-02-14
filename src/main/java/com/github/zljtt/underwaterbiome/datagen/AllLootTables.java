package com.github.zljtt.underwaterbiome.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class AllLootTables extends LootTableProvider {

	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> lootTableGenerators = ImmutableList
			.of(Pair.of(BlockLootTables::new, LootParameterSets.BLOCK),
					Pair.of(EntityLootTables::new, LootParameterSets.ENTITY),
					Pair.of(GenericLootTables::new, LootParameterSets.ALL_PARAMS));
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator generator;

	public AllLootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
		this.generator = dataGeneratorIn;
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
		return this.lootTableGenerators;
	}

	@Override
	public void run(DirectoryCache cache) {
		Map<ResourceLocation, LootTable> map = Maps.newHashMap();
		this.getTables().forEach((pair) -> {
			pair.getFirst().get().accept((rl, builder) -> {
				if (map.put(rl, builder.setParamSet(pair.getSecond()).build()) != null) {
					throw new IllegalStateException("Duplicate loot table " + rl);
				}
			});
		});
		map.forEach((key, lootTable) -> {
			Path path = this.generator.getOutputFolder()
					.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
			} catch (IOException e) {
				UnderwaterBiome.LOGGER.error("Couldn't write loot table {}", path, e);
			}
		});
	}

	@Override
	public String getName() {
		return "UnderwaterBiome LootTables";
	}

	public static LootTable.Builder dropping(IItemProvider item) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.add(ItemLootEntry.lootTableItem(item)).when(SurvivesExplosion.survivesExplosion()));
	}

	public static LootTable.Builder droppingWithChance(IItemProvider item, float chance) {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(item).when(RandomChance.randomChance(chance)))
						.when(SurvivesExplosion.survivesExplosion()));
	}
}
