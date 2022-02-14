package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.List;
import java.util.Random;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;

public class ElementalDisk extends Feature<NoFeatureConfig> {

	public final RegistryObject<Block> state;
	public final FeatureSpread radius;
	public final int halfHeight;
	public final List<BlockState> targets;

	public ElementalDisk(RegistryObject<Block> state, FeatureSpread radius, int halfHeight, List<BlockState> targets) {
		super(NoFeatureConfig.CODEC);
		this.state = state;
		this.radius = radius;
		this.halfHeight = halfHeight;
		this.targets = targets;

	}

	public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig c) {
		boolean flag = false;
		int i = radius.sample(random);

		for (int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
			for (int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
				int l = j - pos.getX();
				int i1 = k - pos.getZ();
				if (l * l + i1 * i1 <= i * i) {
					for (int j1 = pos.getY() - halfHeight; j1 <= pos.getY() + halfHeight; ++j1) {
						BlockPos blockpos = new BlockPos(j, j1, k);
						Block block = reader.getBlockState(blockpos).getBlock();

						for (BlockState blockstate : targets) {
							if (blockstate.is(block)) {
								reader.setBlock(blockpos, state.get().defaultBlockState(), 2);
								if (random.nextBoolean() && reader.getFluidState(blockpos.above()).is(FluidTags.WATER)) {
									if (state.get().equals(BlockRegistry.AZURIL_SAND.get())) {
										reader.setBlock(blockpos.above(), BlockRegistry.AZURIL_GRASS.get().defaultBlockState(),
												2);
									} else if (state.get().equals(BlockRegistry.EMERALD_SAND.get())) {
										reader.setBlock(blockpos.above(), BlockRegistry.EMERALD_GRASS.get().defaultBlockState(),
												2);
									} else if (state.get().equals(BlockRegistry.FIRE_SAND.get())) {
										if (random.nextBoolean()) {
											reader.setBlock(blockpos.above(), BlockRegistry.FIRE_SEAGRASS_TALL.get()
													.defaultBlockState().setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.LOWER),
													2);
											reader.setBlock(blockpos.above(2), BlockRegistry.FIRE_SEAGRASS_TALL.get()
													.defaultBlockState().setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER),
													2);
										} else {
											reader.setBlock(blockpos.above(),
													BlockRegistry.FIRE_SEAGRASS.get().defaultBlockState(), 2);
										}
									}
								}
								flag = true;
								break;
							}
						}
					}
				}
			}
		}

		return flag;
	}
}
