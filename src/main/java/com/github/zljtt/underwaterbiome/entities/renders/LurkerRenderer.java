package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Lurker;
import com.github.zljtt.underwaterbiome.entities.models.LurkerModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class LurkerRenderer extends LivingRenderer<Lurker, LurkerModel> {

	public LurkerRenderer(EntityRendererManager manager) {
		super(manager, new LurkerModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Lurker entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/lurker.png");
	}

	public static class RenderFactory implements IRenderFactory<Lurker> {

		@Override
		public EntityRenderer<? super Lurker> createRenderFor(EntityRendererManager manager) {
			return new LurkerRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(Lurker entity) {
		return false;
	}
}
