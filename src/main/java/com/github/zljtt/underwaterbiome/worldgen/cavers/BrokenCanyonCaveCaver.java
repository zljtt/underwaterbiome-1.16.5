package com.github.zljtt.underwaterbiome.worldgen.cavers;

import net.minecraft.world.gen.carver.UnderwaterCaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class BrokenCanyonCaveCaver extends UnderwaterCaveWorldCarver {

	public BrokenCanyonCaveCaver() {
		super(ProbabilityConfig.CODEC);
	}

	@Override
	protected int getCaveBound() {
		return 30;
	}

	@Override
	protected double getYScale() {
		return 2;
	}
}
