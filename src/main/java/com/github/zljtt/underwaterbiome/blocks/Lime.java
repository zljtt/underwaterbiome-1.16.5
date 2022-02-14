package com.github.zljtt.underwaterbiome.blocks;

import com.github.zljtt.underwaterbiome.datagen.AllLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockLootTables;
import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.BlockStates;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class Lime extends BlockBase implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape BOUNDING_BOX_0 = Block.box(3, 0, 3, 15, 2, 15);

	public Lime() {
		super(Properties.of(Material.STONE).strength(0.5f).sound(SoundType.STONE));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, true));
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		// not generating
	}

	@Override
	public Builder getLoottableBuilder(Block block) {
		return AllLootTables.dropping(ItemRegistry.LIME_POWDER.get());
	}

	@Override
	public void generateBlockState(BlockStates dataGenerator, Block block) {
		dataGenerator.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
				.modelFile(dataGenerator.existingModel(block)).rotationY(state.getValue(FACING).get2DDataValue() * 90).build());
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.solid();
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
			ISelectionContext p_220053_4_) {
		return BOUNDING_BOX_0;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_,
			ISelectionContext p_220071_4_) {
		return BOUNDING_BOX_0;
	}

	// IWaterLoggable code
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED,
				Boolean.valueOf(fluidstate.is(FluidTags.WATER)));

	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.WATER.defaultBlockState()
					: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
		return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);

	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return state.isFaceSturdy(world, pos, Direction.DOWN);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}
}
