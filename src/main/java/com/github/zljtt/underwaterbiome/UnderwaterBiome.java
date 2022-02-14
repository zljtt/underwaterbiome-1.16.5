package com.github.zljtt.underwaterbiome;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.network.NetworkHandler;
import com.github.zljtt.underwaterbiome.registries.BiomeRegistry;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.github.zljtt.underwaterbiome.registries.EntityRegistry;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;
import com.github.zljtt.underwaterbiome.registries.SurfaceBuilderRegistry;
import com.github.zljtt.underwaterbiome.utils.Config;
import com.github.zljtt.underwaterbiome.worldgen.WaterWorldBiomeProvider;
import com.github.zljtt.underwaterbiome.worldgen.WaterWorldChunkGenerator;
import net.minecraftforge.fml.config.ModConfig;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("underwaterbiome")
public class UnderwaterBiome {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "underwaterbiome";
	public static final RegistryKey<DimensionType> WATERWORLD_TYPE = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,
			new ResourceLocation(MODID, "waterworld_type"));
	public static final RegistryKey<World> WATERWORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(MODID, "waterworld"));
	public static final RegistryKey<DimensionSettings> WATERWORLD_SETTING = RegistryKey.create(
			Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(UnderwaterBiome.MODID, "waterworld_noise_setting"));
//	public static final RegistryKey<DimensionSettings> WATERWORLD_SETTING = RegistryKey
//			.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation(UnderwaterBiome.MODID, "waterworld"));

	public UnderwaterBiome() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MinecraftForge.EVENT_BUS.register(EntityRegistry.class);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);

		ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BiomeRegistry.BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
		EntityRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SurfaceBuilderRegistry.SURFACE_BUILDERS.register(FMLJavaModLoadingContext.get().getModEventBus());
//		FeatureRegistry.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("[Underwater Biome] Common Setup");
		Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(MODID, "waterworld_layered"),
				WaterWorldBiomeProvider.CODEC);
		Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(MODID, "waterworld_noise"),
				WaterWorldChunkGenerator.CODEC);
		NetworkHandler.init();
		EntityRegistry.registerAttributes();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		for (RegistryObject<Block> block : BlockRegistry.BLOCKS.getEntries()) {
			if (block.get() instanceof IBlockDataProvider) {
				UnderwaterBiome.LOGGER.info("Applying RenderType to Model : " + block.get().getRegistryName());
				IBlockDataProvider blockData = (IBlockDataProvider) block.get();
				RenderTypeLookup.setRenderLayer(block.get(), blockData.getRenderType());
			}
		}
		EntityRegistry.registerModel();
		LOGGER.info("[Underwater Biome] Client Setup");

	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		InterModComms.sendTo("underwaterbiome", "helloworld", () -> {
			LOGGER.info("[Underwater Biome]  MDK");
			return "Hello world";
		});
	}

	private void processIMC(final InterModProcessEvent event) {
		LOGGER.info("[Underwater Biome] Got IMC {}",
				event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		LOGGER.info("[Underwater Biome] Server starting");
	}
}
