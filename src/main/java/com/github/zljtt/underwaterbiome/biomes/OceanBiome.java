package com.github.zljtt.underwaterbiome.biomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.entities.Shark;
import com.github.zljtt.underwaterbiome.entities.Sturgeon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.fml.RegistryObject;

public class OceanBiome {
	private Supplier<Biome> biome;
	private int weight;
	private Map<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>> spawns;

	public OceanBiome() {
		this.spawns = new HashMap<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>>();
	}

	public OceanBiome(Supplier<Biome> biome, int weight) {
		this.setBiome(biome);
		this.weight = weight;
		this.spawns = new HashMap<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>>();

	}

	public void addSpawner(EntityClassification classification, EntityType<?> entity, int weight, int min, int max) {
		if (!spawns.containsKey(classification)) {
			spawns.put(classification, new ArrayList<Supplier<MobSpawnInfo.Spawners>>());
		}
		spawns.get(classification).add(() -> new MobSpawnInfo.Spawners(entity, weight, min, max));
	}

	public <T extends Entity> void addSpawner(EntityClassification classification, RegistryObject<EntityType<T>> entity,
			int weight, int min, int max) {
		if (!spawns.containsKey(classification)) {
			spawns.put(classification, new ArrayList<Supplier<MobSpawnInfo.Spawners>>());
		}
		spawns.get(classification).add(() -> new MobSpawnInfo.Spawners(entity.get(), weight, min, max));
	}

	public Map<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>> getSpawns() {
		return spawns;
	}

	public OceanBiome setSpawns(Map<EntityClassification, List<Supplier<MobSpawnInfo.Spawners>>> spawns) {
		this.spawns = spawns;
		return this;
	}

	public int getWeight() {
		return weight;
	}

	public OceanBiome setWeight(int weight) {
		this.weight = weight;
		return this;
	}

	public Supplier<Biome> getBiome() {
		return biome;
	}

	public OceanBiome setBiome(Supplier<Biome> biome) {
		this.biome = biome;
		return this;
	}

}
