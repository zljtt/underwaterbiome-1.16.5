package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.Random;

import com.github.zljtt.underwaterbiome.registries.BlockRegistry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Reef extends Feature<NoFeatureConfig> {
	public Reef() {
		super(NoFeatureConfig.CODEC);
	}

	public boolean place(ISeedReader reader, ChunkGenerator p_241855_2_, Random random, BlockPos start, NoFeatureConfig cofig) {
		int x = start.getX() - 8 + random.nextInt(16);
		int z = start.getZ() - 8 + random.nextInt(16);

		BlockPos pos = new BlockPos(x, reader.getHeight(Heightmap.Type.OCEAN_FLOOR, x, z), z);
		int size = 2 + random.nextInt(3);

		for (int i = -size; i <= size; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -size; k <= size; k++) {
					if ((float) (i * i + k * k + j * j) <= random.nextFloat() * 15F - random.nextFloat() * 5F) {
						reader.setBlock(pos.offset(i, -1 + j, k), BlockRegistry.REEF.get().defaultBlockState(), 2);
					}
				}

			}
		}

		return true;
	}
}