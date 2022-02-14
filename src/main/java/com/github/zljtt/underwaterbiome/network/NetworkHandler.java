package com.github.zljtt.underwaterbiome.network;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.network.messages.RaySize;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final SimpleChannel SIMPLE_CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(UnderwaterBiome.MODID, "underwaterbiome_simplechannel"))
			.clientAcceptedVersions(version -> true).serverAcceptedVersions(version -> true)
			.networkProtocolVersion(() -> "UNDERWATER_BIOME").simpleChannel();

	public static void init() {
		SIMPLE_CHANNEL.registerMessage(0, RaySize.class, RaySize::encode, RaySize::decode, RaySize.Handler::handle);
	}

	public static void sendTo(ServerPlayerEntity playerMP, Object toSend) {
		SIMPLE_CHANNEL.sendTo(toSend, playerMP.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToDimension(Object packet, ServerWorld serverWorld, RegistryKey<World> dimension) {
		PlayerList playerList = serverWorld.getServer().getPlayerList();
		for (int i = 0; i < playerList.getPlayerCount(); ++i) {
			ServerPlayerEntity serverPlayer = playerList.getPlayers().get(i);
			if (serverPlayer.level.dimension() == dimension) {
				sendTo(serverPlayer, packet);
			}
		}
	}

	public static void sendToServer(Object msg) {
		SIMPLE_CHANNEL.sendToServer(msg);
	}

}
