package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Sturgeon;
import com.github.zljtt.underwaterbiome.entities.models.SturgeonModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SturgeonRenderer extends LivingRenderer<Sturgeon, SturgeonModel> {

	public SturgeonRenderer(EntityRendererManager manager) {
		super(manager, new SturgeonModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Sturgeon entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/sturgeon.png");
	}

	public static class RenderFactory implements IRenderFactory<Sturgeon> {

		@Override
		public EntityRenderer<? super Sturgeon> createRenderFor(EntityRendererManager manager) {
			return new SturgeonRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(Sturgeon entity) {
		return false;
	}
}
