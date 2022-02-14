package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.CreeperFish;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperFishModel extends EntityModel<CreeperFish> {
	private final ModelRenderer bone;
	private final ModelRenderer body1;
	private final ModelRenderer body2;
	private final ModelRenderer side1;
	private final ModelRenderer side2;
	private final ModelRenderer bone2;
	private final ModelRenderer bone4;
	private final ModelRenderer tail1;
	private final ModelRenderer bone6;
	private final ModelRenderer bone5;
	private final ModelRenderer body3;
	private final ModelRenderer side4;
	private final ModelRenderer bone3;

	public CreeperFishModel() {
		texWidth = 32;
		texHeight = 32;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);

		body1 = new ModelRenderer(this);
		body1.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(body1);
		body1.addBox("body", -1.0F, -7.0F, -2.0F, 4, 4, 4, 0.0F, 0, 0);

		body2 = new ModelRenderer(this);
		body2.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(body2, 0.0F, 0.0F, 0.0873F);
		bone.addChild(body2);
		body2.addBox("body", -3.0F, -6.0F, -1.0F, 2, 1, 2, 0.0F, 6, 8);

		side1 = new ModelRenderer(this);
		side1.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(side1, 0.4363F, 0.0F, 0.0F);
		bone.addChild(side1);
		side1.addBox("side", 0.0F, -4.0F, 3.0F, 2, 2, 1, 0.0F, 14, 8);

		side2 = new ModelRenderer(this);
		side2.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(side2, -0.4363F, 0.0F, 0.0F);
		bone.addChild(side2);
		side2.addBox("side", 0.0F, -4.0F, -4.0F, 2, 2, 1, 0.0F, 14, 8);

		bone2 = new ModelRenderer(this);
		bone2.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(bone2);
		bone2.addBox("bone", 3.0F, -4.0F, -1.0F, 1, 1, 2, 0.0F, 12, 0);

		bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(bone4);
		bone4.addBox("bone", 3.0F, -6.0F, -2.0F, 1, 2, 1, 0.0F, 0, 0);

		tail1 = new ModelRenderer(this);
		tail1.setPos(-5.0F, 0.0F, 0.0F);
		setRotationAngle(tail1, 0.0F, 0.0F, 0.5236F);
		bone.addChild(tail1);
		tail1.addBox("tail", 5.3301F, -12.5F, -1.0F, 1, 4, 2, 0.0F, 0, 8);

		bone6 = new ModelRenderer(this);
		bone6.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone6, 0.0F, 0.0F, -0.5236F);
		bone.addChild(bone6);
		bone6.addBox("bone", 6.0F, -2.0F, -1.0F, 1, 3, 2, 0.0F, 12, 12);

		bone5 = new ModelRenderer(this);
		bone5.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);
		bone.addChild(bone5);
		bone5.addBox("bone", -2.0F, -5.0F, -1.0F, 2, 1, 2, 0.0F, 0, 0);

		body3 = new ModelRenderer(this);
		body3.setPos(0.0F, 24.0F, 0.0F);
		body3.addBox("bidy", 3.0F, -6.0F, -1.0F, 2, 2, 2, 0.0F, 4, 12);

		side4 = new ModelRenderer(this);
		side4.setPos(0.0F, 24.0F, 0.0F);
		side4.addBox("side", 3.0F, -7.0F, -1.0F, 1, 1, 2, 0.0F, 12, 0);

		bone3 = new ModelRenderer(this);
		bone3.setPos(0.0F, 24.0F, 0.0F);
		bone3.addBox("bone", 3.0F, -6.0F, 1.0F, 1, 2, 1, 0.0F, 0, 0);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		body3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		side4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

	}

	public void setRotationAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(CreeperFish entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.bone.yRot = (netHeadYaw - 90) * ((float) Math.PI / 180F);
		this.body3.yRot = (netHeadYaw - 90) * ((float) Math.PI / 180F);
		this.side4.yRot = (netHeadYaw - 90) * ((float) Math.PI / 180F);
		this.bone3.yRot = (netHeadYaw - 90) * ((float) Math.PI / 180F);

		float f = 1.0F;
		if (!entityIn.isInWater()) {
			f = 1.5F;
		}
		this.tail1.yRot = -f * 0.1F * MathHelper.sin(0.4F * ageInTicks);
		this.bone6.yRot = -f * 0.1F * MathHelper.sin(0.4F * ageInTicks);

//		this.bone.xRot = headPitch * ((float)Math.PI / 640F);
//		this.head.xRot = headPitch * ((float)Math.PI / 540F);
//
//	    this.bone.yRot = netHeadYaw * ((float)Math.PI / 640F);
//		this.head.yRot = headPitch * ((float)Math.PI / 540F);

//	    if (Entity.func_213296_b(entityIn.getMotion()) > 1.0E-7D) {
////	         this.tail_1.xRot += -0.05F + -0.05F * MathHelper.cos(ageInTicks * 0.3F);
//	         this.tail_1.yRot = -0.05F * MathHelper.cos(ageInTicks * 0.3F);
//	         this.tail_2.yRot = -0.09F * MathHelper.cos(ageInTicks * 0.3F);
//	         this.tail_3.yRot = -0.13F * MathHelper.cos(ageInTicks * 0.3F);
//	      }
//	    else
//	    {
//	    	 this.tail_1.yRot = -0.02F * MathHelper.cos(ageInTicks * 0.3F);
//	         this.tail_2.yRot = -0.06F * MathHelper.cos(ageInTicks * 0.3F);
//	         this.tail_3.yRot = -0.10F * MathHelper.cos(ageInTicks * 0.3F);
	}
//	      this.field_78148_b.xRot = ((float)Math.PI / 2F);
//	      this.field_78149_c.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//	      this.field_78146_d.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78147_e.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78144_f.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
}
