package com.github.zljtt.underwaterbiome.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.BiomeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;

public class Biomes extends BiomeProvider {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;

	public Biomes(DataGenerator generator) {
		super(generator);
		this.generator = generator;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(final DirectoryCache cache) {

		Path path = this.generator.getOutputFolder();

		for (Entry<RegistryKey<Biome>, Biome> entry : WorldGenRegistries.BIOME.entrySet()) {
			if (entry.getValue().getRegistryName().getNamespace().equals(UnderwaterBiome.MODID)) {
				Path path1 = path.resolve("data/" + entry.getKey().location().getNamespace() + "/worldgen/biome/"
						+ entry.getKey().location().getPath() + ".json");
				Biome biome = entry.getValue();
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Biome : " + biome.getRegistryName());
				Function<Supplier<Biome>, DataResult<JsonElement>> function = JsonOps.INSTANCE.withEncoder(Biome.CODEC);

				Optional<JsonElement> optional = function.apply(() -> {
					return biome;
				}).result();
				try {
					IDataProvider.save(GSON, cache, optional.get(), path1);
				} catch (IOException e) {
					e.printStackTrace();
				}

//				try {
//					Optional<JsonElement> optional = function.apply(() -> {
//						return biome;
//					}).result();
//					if (optional.isPresent()) {
//						IDataProvider.save(GSON, cache, optional.get(), path1);
//					} else {
//						LOGGER.error("Couldn't serialize biome {}", (Object) path1);
//					}
//				} catch (IOException ioexception) {
//					LOGGER.error("Couldn't save biome {}", path1, ioexception);
//				}
			}
		}
	}

	@Override
	public String getName() {
		return "UnderwaterBiome Biomes";
	}
}
