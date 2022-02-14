package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.LavaFish;
import com.github.zljtt.underwaterbiome.entities.models.LavaFishModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class LavaFishRenderer extends LivingRenderer<LavaFish, LavaFishModel> {

	ResourceLocation lava = new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/lava_fish.png");
	ResourceLocation obsi = new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/obsidian_fish.png");

	public LavaFishRenderer(EntityRendererManager manager) {
		super(manager, new LavaFishModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(LavaFish entity) {
		return entity.isLavaForm() ? lava : obsi;
	}

	public static class RenderFactory implements IRenderFactory<LavaFish> {

		@Override
		public EntityRenderer<? super LavaFish> createRenderFor(EntityRendererManager manager) {
			return new LavaFishRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(LavaFish entity) {
		return false;
	}
}
