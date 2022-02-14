package com.github.zljtt.underwaterbiome.entities.renders;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.entities.Ray;
import com.github.zljtt.underwaterbiome.entities.models.RayModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RayRenderer extends LivingRenderer<Ray, RayModel> {

	public RayRenderer(EntityRendererManager manager) {
		super(manager, new RayModel(), 0F);
	}

	@Override
	protected void scale(Ray entity, MatrixStack matrix, float p_225620_3_) {

		matrix.scale(entity.getBbWidth() / 2.5F, entity.getBbWidth() / 2.5F, entity.getBbWidth() / 2.5F);
	}

//	@Override
//	public void render(Ray entity, float p_225623_2_, float partialRenderTick, MatrixStack matrixStackIn,
//			IRenderTypeBuffer renderTypeBuffer, int packedLightIn) {
//		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS
//				.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<Ray, RayModel>(entity, this,
//						partialRenderTick, matrixStackIn, renderTypeBuffer, packedLightIn)))
//			return;
//		matrixStackIn.pushPose();
//		this.model.attackTime = this.getAttackAnim(entity, partialRenderTick);
//
//		boolean shouldSit = entity.isPassenger()
//				&& (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
//		this.model.riding = shouldSit;
//		this.model.young = entity.isBaby();
//		float f = MathHelper.rotLerp(partialRenderTick, entity.yBodyRotO, entity.yBodyRot);
//		float f1 = MathHelper.rotLerp(partialRenderTick, entity.yHeadRotO, entity.yHeadRot);
//		float f2 = f1 - f;
//		if (shouldSit && entity.getVehicle() instanceof LivingEntity) {
//			LivingEntity livingentity = (LivingEntity) entity.getVehicle();
//			f = MathHelper.rotLerp(partialRenderTick, livingentity.yBodyRotO, livingentity.yBodyRot);
//			f2 = f1 - f;
//			float f3 = MathHelper.wrapDegrees(f2);
//			if (f3 < -85.0F) {
//				f3 = -85.0F;
//			}
//
//			if (f3 >= 85.0F) {
//				f3 = 85.0F;
//			}
//
//			f = f1 - f3;
//			if (f3 * f3 > 2500.0F) {
//				f += f3 * 0.2F;
//			}
//
//			f2 = f1 - f;
//		}
//
//		float f6 = MathHelper.lerp(partialRenderTick, entity.xRotO, entity.xRot);
//		if (entity.getPose() == Pose.SLEEPING) {
//			Direction direction = entity.getBedOrientation();
//			if (direction != null) {
//				float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
//				matrixStackIn.translate((double) ((float) (-direction.getStepX()) * f4), 0.0D,
//						(double) ((float) (-direction.getStepZ()) * f4));
//			}
//		}
//
//		float f7 = this.getBob(entity, partialRenderTick);
//		this.setupRotations(entity, matrixStackIn, f7, f, partialRenderTick);
//		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
//		this.scale(entity, matrixStackIn, partialRenderTick);
//		matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);
//		float f8 = 0.0F;
//		float f5 = 0.0F;
//		if (!shouldSit && entity.isAlive()) {
//			f8 = MathHelper.lerp(partialRenderTick, entity.animationSpeedOld, entity.animationSpeed);
//			f5 = entity.animationPosition - entity.animationSpeed * (1.0F - partialRenderTick);
//			if (entity.isBaby()) {
//				f5 *= 3.0F;
//			}
//
//			if (f8 > 1.0F) {
//				f8 = 1.0F;
//			}
//		}
//
//		this.model.prepareMobModel(entity, f5, f8, partialRenderTick);
//		this.model.setupAnim(entity, f5, f8, f7, f2, f6);
//		Minecraft minecraft = Minecraft.getInstance();
//		boolean flag = this.isBodyVisible(entity);
//		boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
//		boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
//		RenderType rendertype = this.getRenderType(entity, flag, flag1, flag2);
//		if (rendertype != null) {
//			IVertexBuilder ivertexbuilder = renderTypeBuffer.getBuffer(rendertype);
//			int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialRenderTick));
//			this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F,
//					flag1 ? 0.15F : 1.0F);
//		}
//
//		if (!entity.isSpectator()) {
//			for (LayerRenderer<Ray, RayModel> layerrenderer : this.layers) {
//				layerrenderer.render(matrixStackIn, renderTypeBuffer, packedLightIn, entity, f5, f8, partialRenderTick,
//						f7, f2, f6);
//			}
//		}
//
//		matrixStackIn.popPose();
//		net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(
//				entity, entity.getDisplayName(), this, matrixStackIn, renderTypeBuffer, packedLightIn,
//				partialRenderTick);
//		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
//		if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY
//				&& (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW
//						|| this.shouldShowName(entity))) {
//			this.renderNameTag(entity, renderNameplateEvent.getContent(), matrixStackIn, renderTypeBuffer,
//					packedLightIn);
//		}
//		net.minecraftforge.common.MinecraftForge.EVENT_BUS
//				.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<Ray, RayModel>(entity, this,
//						partialRenderTick, matrixStackIn, renderTypeBuffer, packedLightIn));
//	}

	@Override
	public ResourceLocation getTextureLocation(Ray entity) {
		return new ResourceLocation(UnderwaterBiome.MODID, "textures/entity/ray.png");
	}

	public static class RenderFactory implements IRenderFactory<Ray> {

		@Override
		public EntityRenderer<? super Ray> createRenderFor(EntityRendererManager manager) {
			return new RayRenderer(manager);
		}

	}

	@Override
	protected boolean shouldShowName(Ray entity) {
		return false;
	}
}
