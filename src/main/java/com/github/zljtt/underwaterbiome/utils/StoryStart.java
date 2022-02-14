package com.github.zljtt.underwaterbiome.utils;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnderwaterBiome.MODID)
public class StoryStart {

	@SubscribeEvent
	public static void onPlayerFirstJoin(final PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity && player.level instanceof ServerWorld) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			ServerWorld world = (ServerWorld) player.level;
//			RegistryKey<World> key = world.getDimensionKey() == UnderwaterBiome.WATERWORLD ? World.OVERWORLD
//					: UnderwaterBiome.WATERWORLD; 
			ServerWorld destWorld = world.getServer().getLevel(UnderwaterBiome.WATERWORLD);
			if (destWorld == null || world.dimension() == UnderwaterBiome.WATERWORLD || !Config.GENERAL.bornInWaterworld.get()) {
				return;
			}
			if (!player.isOnPortalCooldown()) {
				player.level.getProfiler().push("portal");
				player.changeDimension(destWorld, new TeleporterWaterworldStart());
				player.setPortalCooldown();
				player.level.getProfiler().pop();
			}
			serverPlayer.setRespawnPosition(UnderwaterBiome.WATERWORLD, serverPlayer.blockPosition(), serverPlayer.rotA, true,
					false);
		}

	}

}
