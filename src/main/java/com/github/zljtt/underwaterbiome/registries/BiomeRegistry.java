package com.github.zljtt.underwaterbiome.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.biomes.BrokenCanyon;
import com.github.zljtt.underwaterbiome.biomes.CoralReef;
import com.github.zljtt.underwaterbiome.biomes.FloatingIslands;
import com.github.zljtt.underwaterbiome.biomes.GlowingKelpForest;
import com.github.zljtt.underwaterbiome.biomes.LavaBasin;
import com.github.zljtt.underwaterbiome.biomes.MangroveForest;
import com.github.zljtt.underwaterbiome.biomes.OceanBiome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = UnderwaterBiome.MODID)
public class BiomeRegistry {
	public static final Map<ResourceLocation, OceanBiome> BIOME_MAP = new HashMap<ResourceLocation, OceanBiome>();
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, UnderwaterBiome.MODID);

	public static final RegistryObject<Biome> MANGROVE = register("mangrove", MangroveForest.get());
	public static final RegistryObject<Biome> BROKEN_CANYON = register("broken_canyon", BrokenCanyon.get());
	public static final RegistryObject<Biome> CORAL_REEF_NORMAL = register("coral_reef_normal", CoralReef.getNormal());
	public static final RegistryObject<Biome> FLOATING_ISLANDS = register("floating_island", FloatingIslands.get());
	public static final RegistryObject<Biome> LAVA_BASIN = register("lava_basin", LavaBasin.get());

	public static final RegistryObject<Biome> GLOWING_KELP_FOREST_NORMAL = register("glowing_kelp_forest_normal",
			GlowingKelpForest.getNormal());
	public static final RegistryObject<Biome> GLOWING_KELP_FOREST_EDGE = register("glowing_kelp_forest_edge",
			GlowingKelpForest.getEdge());
	public static final RegistryObject<Biome> GLOWING_KELP_FOREST_ADVANCED = register("glowing_kelp_forest_advanced",
			GlowingKelpForest.getAdvanced());

	private static RegistryObject<Biome> register(String name, OceanBiome biome) {
		RegistryObject<Biome> object = BIOMES.register(name, biome.getBiome());
		BIOME_MAP.put(new ResourceLocation(UnderwaterBiome.MODID, name), biome);
		return object;
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoad(BiomeLoadingEvent event) { // Passive spawns handling
		if (BIOME_MAP.containsKey(event.getName())) {
			System.out.println("[UnderwaterBiome] Loading biome " + event.getName().toString());
			OceanBiome info = BIOME_MAP.get(event.getName());
			for (Entry<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>> entry : info.getSpawns().entrySet()) {
				event.getSpawns().getSpawner(entry.getKey()).clear();
				for (Supplier<Spawners> spawner : entry.getValue()) {
					event.getSpawns().getSpawner(entry.getKey()).add(spawner.get());
				}
			}
		}
	}
}
