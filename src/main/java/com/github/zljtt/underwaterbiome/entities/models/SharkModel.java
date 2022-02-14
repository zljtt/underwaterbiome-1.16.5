package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.Shark;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SharkModel extends EntityModel<Shark> {
	private final ModelRenderer bone;
	private final ModelRenderer center;
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
	private final ModelRenderer tail3;
	private final ModelRenderer tail3_2;
	private final ModelRenderer tail3_1;
	private final ModelRenderer body_1;
	private final ModelRenderer head;
	private final ModelRenderer body_2;
	private final ModelRenderer head_1_1;
	private final ModelRenderer head_1_2;
	private final ModelRenderer head_2;
	private final ModelRenderer fins;
	private final ModelRenderer fin_left;
	private final ModelRenderer fin_right;
	private final ModelRenderer fin_up;

	public SharkModel() {
		texWidth = 256;
		texHeight = 256;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);

		center = new ModelRenderer(this);
		center.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(center);
		// tail
		tail1 = new ModelRenderer(this);
		tail1.setPos(0.0F, -6.0F, 25.0F);
		center.addChild(tail1);
		tail1.addBox("tail", -9.0F, 4.0F, -4.0F, 18, 2, 15, 0.0F, 69, 239);
		tail1.addBox("tail", -9.0F, -13.0F, -4.0F, 18, 17, 22, 0.0F, 88, 0);

		tail2 = new ModelRenderer(this);
		tail2.setPos(0.0F, -6.0F, 25.0F);
		center.addChild(tail2);
		tail2.addBox("tail", -6.0F, -9.0F, 13.0F, 12, 11, 17, 0.0F, 57, 120);

		tail3 = new ModelRenderer(this);
		tail3.setPos(0.0F, -6.0F, 25.0F);
		center.addChild(tail3);

		tail3_2 = new ModelRenderer(this);
		tail3_2.setPos(-0.5F, -4.0F, 37.5F);
		setRotationAngle(tail3_2, -0.6981F, 0.0F, 0.0F);
		tail3.addChild(tail3_2);
		tail3_2.addBox("tail", -1.5F, -20.0F, -10.5F, 3, 30, 7, 0.0F, 115, 120);

		tail3_1 = new ModelRenderer(this);
		tail3_1.setPos(-0.5F, -4.0F, 37.5F);
		setRotationAngle(tail3_1, 0.6981F, 0.0F, 0.0F);
		tail3.addChild(tail3_1);
		tail3_1.addBox("tail", -1.5F, -7.0F, -10.5F, 3, 17, 6, 0.0F, 0, 64);
		// body
		body_1 = new ModelRenderer(this);
		body_1.setPos(0.0F, -3.0F, 0.0F);
		center.addChild(body_1);
		body_1.addBox("body", -12.0F, -21.0F, -18.0F, 24, 24, 44, 0.0F, 0, 0);
		body_1.addBox("body", -12.0F, 3.0F, -18.0F, 24, 1, 44, 0.0F, 0, 211);
		// head
		head = new ModelRenderer(this);
		head.setPos(0.0F, -7.0F, -22.0F);
		center.addChild(head);

		body_2 = new ModelRenderer(this);
		body_2.setPos(0.0F, -3.5F, 10.5F);
		head.addChild(body_2);
		body_2.addBox("body", -10.0F, -11.5F, -17.5F, 20, 23, 27, 0.0F, 0, 64);

		head_1_1 = new ModelRenderer(this);
		head_1_1.setPos(0.0F, 4.0F, 0.0F);
		setRotationAngle(head_1_1, 0.3491F, 0.0F, 0.0F);
		head.addChild(head_1_1);
		head_1_1.addBox("head", -10.0F, -19.2085F, -22.5526F, 20, 6, 22, 0.0F, 70, 90);

		head_1_2 = new ModelRenderer(this);
		head_1_2.setPos(0.0F, 4.0F, 0.0F);
		setRotationAngle(head_1_2, 2.8798F, 0.0F, 0.0F);
		head.addChild(head_1_2);
		head_1_2.addBox("head", -10.0F, -5.2117F, 3.1822F, 20, 4, 17, 0.0F, 0, 114);

		head_2 = new ModelRenderer(this);
		head_2.setPos(0.0F, 4.0F, 0.0F);
		head.addChild(head_2);
		head_2.addBox("head", -9.0F, -12.0F, -20.0F, 18, 12, 23, 0.0F, 105, 41);
		// fins
		fins = new ModelRenderer(this);
		fins.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(fins);

		fin_left = new ModelRenderer(this);
		fin_left.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(fin_left, 0.0F, 0.0F, 0.6109F);
		fins.addChild(fin_left);
		fin_left.addBox("fin", -17.0F, -7.0F, -7.0F, 8, 18, 8, 0.0F, 0, 0);
		fin_left.addBox("fin", -16.0F, -7.0F, -6.0F, 5, 27, 6, 0.0F, 0, 135);

		fin_right = new ModelRenderer(this);
		fin_right.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(fin_right, 0.0F, 0.0F, -0.6109F);
		fins.addChild(fin_right);
		fin_right.addBox("fin", 10.0F, -7.0F, -7.0F, 8, 18, 8, 0.0F, 0, 0);
		fin_right.addBox("fin", 11.0F, -7.0F, -6.0F, 5, 27, 6, 0.0F, 0, 135);

		fin_up = new ModelRenderer(this);
		fin_up.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(fin_up, -0.7854F, 0.0F, 0.0F);
		fins.addChild(fin_up);
		fin_up.addBox("fin", -2.0F, -37.0F, -17.0F, 3, 21, 10, 0.0F, 134, 76);
	}

	public void setRotationAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Shark entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.bone.xRot = headPitch * ((float) Math.PI / 720F);
		this.head.xRot = headPitch * ((float) Math.PI / 640F);

		this.bone.yRot = netHeadYaw * ((float) Math.PI / 720F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 680F);

		if (Entity.getHorizontalDistanceSqr(entityIn.getDeltaMovement()) > 1.0E-7D) {
			this.tail1.yRot = -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail2.yRot = -0.1F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail3.yRot = -0.2F * MathHelper.cos(ageInTicks * 0.3F);

		} else {
			this.tail1.yRot = -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail2.yRot = -0.07F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail3.yRot = -0.1F * MathHelper.cos(ageInTicks * 0.3F);
		}
		this.fin_right.zRot = -0.6109F - 0.05F * MathHelper.cos(ageInTicks * 0.3F);
		this.fin_left.zRot = 0.6109F + 0.05F * MathHelper.cos(ageInTicks * 0.3F);
//	      this.field_78148_b.xRot = ((float)Math.PI / 2F);
//	      this.field_78149_c.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//	      this.field_78146_d.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78147_e.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78144_f.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}