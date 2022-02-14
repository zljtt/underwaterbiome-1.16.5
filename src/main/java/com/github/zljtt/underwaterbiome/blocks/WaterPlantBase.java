package com.github.zljtt.underwaterbiome.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.PlantType;

public class WaterPlantBase extends SeaGrassBlock implements ILiquidContainer, IBlockDataProvider {

	VoxelShape shape;
	private List<Block> canSurvive = Lists.newArrayList(Blocks.SAND, Blocks.DIRT, Blocks.STONE);
	@Nullable
	public Supplier<ItemLootEntry.Builder<?>> extraDrop;

	public WaterPlantBase(Properties porperty, VoxelShape shape, @Nullable Supplier<ItemLootEntry.Builder<?>> extraDrop) {
		super(porperty);
		this.shape = shape;
		this.extraDrop = extraDrop;
	}

	public WaterPlantBase(Properties porperty, VoxelShape shape, @Nullable Supplier<ItemLootEntry.Builder<?>> extraDrop,
			Block... blocks) {
		super(porperty);
		this.shape = shape;
		this.extraDrop = extraDrop;
		this.canSurvive = Lists.newArrayList(blocks);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

	@Override
	public Builder getLoottableBuilder(Block block) {
		if (extraDrop != null) {
			return BlockLootTables.silkTouchOrShear(extraDrop.get(), block);
		}
		return BlockLootTables.silkTouchOrShear(ItemLootEntry.lootTableItem(Items.AIR), block);
	}

	@Override
	public void generateModel(ItemModels dataGenerator, String name, Item item) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("generated"), "layer0",
				ItemModels.getTexture(item, ModelProvider.BLOCK_FOLDER));
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("tinted_cross"), "cross",
				BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
		return p_200014_1_.isFaceSturdy(p_200014_2_, p_200014_3_, Direction.UP) && !p_200014_1_.is(Blocks.MAGMA_BLOCK)
				&& canSurvive.contains(p_200014_1_.getBlock());
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_,
			boolean p_176473_4_) {
		return false;
	}

	@Override
	public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
		return false;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.WATER;
	}
}
