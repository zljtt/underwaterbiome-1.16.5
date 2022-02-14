package com.github.zljtt.underwaterbiome.registries;

import java.util.Random;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.items.Fuel;
import com.github.zljtt.underwaterbiome.items.ItemBase;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

	public static final ItemGroup UB_ITEMS = new ItemGroup("ub_items") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemRegistry.FUEL.get());
		}
	};
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnderwaterBiome.MODID);
	private final static Properties ITEM_BLOCK_PROPERTIES = new Properties().tab(UB_ITEMS);
	private final static Properties ITEM_PROPERTIES = new Properties().tab(UB_ITEMS);

	// tools
	public static final RegistryObject<Item> FUEL = ITEMS.register("fuel", () -> new Fuel(ITEM_PROPERTIES));
	public static final RegistryObject<Item> DOUBLE_OXYGEN_TANK = ITEMS.register("double_oxygen_tank",
			() -> new ItemBase(ITEM_PROPERTIES.durability(600)));
	public static final RegistryObject<Item> SINGLE_OXYGEN_TANK = ITEMS.register("single_oxygen_tank",
			() -> new ItemBase(ITEM_PROPERTIES.durability(300)));
	public static final RegistryObject<Item> MEASURING_DEVICE = ITEMS.register("measuring_device",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> TEMPERATURE_METER = ITEMS.register("temperature_meter",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> PRESSURE_METER = ITEMS.register("pressure_meter",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> TORCH_FAR = ITEMS.register("torch_far", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> TORCH_NEAR = ITEMS.register("torch_near", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> DECIPHER_DEVICE = ITEMS.register("decipher_device",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> BAIT = ITEMS.register("bait", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> NEEDLE_AND_THREAD = ITEMS.register("needle_and_thread",
			() -> new ItemBase(ITEM_PROPERTIES));

	// ingredients
	public static final RegistryObject<Item> CONICAL_FLASK = ITEMS.register("conical_flask", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> LIME_POWDER = ITEMS.register("lime_powder", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> CHAMELEON_SKIN = ITEMS.register("chameleon_skin",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> PERPETUAL_MOTION_CORE = ITEMS.register("perpetual_motion_core",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> PLANT_EXTRACT = ITEMS.register("plant_extract", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> LIGHTING_EXTRACT = ITEMS.register("lighting_extract",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> FLOATING_EXTRACT = ITEMS.register("floating_extract",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> VENOM_EXTRACT = ITEMS.register("venom_extract", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> PRECISION_PART = ITEMS.register("precision_part",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> FISH_SKIN = ITEMS.register("fish_skin", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHARK_FIN = ITEMS.register("shark_fin", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> SILT = ITEMS.register("silt", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> CORAL_STICK = ITEMS.register("coral_stick", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> NEEDLE = ITEMS.register("needle", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> THREAD = ITEMS.register("thread", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> FIRE_ELEMENT = ITEMS.register("fire_element", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> WATER_ELEMENT = ITEMS.register("water_element", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> METHANE_BOTTLE = ITEMS.register("methane_bottle",
			() -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> WATER_BAR = ITEMS.register("water_bar", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> LAVA_BAR = ITEMS.register("lava_bar", () -> new ItemBase(ITEM_PROPERTIES));
	public static final RegistryObject<Item> OBSIDIAN_STICK = ITEMS.register("obsidian_stick",
			() -> new ItemBase(ITEM_PROPERTIES));

	// food
	public static final RegistryObject<Item> SEAWEED_FRUIT = ITEMS.register("seaweed_fruit",
			() -> new ItemBase(ITEM_PROPERTIES.food(new Food.Builder().nutrition(2).saturationMod(0.1F)
					.effect(() -> new EffectInstance(Effects.GLOWING, 600), 1F).build())));
	public static final RegistryObject<Item> OXYGEN_FRUIT = ITEMS.register("oxygen_fruit", () -> new ItemBase(ITEM_PROPERTIES
			.food(new Food.Builder().alwaysEat().effect(() -> new EffectInstance(Effects.WATER_BREATHING, 200), 1F).build())));
	public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp",
			() -> new ItemBase(ITEM_PROPERTIES.food(new Food.Builder().nutrition(3).saturationMod(0.5F).build())));
	public static final RegistryObject<Item> CRAB = ITEMS.register("crab",
			() -> new ItemBase(ITEM_PROPERTIES.food(new Food.Builder().nutrition(3).saturationMod(0.5F).build())));

	// blocks
	public static final RegistryObject<Item> WATER_TORCH = ITEMS.register("water_torch",
			() -> new WallOrFloorItem(BlockRegistry.WATER_TORCH.get(), BlockRegistry.WALL_WATER_TORCH.get(),
					new Item.Properties().tab(ItemRegistry.UB_ITEMS)));
	public static final RegistryObject<Item> MANGROVE_LOG = ITEMS.register("mangrove_log",
			() -> new BlockItem(BlockRegistry.MANGROVE_LOG.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> MANGROVE_LEAVES = ITEMS.register("mangrove_leaf",
			() -> new BlockItem(BlockRegistry.MANGROVE_LEAVES.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> LIME_STONE = ITEMS.register("lime_stone",
			() -> new BlockItem(BlockRegistry.LIME_STONE.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> FLOATING_CORE = ITEMS.register("floating_core",
			() -> new BlockItem(BlockRegistry.FLOATING_CORE.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> FIRE_CORE = ITEMS.register("fire_core",
			() -> new BlockItem(BlockRegistry.FIRE_CORE.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> WATER_CORE = ITEMS.register("water_core",
			() -> new BlockItem(BlockRegistry.WATER_CORE.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> REEF = ITEMS.register("reef",
			() -> new BlockItem(BlockRegistry.REEF.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> LIVING_ROOT = ITEMS.register("living_root",
			() -> new BlockItem(BlockRegistry.LIVING_ROOT.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> AZURIL_SAND = ITEMS.register("azuril_sand",
			() -> new BlockItem(BlockRegistry.AZURIL_SAND.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> EMERALD_SAND = ITEMS.register("emerald_sand",
			() -> new BlockItem(BlockRegistry.EMERALD_SAND.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> FIRE_SAND = ITEMS.register("fire_sand",
			() -> new BlockItem(BlockRegistry.FIRE_SAND.get(), ITEM_BLOCK_PROPERTIES));
	// sea grass
	public static final RegistryObject<Item> WATER_GRASS_RED = ITEMS.register("water_grass_red",
			() -> new BlockItem(BlockRegistry.WATER_GRASS_RED.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> WATER_GRASS_GREEN = ITEMS.register("water_grass_green",
			() -> new BlockItem(BlockRegistry.WATER_GRASS_GREEN.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> EMERALD_GRASS = ITEMS.register("emerald_grass",
			() -> new BlockItem(BlockRegistry.EMERALD_GRASS.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> AZURIL_GRASS = ITEMS.register("azuril_grass",
			() -> new BlockItem(BlockRegistry.AZURIL_GRASS.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> AZURIL_BUSH = ITEMS.register("azuril_bush",
			() -> new BlockItem(BlockRegistry.AZURIL_BUSH.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> FIRE_SEAGRASS = ITEMS.register("fire_seagrass",
			() -> new BlockItem(BlockRegistry.FIRE_SEAGRASS.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> FIRE_SEAGRASS_TALL = ITEMS.register("fire_seagrass_tall",
			() -> new BlockItem(BlockRegistry.FIRE_SEAGRASS_TALL.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> DEPTH_GRASS = ITEMS.register("depth_grass",
			() -> new BlockItem(BlockRegistry.DEPTH_GRASS.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> OXYGEN_FRUIT_PLANT = ITEMS.register("oxygen_fruit_plant",
			() -> new BlockItem(BlockRegistry.OXYGEN_FRUIT_PLANT.get(), ITEM_BLOCK_PROPERTIES));
	public static final RegistryObject<Item> GLOWSHROOM_LITTLE = ITEMS.register("glowshroom_little",
			() -> new BlockItem(BlockRegistry.GLOWSHROOM_LITTLE.get(), ITEM_BLOCK_PROPERTIES));

	public static Item get(ResourceLocation rl) {
		return RegistryObject.of(rl, ForgeRegistries.ITEMS).get();
	}

}
