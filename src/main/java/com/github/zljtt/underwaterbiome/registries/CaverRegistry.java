package com.github.zljtt.underwaterbiome.registries;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.worldgen.cavers.BrokenCanyonCaveCaver;
import com.github.zljtt.underwaterbiome.worldgen.cavers.BrokenCanyonCaver;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class CaverRegistry {

	public static final WorldCarver<ProbabilityConfig> BROKEN_CANYON = CaverRegistry.register("broken_canyon",
			new BrokenCanyonCaver());
	public static final WorldCarver<ProbabilityConfig> BROKEN_CANYON_CAVE = CaverRegistry.register("broken_canyon_cave",
			new BrokenCanyonCaveCaver());

	private static <C extends ICarverConfig, F extends WorldCarver<C>> F register(String key, F value) {
		value.setRegistryName(new ResourceLocation(UnderwaterBiome.MODID, key));
		ForgeRegistries.WORLD_CARVERS.register(value);
		return value;
	}

}
