package com.github.zljtt.underwaterbiome.datagen;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.blocks.IBlockDataProvider;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds.IntBound;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

	private final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> map) {
		for (RegistryObject<Block> block : BlockRegistry.BLOCKS.getEntries()) {
			if (block.get() instanceof IBlockDataProvider) {
				UnderwaterBiome.LOGGER.info("[UnderwaterBiome] Generating Block LootTable : " + block.get().getRegistryName());
				this.lootTables.put(block.get().getLootTable(),
						((IBlockDataProvider) block.get()).getLoottableBuilder(block.get()));
			}
		}
		Set<ResourceLocation> set = Sets.newHashSet();
		for (Block block : StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false).filter(
				entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(UnderwaterBiome.MODID))
				.collect(Collectors.toSet())) {
			ResourceLocation resourcelocation = block.getLootTable();
			if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
				LootTable.Builder builder = this.lootTables.remove(resourcelocation);
				if (builder == null) {
					throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation,
							BlockRegistry.get(resourcelocation)));
				}
				map.accept(resourcelocation, builder);
			}
		}
	}

	public static LootTable.Builder silkTouchOrShear(LootEntry.Builder<?> without, IItemProvider with) {
		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
								without).when(
										Inverted.invert(
												MatchTool
														.toolMatches(ItemPredicate.Builder.item()
																.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH,
																		IntBound.atLeast(1))))
														.or(MatchTool
																.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(with))
						.when(MatchTool
								.toolMatches(ItemPredicate.Builder.item()
										.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, IntBound.atLeast(1))))
								.or(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)))));
	}

	public static LootTable.Builder noDropping() {
		return LootTable.lootTable();
	}
}
