package com.github.zljtt.underwaterbiome.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.BlockStates;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class DoubleWaterPlant extends TallSeaGrassBlock implements IBlockDataProvider {

	@Nullable
	public ItemLootEntry.Builder<?> extraDrop;
	VoxelShape shape;
	private List<Block> canSurvive = Lists.newArrayList(Blocks.SAND, Blocks.DIRT, Blocks.STONE);

	public DoubleWaterPlant(Properties porperty, VoxelShape shape, @Nullable ItemLootEntry.Builder<?> extraDrop) {
		super(porperty);
		this.shape = shape;
		this.extraDrop = extraDrop;
	}

	public DoubleWaterPlant(Properties porperty, VoxelShape shape, @Nullable ItemLootEntry.Builder<?> extraDrop,
			Block... blocks) {
		super(porperty);
		this.shape = shape;
		this.extraDrop = extraDrop;
		this.canSurvive = Lists.newArrayList(blocks);
	}

	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
			ISelectionContext p_220053_4_) {
		return shape;
	}

	@Override
	protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
		return p_200014_1_.isFaceSturdy(p_200014_2_, p_200014_3_, Direction.UP) && !p_200014_1_.is(Blocks.MAGMA_BLOCK)
				&& canSurvive.contains(p_200014_1_.getBlock());
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

	@Override
	public Builder getLoottableBuilder(Block block) {
		if (extraDrop != null) {
			return BlockLootTables.silkTouchOrShear(extraDrop, block);
		}
		return BlockLootTables.silkTouchOrShear(ItemLootEntry.lootTableItem(Items.AIR), block);
	}

	@Override
	public void generateBlockState(BlockStates dataGenerator, Block block) {
		dataGenerator.getVariantBuilder(block)
				.forAllStates(state -> ConfiguredModel.builder()
						.modelFile(dataGenerator.models().getExistingFile(state.getValue(HALF).equals(DoubleBlockHalf.LOWER)
								? new ResourceLocation(UnderwaterBiome.MODID, block.getRegistryName().getPath() + "_lower")
								: new ResourceLocation(UnderwaterBiome.MODID, block.getRegistryName().getPath() + "_upper")))
						.build());
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name + "_lower", dataGenerator.mcLoc("tinted_cross"), "cross",
				new ResourceLocation(UnderwaterBiome.MODID, "block/" + name + "_lower"));
		dataGenerator.singleTexture(name + "_upper", dataGenerator.mcLoc("tinted_cross"), "cross",
				new ResourceLocation(UnderwaterBiome.MODID, "block/" + name + "_upper"));
	}

	@Override
	public void generateModel(ItemModels dataGenerator, String name, Item item) {
		dataGenerator.withExistingParent(name, new ResourceLocation(UnderwaterBiome.MODID, "block/" + name + "_lower"));
	}

	public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
		return new ItemStack(BlockRegistry.FIRE_SEAGRASS.get());
	}

}
