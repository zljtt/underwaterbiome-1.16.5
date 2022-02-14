package com.github.zljtt.underwaterbiome.worldgen;

import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class WaterWorld extends ForgeWorldType {

	public WaterWorld() {
		super(new IChunkGeneratorFactory() {
			@Override
			public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry,
					Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
				return null;
//				return new WaterWorldChunkGenerator(new WaterWorldBiomeProvider(false, biomeRegistry),
//						() -> WorldGenRegistries.NOISE_SETTINGS.getOrThrow(DimensionSettings.OVERWORLD));
			}
		});
	}

	@Override
	public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures,
			boolean generateLoot, String generatorSettings) {
		return super.createSettings(dynamicRegistries, seed, generateStructures, generateLoot, generatorSettings);
	}

}
