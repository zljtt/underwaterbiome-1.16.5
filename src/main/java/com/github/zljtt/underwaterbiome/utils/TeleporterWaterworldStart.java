package com.github.zljtt.underwaterbiome.utils;

import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterWaterworldStart implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		Entity tpEntity = repositionEntity.apply(false);
		BlockPos spawnPos = new BlockPos(destWorld.getLevelData().getXSpawn(), 130, destWorld.getLevelData().getYSpawn());
		while (spawnPos.getY() > 1 && destWorld.isEmptyBlock(spawnPos.below())) {
			spawnPos = spawnPos.below();
		}
		tpEntity.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
		return tpEntity;
	}

}
