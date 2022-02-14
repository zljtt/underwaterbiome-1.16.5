package com.github.zljtt.underwaterbiome.utils;

import java.util.Set;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.registries.ItemRegistry;

import com.google.common.collect.Sets;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnderwaterBiome.MODID)
public class OxygenHelper {

	public static final Set<RegistryObject<Item>> BREATHABLE_ITEMS = Sets.newHashSet(ItemRegistry.DOUBLE_OXYGEN_TANK, ItemRegistry.SINGLE_OXYGEN_TANK);

	public static int calculateWaterPressure(double posY) {
		return (int) (10 / (1 + ((100 - posY) / 67d) * ((100 - posY) / 67d)));
	}

	@SubscribeEvent
	public static void onTick(PlayerTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END) && !event.player.level.isClientSide
				&& event.player instanceof ServerPlayerEntity && event.player.getAirSupply() < event.player.getMaxAirSupply()
						- calculateWaterPressure(event.player.position().y)) {
			ItemStack tank = null;
			for (int i = 0; i < event.player.inventory.items.size(); ++i) {
				ItemStack itemstack = event.player.inventory.items.get(i);
				if (itemstack.getDamageValue() < itemstack.getMaxDamage()) {
					for (RegistryObject<Item> item : BREATHABLE_ITEMS) {
						if (item.get().equals(itemstack.getItem())) {
							tank = itemstack;
						}

					}
				}

			}
			if (tank != null) {
				tank.hurt(1, event.player.getRandom(), (ServerPlayerEntity) event.player);
				event.player.setAirSupply(event.player.getAirSupply() + calculateWaterPressure(event.player.position().y));
			}
		}
	}

}