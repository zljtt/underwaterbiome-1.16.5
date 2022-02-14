package com.github.zljtt.underwaterbiome.worldgen;

import java.util.List;
import java.util.Random;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.utils.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.DeepOceanLayer;
import net.minecraft.world.gen.layer.EdgeBiomeLayer;
import net.minecraft.world.gen.layer.EdgeLayer;
import net.minecraft.world.gen.layer.HillsLayer;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.MixOceansLayer;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.StartRiverLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraftforge.registries.ForgeRegistries;

public class WaterWorldBiomeProvider extends BiomeProvider {

	public static final Codec<WaterWorldBiomeProvider> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.BOOL.fieldOf("large_biomes").orElse(false).stable().forGetter((waterWorldBiomeProvider) -> {
			return waterWorldBiomeProvider.largeBiomes;
		}), RegistryLookupCodec.create(ForgeRegistries.Keys.BIOMES).forGetter((waterWorldBiomeProvider) -> {
			return waterWorldBiomeProvider.biomeRegistry;
		})).apply(builder, builder.stable(WaterWorldBiomeProvider::new));
	});

	private final Layer genBiomes;
	private Registry<Biome> biomeRegistry;
	private boolean largeBiomes;

	public WaterWorldBiomeProvider(boolean largeBiomes, Registry<Biome> biomeRegistry) {
		super(StreamSupport.stream(ForgeRegistries.BIOMES.spliterator(), false).filter(
				entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(UnderwaterBiome.MODID))
				.collect(Collectors.toList()));
		long seed = (new Random()).nextLong();
		this.largeBiomes = largeBiomes;
		this.biomeRegistry = biomeRegistry;
		this.genBiomes = WaterWorldBiomeProvider.getNoiseLayer(biomeRegistry, seed,
				largeBiomes ? 5 : Config.WORLDGEN.biomeSize.get());

	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return this.genBiomes.get(this.biomeRegistry, x, z);
	}

	@Override
	protected Codec<? extends BiomeProvider> codec() {
		return CODEC;
	}

	@Override
	public List<Biome> possibleBiomes() {
		return StreamSupport.stream(ForgeRegistries.BIOMES.spliterator(), false).filter(
				entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(UnderwaterBiome.MODID))
				.collect(Collectors.toList());
	}

	@Override
	public BiomeProvider withSeed(long p_230320_1_) {
		return new WaterWorldBiomeProvider(this.largeBiomes, this.biomeRegistry);
	}

