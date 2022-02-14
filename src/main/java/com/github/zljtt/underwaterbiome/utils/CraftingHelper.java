package com.github.zljtt.underwaterbiome.utils;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Ray;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnderwaterBiome.MODID)
public class CraftingHelper {

	@SubscribeEvent
	public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
		System.out.println("switching game rules");
		event.getPlayer().level.getGameRules().getRule(GameRules.RULE_LIMITED_CRAFTING).set(Config.GENERAL.useBlueprint.get(),
				event.getPlayer().level.getServer());
	}

}
