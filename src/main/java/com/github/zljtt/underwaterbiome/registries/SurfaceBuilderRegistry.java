package com.github.zljtt.underwaterbiome.registries;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderRegistry {
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister
			.create(ForgeRegistries.SURFACE_BUILDERS, UnderwaterBiome.MODID);

//	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MANGROVE = SURFACE_BUILDERS
//			.register("mangrove", () -> new MangroveSurfaceBuilder(SurfaceBuilderConfig.CODEC));
}
