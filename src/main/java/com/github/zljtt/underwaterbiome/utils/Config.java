package com.github.zljtt.underwaterbiome.utils;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Config {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final WorldGen WORLDGEN = new WorldGen(BUILDER);

	public static class General {
		public static final String GENERAL = "general";
		public final ForgeConfigSpec.BooleanValue useBlueprint;
		public final ForgeConfigSpec.BooleanValue bornInWaterworld;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push(GENERAL);
			this.useBlueprint = builder.comment(I18n.get("config.general.use_blueprint.comment"))
					.translation("config.general.use_blueprint").define("Use Blueprint", true);
			this.bornInWaterworld = builder.comment(I18n.get("config.general.born_in_waterworld.comment"))
					.translation("config.general.born_in_waterworld").define("Born In Waterworld", true);
			builder.pop();
		}
	}

	public static class WorldGen {
		public static final String WORLDGEN = "worldgen";
		public final IntValue biomeSize;

		public WorldGen(ForgeConfigSpec.Builder builder) {
			builder.push(WORLDGEN);
			this.biomeSize = builder.comment(I18n.get("config.worldgen.biome_size.comment"))
					.translation("config.worldgen.biome_size").defineInRange("Biome Size", 3, 2, 4);
			builder.pop();
		}
	}

	public static ForgeConfigSpec spec = BUILDER.build();

}
