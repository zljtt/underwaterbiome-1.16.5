package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.CreeperFish;
import com.github.zljtt.underwaterbiome.entities.models.CreeperFishModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CreeperFishRenderer extends LivingRenderer<CreeperFish, CreeperFishModel> {

	public CreeperFishRenderer(EntityRendererManager manager) {
		super(manager, new CreeperFishModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperFish entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/creeper_fish.png");
	}

	public static class RenderFactory implements IRenderFactory<CreeperFish> {

		@Override
		public EntityRenderer<? super CreeperFish> createRenderFor(EntityRendererManager manager) {
			return new CreeperFishRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(CreeperFish entity) {
		return false;
	}
}
