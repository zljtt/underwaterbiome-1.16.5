package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.Sturgeon;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SturgeonModel extends EntityModel<Sturgeon> {
	private final ModelRenderer bone;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer head_3;
	private final ModelRenderer head_2;
	private final ModelRenderer head_1;
	private final ModelRenderer tail_3;
	private final ModelRenderer tail_3_2;
	private final ModelRenderer tail_3_1;
	private final ModelRenderer tail_2;
	private final ModelRenderer tail_1;
	private final ModelRenderer fin;
	private final ModelRenderer fin_2;
	private final ModelRenderer fin_1;

	public SturgeonModel() {
		texWidth = 128;
		texHeight = 128;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 20.0F, 0.0F);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(body);
		body.addBox("body", -6.0F, -5.0F, -4.0F, 12, 9, 18, 0.0F, 0, 0);
		body.addBox("body", -5.0F, -6.0F, -4.0F, 10, 1, 17, 0.0F, 0, 27);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(head);

		head_3 = new ModelRenderer(this);
		head_3.setPos(0.0F, 1.0F, -8.0F);
		setRotationAngle(head_3, -1.2217F, 0.0F, 0.0F);
		head.addChild(head_3);
		head_3.addBox("head", -5.0F, -6.0F, -5.0F, 10, 13, 3, 0.0F, 0, 45);

		head_2 = new ModelRenderer(this);
		head_2.setPos(0.0F, 1.0F, -11.5F);
		head.addChild(head_2);
		head_2.addBox("head", -4.0F, -4.0F, 1.5F, 8, 5, 4, 0.0F, 60, 11);
		head_2.addBox("head", -4.0F, -3.0F, -1.5F, 8, 4, 3, 0.0F, 60, 20);
		head_2.addBox("head", -5.0F, -4.0F, 5.5F, 10, 7, 2, 0.0F, 24, 59);

		head_1 = new ModelRenderer(this);
		head_1.setPos(0.0F, 3.0F, -11.5F);
		setRotationAngle(head_1, -0.1745F, 0.0F, 0.0F);
		head.addChild(head_1);
		head_1.addBox("head", -5.0F, -2.0F, -2.5F, 10, 2, 9, 0.0F, 42, 0);

		tail_3 = new ModelRenderer(this);
		tail_3.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(tail_3);

		tail_3_2 = new ModelRenderer(this);
		tail_3_2.setPos(0.0F, 2.0F, 28.0F);
		setRotationAngle(tail_3_2, -0.6981F, 0.0F, 0.0F);
		tail_3.addChild(tail_3_2);
		tail_3_2.addBox("tail", -1.0F, -13.0F, -4.0F, 2, 12, 4, 0.0F, 0, 0);

		tail_3_1 = new ModelRenderer(this);
		tail_3_1.setPos(0.0F, 2.0F, 28.0F);
		setRotationAngle(tail_3_1, -2.618F, 0.0F, 0.0F);
		tail_3.addChild(tail_3_1);
		tail_3_1.addBox("tail", -1.0F, -9.0F, -1.0F, 2, 12, 3, 0.0F, 0, 27);

		tail_2 = new ModelRenderer(this);
		tail_2.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(tail_2);
		tail_2.addBox("tail", -3.0F, -2.0F, 21.0F, 6, 5, 7, 0.0F, 49, 49);
		tail_2.addBox("tail", -2.0F, -3.0F, 21.0F, 4, 1, 4, 0.0F, 42, 11);

		tail_1 = new ModelRenderer(this);
		tail_1.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(tail_1);
		tail_1.addBox("tail", -5.0F, -4.0F, 13.0F, 10, 8, 9, 0.0F, 37, 27);
		tail_1.addBox("tail", -4.0F, -5.0F, 13.0F, 8, 1, 7, 0.0F, 26, 45);

		fin = new ModelRenderer(this);
		fin.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(fin);

		fin_2 = new ModelRenderer(this);
		fin_2.setPos(0.0F, -7.0F, 7.5F);
		setRotationAngle(fin_2, -0.6109F, 0.0F, 0.0F);
		fin.addChild(fin_2);
		fin_2.addBox("fin", -1.0F, -5.0F, -1.5F, 2, 8, 3, 0.0F, 0, 61);

		fin_1 = new ModelRenderer(this);
		fin_1.setPos(0.0F, 6.0F, 16.5F);
		setRotationAngle(fin_1, 0.3491F, 0.0F, 0.0F);
		fin.addChild(fin_1);
		fin_1.addBox("fin", -1.0F, -3.0F, -1.5F, 2, 6, 3, 0.0F, 10, 61);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}

	public void setRotationAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Sturgeon entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.bone.xRot = headPitch * ((float) Math.PI / 640F);
		this.head.xRot = headPitch * ((float) Math.PI / 540F);

		this.bone.yRot = netHeadYaw * ((float) Math.PI / 640F);
		this.head.yRot = headPitch * ((float) Math.PI / 540F);

		if (Entity.getHorizontalDistanceSqr(entityIn.getDeltaMovement()) > 1.0E-7D) {
//	         this.tail_1.xRot += -0.05F + -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail_1.yRot = -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail_2.yRot = -0.09F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail_3.yRot = -0.13F * MathHelper.cos(ageInTicks * 0.3F);
		} else {
			this.tail_1.yRot = -0.02F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail_2.yRot = -0.06F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail_3.yRot = -0.10F * MathHelper.cos(ageInTicks * 0.3F);
		}
//	      this.field_78148_b.xRot = ((float)Math.PI / 2F);
//	      this.field_78149_c.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//	      this.field_78146_d.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78147_e.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78144_f.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}
