package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.BlockStates;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder.PartialBlockstate;

public class GlowingKelp extends KelpBlock implements IBlockDataProvider {

	public static final BooleanProperty FRUIT = BooleanProperty.create("fruit");

	public GlowingKelp() {
		super(Properties.of(Material.WATER_PLANT).strength(0).instabreak().sound(SoundType.WET_GRASS).noCollission()
				.lightLevel((state) -> state.getValue(FRUIT) ? 15 : 0));
		this.registerDefaultState(this.stateDefinition.any().setValue(FRUIT, false));

	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return (AbstractTopPlantBlock) BlockRegistry.GLOWING_KELP_TOP.get();
	}

	@Override
	public void generateBlockState(BlockStates dataGenerator, Block block) {
		PartialBlockstate partialState = dataGenerator.getVariantBuilder(block).partialState();
		dataGenerator.getVariantBuilder(block).addModels(partialState.with(FRUIT, true), ConfiguredModel.builder()
				.modelFile(dataGenerator.models().getExistingFile(
						new ResourceLocation(UnderwaterBiome.MODID, block.getRegistryName().getPath() + "_fruit")))
				.build()).addModels(partialState.with(FRUIT, false),
						ConfiguredModel.builder().modelFile(dataGenerator.existingModel(block)).build());
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name + "_fruit", dataGenerator.mcLoc("tinted_cross"), "cross",
				new ResourceLocation(UnderwaterBiome.MODID, "block/" + name + "_fruit"));
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("tinted_cross"), "cross",
				BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FRUIT));
	}

	@Override
	public net.minecraft.loot.LootTable.Builder getLoottableBuilder(Block block) {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(Items.KELP).when(RandomChance.randomChance(0.3F)))
						.add(ItemLootEntry.lootTableItem(ItemRegistry.SEAWEED_FRUIT.get())
								.when(new BlockStateProperty.Builder(block)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FRUIT, true))))
						.when(SurvivesExplosion.survivesExplosion()));
	}

	@Override
	public void generateModel(ItemModels dataGenerator, String name, Item item) {
		return;
	}
}
