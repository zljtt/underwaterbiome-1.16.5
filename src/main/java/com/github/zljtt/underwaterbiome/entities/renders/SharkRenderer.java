package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Shark;
import com.github.zljtt.underwaterbiome.entities.models.SharkModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SharkRenderer extends LivingRenderer<Shark, SharkModel> {

	public SharkRenderer(EntityRendererManager manager) {
		super(manager, new SharkModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Shark entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/shark.png");
	}

	public static class RenderFactory implements IRenderFactory<Shark> {

		@Override
		public EntityRenderer<? super Shark> createRenderFor(EntityRendererManager manager) {
			return new SharkRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(Shark entity) {
		return false;
	}
}
