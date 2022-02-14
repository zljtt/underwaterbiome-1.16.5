package com.github.zljtt.underwaterbiome.blocks;

import javax.annotation.Nullable;

import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class MangroveLeaf extends LeavesBlock implements IWaterLoggable, IBlockDataProvider {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public MangroveLeaf() {
		super(Properties.of(Material.LEAVES).strength(0.2F).randomTicks().noOcclusion().sound(SoundType.GRASS)
				.isValidSpawn((state, reader, pos, entity) -> false).isSuffocating((state, reader, pos) -> false)
				.isViewBlocking((state, reader, pos) -> false));
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}

		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(WATERLOGGED));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.translucentNoCrumbling();
	}

	@Override
	public INamedTag<Block> getBlockTags() {
		return BlockTags.LEAVES;
	}

	@Override
	public Builder getLoottableBuilder(Block block) {
		return BlockLootTables.silkTouchOrShear(ItemLootEntry.lootTableItem(Items.AIR), block);
	}

}
