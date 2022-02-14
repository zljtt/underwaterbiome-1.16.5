package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FloatingIsland extends Feature<NoFeatureConfig> {
	public FloatingIsland() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader reader, ChunkGenerator p_241855_2_, Random random, BlockPos start,
			NoFeatureConfig p_241855_5_) {
		int x = start.getX() - 8 + random.nextInt(16);
		int z = start.getZ() - 8 + random.nextInt(16);
		BlockPos pos = new BlockPos(x, reader.getHeight(Heightmap.Type.OCEAN_FLOOR, x, z), z);

		int offset = 100 - pos.getY();
		if (offset > 24) {
			BlockPos genPos = pos.above(5 + random.nextInt(offset - 10));
			int range = 3 + random.nextInt(7);
			Set<BlockPos> listStone = new HashSet<BlockPos>();
			Set<BlockPos> listSand = new HashSet<BlockPos>();
			Set<BlockPos> listGrass = new HashSet<BlockPos>();

			for (int i = 0; i < range * range * range * 3; i++) {
				double angle1 = random.nextDouble() * 360;
				double angle2 = -random.nextDouble() * 90;
				double randomRange = random.nextDouble() * range;
				BlockPos genPosStone = new BlockPos(
						genPos.getX()
								+ Math.ceil((randomRange) * Math.cos(Math.toRadians(angle2)) * Math.cos(Math.toRadians(angle1))),

						genPos.getY() + Math.ceil((randomRange) * Math.sin(Math.toRadians(angle2))), genPos.getZ()
								+ Math.ceil((randomRange) * Math.cos(Math.toRadians(angle2)) * Math.sin(Math.toRadians(angle1))));
				listStone.add(genPosStone);

			}
			for (int i = 0; i < range * range * 3; i++) {
				double angleSand = random.nextDouble() * 360;
				double randomRangeSand = random.nextDouble() * range;
				BlockPos genPosSand = new BlockPos(
						genPos.getX() + Math.ceil(randomRangeSand * Math.cos(Math.toRadians(angleSand))), genPos.getY() + 1,
						genPos.getZ() + Math.ceil(randomRangeSand * Math.sin(Math.toRadians(angleSand))));
				listSand.add(genPosSand);

			}
			for (int i = 0; i < range * range; i++) {
				double angleGrass = random.nextDouble() * 360;
				double randomRange = random.nextDouble() * range;
				BlockPos genPosGrass = new BlockPos(
						genPos.getX() + Math.ceil((randomRange) * Math.cos(Math.toRadians(angleGrass))), genPos.getY() + 2,
						genPos.getZ() + Math.ceil((randomRange) * Math.sin(Math.toRadians(angleGrass))));
				listGrass.add(genPosGrass);

			}
			listStone.forEach((p) -> {
				reader.setBlock(p, Blocks.STONE.defaultBlockState(), 2);
			});
			int waterCore = random.nextInt(3);
			for (int i = 0; i < waterCore; i++) {
				int id = random.nextInt(listSand.size());
				BlockPos remove = listSand.toArray(new BlockPos[] {})[id];
				reader.setBlock(remove, BlockRegistry.WATER_CORE.get().defaultBlockState(), 2);
				listSand.remove(remove);
			}
			listSand.forEach((p) -> {
				reader.setBlock(p, Blocks.SAND.defaultBlockState(), 2);
			});
			boolean color = random.nextBoolean();
			if (color) {
				listGrass.forEach((p) -> {
					if (reader.getBlockState(p.below()).getBlock().equals(Blocks.SAND)) {
						reader.setBlock(p, BlockRegistry.WATER_GRASS_GREEN.get().defaultBlockState(), 2);
					}
				});
			} else {
				listGrass.forEach((p) -> {
					if (reader.getBlockState(p.below()).getBlock().equals(Blocks.SAND)) {
						reader.setBlock(p, BlockRegistry.WATER_GRASS_RED.get().defaultBlockState(), 2);
					}
				});
			}

			reader.setBlock(genPos, BlockRegistry.FLOATING_CORE.get().defaultBlockState(), 2);

			return true;
//			int height = 5 + random.nextInt(10);
//			int width = height / 4;
//			BlockPos buildPos = pos.above(2 + height + random.nextInt(offset - height * 2));
//			int[] set = new int[width];
//			set[0] = 2 + random.nextInt(height - 2);
//			for (int i = 1; i < width; i++) {
//				set[i] = 2 + set[i - 1] + random.nextInt(height - set[i - 1] - 2 > 0 ? height - set[i - 1] - 2 : 1);
//			}
//			int layer = 0;
//			for (int i = 0; i < height; i++) {
//				if (width > 1 && i == set[layer]) {
//					width -= 1;
//					layer += 1;
//				}
//				for (int x0 = -width + 1; x0 < width; x0++) {
//					for (int z0 = -width + 1; z0 < width; z0++) {
//						reader.setBlock(buildPos.offset(x0, i, z0), Blocks.CUT_SANDSTONE.defaultBlockState(), 2);
//						reader.setBlock(buildPos.offset(x0, -i, z0), Blocks.CUT_SANDSTONE.defaultBlockState(), 2);
//					}
//				}
//			}
//			reader.setBlock(buildPos.offset(0, height, 0), Blocks.CUT_SANDSTONE.defaultBlockState(), 2);
//			reader.setBlock(buildPos.offset(0, -height, 0), Blocks.CUT_SANDSTONE.defaultBlockState(), 2);
//			reader.setBlock(buildPos, BlockRegistry.FLOATING_CORE.get().defaultBlockState(), 2);
		}

		return false;
	}
}