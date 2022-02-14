package com.github.zljtt.underwaterbiome.registries;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.BlockBase;
import com.github.zljtt.underwaterbiome.blocks.DoubleWaterPlant;
import com.github.zljtt.underwaterbiome.blocks.FloatingCore;
import com.github.zljtt.underwaterbiome.blocks.GlowingKelp;
import com.github.zljtt.underwaterbiome.blocks.GlowingKelpTop;
import com.github.zljtt.underwaterbiome.blocks.Lime;
import com.github.zljtt.underwaterbiome.blocks.LivingRoot;
import com.github.zljtt.underwaterbiome.blocks.MangroveLeaf;
import com.github.zljtt.underwaterbiome.blocks.MangroveLog;
import com.github.zljtt.underwaterbiome.blocks.SandBase;
import com.github.zljtt.underwaterbiome.blocks.WallWaterTorch;
import com.github.zljtt.underwaterbiome.blocks.WaterPlantBase;
import com.github.zljtt.underwaterbiome.blocks.WaterTorch;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnderwaterBiome.MODID);

	public static final RegistryObject<Block> WATER_TORCH = BLOCKS.register("water_torch", () -> new WaterTorch());
	public static final RegistryObject<Block> WALL_WATER_TORCH = BLOCKS.register("wall_water_torch", () -> new WallWaterTorch());

	public static final RegistryObject<Block> MANGROVE_LOG = BLOCKS.register("mangrove_log", () -> new MangroveLog());
	public static final RegistryObject<Block> MANGROVE_LEAVES = BLOCKS.register("mangrove_leaf", () -> new MangroveLeaf());

	// small decorations
	public static final RegistryObject<Block> LIME = BLOCKS.register("lime", () -> new Lime());

	// full block decoration stones
	public static final RegistryObject<Block> LIVING_ROOT = BLOCKS.register("living_root", () -> new LivingRoot());
	public static final RegistryObject<Block> FLOATING_CORE = BLOCKS.register("floating_core", () -> new FloatingCore());
	public static final RegistryObject<Block> FIRE_CORE = BLOCKS.register("fire_core",
			() -> new BlockBase(Properties.of(Material.STONE).strength(3F, 3F).sound(SoundType.STONE)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).lightLevel((state) -> 10)));
	public static final RegistryObject<Block> WATER_CORE = BLOCKS.register("water_core",
			() -> new BlockBase(Properties.of(Material.STONE).strength(3F, 3F).sound(SoundType.STONE)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).lightLevel((state) -> 10)));
	public static final RegistryObject<Block> LIME_STONE = BLOCKS.register("lime_stone", () -> new BlockBase(
			Properties.of(Material.STONE).strength(1.5F, 3F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> REEF = BLOCKS.register("reef", () -> new BlockBase(
			Properties.of(Material.STONE).strength(1F, 8F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> AZURIL_SAND = BLOCKS.register("azuril_sand",
			() -> new SandBase(Properties.of(Material.SAND).sound(SoundType.SAND)));
	public static final RegistryObject<Block> EMERALD_SAND = BLOCKS.register("emerald_sand",
			() -> new SandBase(Properties.of(Material.SAND).sound(SoundType.SAND)));
	public static final RegistryObject<Block> FIRE_SAND = BLOCKS.register("fire_sand",
			() -> new SandBase(Properties.of(Material.SAND).sound(SoundType.SAND)));
	// water grass
	public static final RegistryObject<Block> GLOWING_KELP_TOP = BLOCKS.register("glowing_kelp_top", () -> new GlowingKelpTop());
	public static final RegistryObject<Block> GLOWING_KELP = BLOCKS.register("glowing_kelp", () -> new GlowingKelp());

	public static final RegistryObject<Block> WATER_GRASS_RED = BLOCKS.register("water_grass_red",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(2, 0, 2, 14, 8, 14), null));
	public static final RegistryObject<Block> WATER_GRASS_GREEN = BLOCKS.register("water_grass_green",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(2, 0, 2, 14, 8, 14), null));
	public static final RegistryObject<Block> EMERALD_GRASS = BLOCKS.register("emerald_grass",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(1, 0, 1, 15, 14, 15), null));
	public static final RegistryObject<Block> AZURIL_GRASS = BLOCKS.register("azuril_grass",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(1, 0, 1, 15, 4, 15), null));
	public static final RegistryObject<Block> AZURIL_BUSH = BLOCKS.register("azuril_bush",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(2, 0, 2, 14, 16, 14), null));
	public static final RegistryObject<Block> FIRE_SEAGRASS = BLOCKS.register("fire_seagrass",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(1, 0, 1, 15, 4, 15), null));
	public static final RegistryObject<Block> FIRE_SEAGRASS_TALL = BLOCKS.register("fire_seagrass_tall",
			() -> new DoubleWaterPlant(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(1, 0, 1, 15, 4, 15), null));
	public static final RegistryObject<Block> OXYGEN_FRUIT_PLANT = BLOCKS.register("oxygen_fruit_plant",
			() -> new WaterPlantBase(
					Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission(),
					Block.box(2, 0, 2, 14, 14, 14), () -> ItemLootEntry.lootTableItem(ItemRegistry.OXYGEN_FRUIT.get())));
	public static final RegistryObject<Block> GLOWSHROOM_LITTLE = BLOCKS.register("glowshroom_little",
			() -> new WaterPlantBase(Properties.of(Material.WATER_PLANT).strength(0).instabreak().noCollission()
					.sound(SoundType.WET_GRASS).lightLevel((state) -> 12), Block.box(1, 0, 1, 15, 14, 15), null));
	public static final RegistryObject<Block> DEPTH_GRASS = BLOCKS.register("depth_grass",
			() -> new WaterPlantBase(Properties.of(Material.WATER_PLANT).strength(0).instabreak().noCollission()
					.sound(SoundType.WET_GRASS).lightLevel((state) -> 8), Block.box(0, 0, 0, 16, 7, 16), null));

	public static Block get(ResourceLocation rl) {
		return RegistryObject.of(rl, ForgeRegistries.BLOCKS).get();
	}

}