//	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildSmartLayers(
//			Registry<Biome> biomeRegistry, int biomeSize, LongFunction<C> context) {
//		IAreaFactory<T> iareafactory = IslandLayer.INSTANCE.run(context.apply(1L));
//		iareafactory = ZoomLayer.FUZZY.run(context.apply(2000L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(1L), iareafactory);
//		iareafactory = ZoomLayer.NORMAL.run(context.apply(2001L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(2L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(50L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(70L), iareafactory);
//		iareafactory = RemoveTooMuchOceanLayer.INSTANCE.run(context.apply(2L), iareafactory);
//		IAreaFactory<T> iareafactory1 = OceanLayer.INSTANCE.run(context.apply(2L));
//		iareafactory1 = zoom(2001L, ZoomLayer.NORMAL, iareafactory1, 6, context);
//		iareafactory = AddSnowLayer.INSTANCE.run(context.apply(2L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(3L), iareafactory);
//		iareafactory = EdgeLayer.CoolWarm.INSTANCE.run(context.apply(2L), iareafactory);
//		iareafactory = EdgeLayer.HeatIce.INSTANCE.run(context.apply(2L), iareafactory);
//		iareafactory = EdgeLayer.Special.INSTANCE.run(context.apply(3L), iareafactory);
//		iareafactory = ZoomLayer.NORMAL.run(context.apply(2002L), iareafactory);
//		iareafactory = ZoomLayer.NORMAL.run(context.apply(2003L), iareafactory);
//		iareafactory = AddIslandLayer.INSTANCE.run(context.apply(4L), iareafactory);
//		iareafactory = AddMushroomIslandLayer.INSTANCE.run(context.apply(5L), iareafactory);
//		iareafactory = DeepOceanLayer.INSTANCE.run(context.apply(4L), iareafactory);
//		iareafactory = zoom(1000L, ZoomLayer.NORMAL, iareafactory, 0, context);
//		IAreaFactory<T> lvt_6_1_ = zoom(1000L, ZoomLayer.NORMAL, iareafactory, 0, context);
//		lvt_6_1_ = StartRiverLayer.INSTANCE.run(context.apply(100L), lvt_6_1_);
//		IAreaFactory<T> lvt_7_1_ = (new BiomeLayer(p_237216_0_)).run(context.apply(200L), iareafactory);
//		lvt_7_1_ = AddBambooForestLayer.INSTANCE.run(context.apply(1001L), lvt_7_1_);
//		lvt_7_1_ = zoom(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, context);
//		lvt_7_1_ = EdgeBiomeLayer.INSTANCE.run(context.apply(1000L), lvt_7_1_);
//		IAreaFactory<T> lvt_8_1_ = zoom(1000L, ZoomLayer.NORMAL, lvt_6_1_, 2, context);
//		lvt_7_1_ = HillsLayer.INSTANCE.run(context.apply(1000L), lvt_7_1_, lvt_8_1_);
//		lvt_6_1_ = zoom(1000L, ZoomLayer.NORMAL, lvt_6_1_, 2, context);
//		lvt_6_1_ = zoom(1000L, ZoomLayer.NORMAL, lvt_6_1_, p_237216_2_, context);
//		lvt_6_1_ = RiverLayer.INSTANCE.run(context.apply(1L), lvt_6_1_);
//		lvt_6_1_ = SmoothLayer.INSTANCE.run(context.apply(1000L), lvt_6_1_);
//		lvt_7_1_ = RareBiomeLayer.INSTANCE.run(context.apply(1001L), lvt_7_1_);
//
//		for (int i = 0; i < p_237216_1_; ++i) {
//			lvt_7_1_ = ZoomLayer.NORMAL.run(context.apply((long) (1000 + i)), lvt_7_1_);
//			if (i == 0) {
//				lvt_7_1_ = AddIslandLayer.INSTANCE.run(context.apply(3L), lvt_7_1_);
//			}
//
//			if (i == 1 || p_237216_1_ == 1) {
//				lvt_7_1_ = ShoreLayer.INSTANCE.run(context.apply(1000L), lvt_7_1_);
//			}
//		}
//
//		lvt_7_1_ = SmoothLayer.INSTANCE.run(context.apply(1000L), lvt_7_1_);
//		lvt_7_1_ = MixRiverLayer.INSTANCE.run(context.apply(100L), lvt_7_1_, lvt_6_1_);
//		return MixOceansLayer.INSTANCE.run(context.apply(100L), lvt_7_1_, iareafactory1);
//
//	}
//
//	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> zoom(long p_202829_0_,
//			IAreaTransformer1 p_202829_2_, IAreaFactory<T> p_202829_3_, int p_202829_4_, LongFunction<C> p_202829_5_) {
//		IAreaFactory<T> iareafactory = p_202829_3_;
//
//		for (int i = 0; i < p_202829_4_; ++i) {
//			iareafactory = p_202829_2_.run(p_202829_5_.apply(p_202829_0_ + (long) i), iareafactory);
//		}
//
//		return iareafactory;
//	}

	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildLayers(Registry<Biome> biomeRegistry,
			int biomeSize, LongFunction<C> context) {
		IAreaFactory<T> layer = IslandLayer.INSTANCE.run(context.apply(1L));
		layer = ZoomLayer.FUZZY.run(context.apply(2000L), layer);
		layer = ZoomLayer.NORMAL.run(context.apply(2001L), layer);
		layer = EdgeLayer.CoolWarm.INSTANCE.run(context.apply(2L), layer);
		layer = ZoomLayer.NORMAL.run(context.apply(2002L), layer);
		layer = ZoomLayer.NORMAL.run(context.apply(2003L), layer);
		layer = DeepOceanLayer.INSTANCE.run(context.apply(4L), layer);
		layer = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, layer, 0, context);
		IAreaFactory<T> zoomLayer = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, layer, 0, context);
		// zoomLayer = StartRiverLayer.INSTANCE.run(context.apply(100L), zoomLayer);
		IAreaFactory<T> biomeLayer = new WaterWorldBiomeLayer(biomeRegistry).run(context.apply(200L), layer);
		biomeLayer = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, biomeLayer, 2, context);
		biomeLayer = EdgeBiomeLayer.INSTANCE.run(context.apply(1000L), biomeLayer);
		IAreaFactory<T> zoomLayerNormal = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, zoomLayer, 2, context);
		biomeLayer = HillsLayer.INSTANCE.run(context.apply(1000L), biomeLayer, zoomLayerNormal);
		zoomLayer = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, zoomLayer, 2, context);
		zoomLayer = LayerUtil.zoom(1000L, ZoomLayer.NORMAL, zoomLayer, 3, context);
		zoomLayer = SmoothLayer.INSTANCE.run(context.apply(1000L), zoomLayer);
		for (int size = 0; size < biomeSize; ++size) {
			biomeLayer = ZoomLayer.NORMAL.run(context.apply(1000 + size), biomeLayer);
		}

		biomeLayer = SmoothLayer.INSTANCE.run(context.apply(1000L), biomeLayer);
		biomeLayer = MixOceansLayer.INSTANCE.run(context.apply(100L), biomeLayer, zoomLayer);
		return biomeLayer;
	}

	public static Layer getNoiseLayer(Registry<Biome> biomeRegistry, long seed, int biomeSize) {
		int maxCacheSize = 25;
		IAreaFactory<LazyArea> layer = buildLayers(biomeRegistry, biomeSize,
				(seedModifier) -> new LazyAreaLayerContext(maxCacheSize, seed, seedModifier));
		return new WaterWorldLayer(layer);
	}

}
