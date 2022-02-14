package com.github.zljtt.underwaterbiome.worldgen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;

public class WaterWorldLayer extends Layer {
	private static final Logger LOGGER = LogManager.getLogger();
	private final LazyArea lazyArea;

	public WaterWorldLayer(IAreaFactory<LazyArea> lazyAreaFactoryIn) {
		super(lazyAreaFactoryIn);
		this.lazyArea = lazyAreaFactoryIn.make();
	}

	@Override
	public Biome get(Registry<Biome> biomes, int x, int y) {
		int i = this.lazyArea.get(x, y);
		RegistryKey<Biome> registrykey = RegistryKey.create(Registry.BIOME_REGISTRY, biomes.byId(i).getRegistryName());
		if (registrykey == null) {
			throw new IllegalStateException("Unknown biome id emitted by layers: " + i);
		} else {
			Biome biome = biomes.byId(i);
//			Biome biome = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getValue(i);
			if (biome == null) {
				LOGGER.warn("Unknown biome id: ", (int) i);
				return biomes.get(BiomeRegistry.byId(0));
			} else {
				return biome;
			}
		}
	}
}
