package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Conch;
import com.github.zljtt.underwaterbiome.entities.models.ConchModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ConchRenderer extends LivingRenderer<Conch, ConchModel> {

	public ConchRenderer(EntityRendererManager manager) {
		super(manager, new ConchModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Conch entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/conch.png");
	}

	public static class RenderFactory implements IRenderFactory<Conch> {

		@Override
		public EntityRenderer<? super Conch> createRenderFor(EntityRendererManager manager) {
			return new ConchRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(Conch entity) {
		return false;
	}
}
