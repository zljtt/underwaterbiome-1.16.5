package com.github.zljtt.underwaterbiome.blocks;

import java.util.Random;

import com.github.zljtt.underwaterbiome.datagen.BlockModels;
import com.github.zljtt.underwaterbiome.datagen.ItemModels;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ModelProvider;

public class WaterTorch extends TorchBlock implements IWaterLoggable, IBlockDataProvider {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public WaterTorch() {
		super(Properties.of(Material.DECORATION).strength(0).instabreak().lightLevel((state) -> {
			if (state.getValue(WATERLOGGED)) {
				return 14;
			}
			return 0;
		}).noCollission().sound(SoundType.WOOD), ParticleTypes.BUBBLE);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, true));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.is(FluidTags.WATER)));

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
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.addParticle(ParticleTypes.BUBBLE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		} else {
			worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

		}
	}

	@Override
	public void generateModel(BlockModels dataGenerator, String name, Block block) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("template_torch"), "torch",
				BlockModels.getTexture(block, BlockModels.BLOCK_FOLDER));
	}

	@Override
	public void generateModel(ItemModels dataGenerator, String name, Item item) {
		dataGenerator.singleTexture(name, dataGenerator.mcLoc("generated"), "layer0",
				ItemModels.getTexture(item, ModelProvider.BLOCK_FOLDER));
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

}
