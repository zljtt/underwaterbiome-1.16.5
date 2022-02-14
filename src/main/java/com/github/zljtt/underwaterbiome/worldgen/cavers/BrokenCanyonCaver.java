package com.github.zljtt.underwaterbiome.worldgen.cavers;

import net.minecraft.world.gen.carver.UnderwaterCanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class BrokenCanyonCaver extends UnderwaterCanyonWorldCarver {

	public BrokenCanyonCaver() {
		super(ProbabilityConfig.CODEC);
	}

	@Override
	public int getRange() {
		return 6;
	}

}
