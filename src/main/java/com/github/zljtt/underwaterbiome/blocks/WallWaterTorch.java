package com.github.zljtt.underwaterbiome.blocks;

import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.BlockStates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ModelProvider;

public class WallWaterTorch extends WaterTorch {
	public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH,
			Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
			Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST,
			Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

	public WallWaterTorch() {
		super();
		this.registerDefaultState(
				this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, true));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.getValue(HORIZONTAL_FACING));
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		return Block.canSupportCenter(worldIn, blockpos, direction);

	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.defaultBlockState();
		IWorldReader iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		Direction[] adirection = context.getNearestLookingDirections();
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());

		for (Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(HORIZONTAL_FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					return blockstate.setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER)));
				}
			}
		}

		return null;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (facing.getOpposite() == stateIn.getValue(HORIZONTAL_FACING) && !stateIn.canSurvive(worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
		} else if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		Direction direction = stateIn.getValue(HORIZONTAL_FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
//		double d3 = 0.22D;
//		double d4 = 0.27D;
		Direction direction1 = direction.getOpposite();
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.addParticle(ParticleTypes.BUBBLE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D,
					d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);

		} else {
			worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D,
					d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D,
					d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED);
	}

	@Override
	public void generateBlockState(BlockStates dataGenerator, Block block) {
		dataGenerator.horizontalBlock(block, dataGenerator.existingModel(block), 90);
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("template_torch_wall"), "torch",
				BlockModels.getTexture("water_torch", ModelProvider.BLOCK_FOLDER));
	}

}
