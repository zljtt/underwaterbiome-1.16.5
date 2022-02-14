package com.github.zljtt.underwaterbiome.network.messages;

import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.entities.Ray;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class RaySize {
	private float size;
	private int entity;

	public RaySize(float size, int entity) {
		this.size = size;
		this.entity = entity;

	}

	public static void encode(RaySize packet, PacketBuffer buf) {
		buf.writeFloat(packet.size);
		buf.writeInt(packet.entity);

	}

	public static RaySize decode(PacketBuffer buf) {
		return new RaySize(buf.readFloat(), buf.readInt());
	}

	public static class Handler {
		public static void handle(RaySize message, Supplier<NetworkEvent.Context> ctx) {
			Entity entity = Minecraft.getInstance().level.getEntity(message.entity);
			if (entity instanceof Ray) {
				((Ray) entity).setSize(message.size);
			}
			ctx.get().setPacketHandled(true);
		}
	}
}
