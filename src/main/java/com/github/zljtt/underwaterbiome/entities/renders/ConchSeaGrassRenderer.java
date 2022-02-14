package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.ConchSeaGrass;
import com.github.zljtt.underwaterbiome.entities.models.ConchSeaGrassModel;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ConchSeaGrassRenderer extends LivingRenderer<ConchSeaGrass, ConchSeaGrassModel> {

	public ConchSeaGrassRenderer(EntityRendererManager manager) {
		super(manager, new ConchSeaGrassModel(), 0F);
	}

	@Override
	public ResourceLocation getTextureLocation(ConchSeaGrass entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/conch_sea_grass.png");
	}

	public static class RenderFactory implements IRenderFactory<ConchSeaGrass> {

		@Override
		public EntityRenderer<? super ConchSeaGrass> createRenderFor(EntityRendererManager manager) {
			return new ConchSeaGrassRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(ConchSeaGrass entity) {
		return false;
	}
}
