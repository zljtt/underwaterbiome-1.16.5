package com.github.zljtt.underwaterbiome.worldgen;

import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.github.zljtt.underwaterbiome.biomes.OceanBiome;
import com.github.zljtt.underwaterbiome.registries.BiomeRegistry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class WaterWorldBiomeLayer implements IC0Transformer {

	private Registry<Biome> biomeRegistry;

	public WaterWorldBiomeLayer(Registry<Biome> biomeRegistry) {
		this.biomeRegistry = biomeRegistry;
	}

	@Override
	public int apply(@Nonnull INoiseRandom noiseRandom, int value) {
//		for (Entry<Biome, Integer> biome : this.biomes.entrySet()) {
//			System.out.println(biome.getKey().getRegistryName() + " | forge ID "
//					+ ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(biome.getKey()) + " / registry ID "
//					+ biomeRegistry.getId(biome.getKey()) + " | weight : " + biome.getValue());
//		} 

		int totalWeight = 0;
		for (Entry<ResourceLocation, OceanBiome> entry : BiomeRegistry.BIOME_MAP.entrySet()) {
			totalWeight += entry.getValue().getWeight();
		}
		int weight = noiseRandom.nextRandom(totalWeight);
		Biome chosenBiomes = this.biomeRegistry.getOrThrow(Biomes.WARM_OCEAN);
		for (Entry<ResourceLocation, OceanBiome> entry : BiomeRegistry.BIOME_MAP.entrySet()) {
			weight -= entry.getValue().getWeight();
			if (weight <= 0) {
				chosenBiomes = this.biomeRegistry.get(entry.getKey());
				break;
			}
		}
		return this.biomeRegistry.getId(chosenBiomes);
	}

}