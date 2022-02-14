package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.Ray;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RayModel extends EntityModel<Ray> {
	private final ModelRenderer bone;
	private final ModelRenderer right;
	private final ModelRenderer left;

	public RayModel() {
		texWidth = 256;
		texHeight = 256;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone, 0.0F, 3.1416F, 0.0F);
		bone.addBox("bone", -12.0F, -6.0F, -5.0435F, 6, 2, 22, 0.0F, 72, 0);
		bone.addBox("bone", -8.0F, -4.0F, -15.0435F, 16, 2, 40, 0.0F, 0, 0);
		bone.addBox("bone", -4.0F, -4.0F, -35.0435F, 8, 2, 12, 0.0F, 0, 0);
		bone.addBox("bone", -2.0F, -4.0F, -53.0435F, 4, 2, 18, 0.0F, 38, 96);
		bone.addBox("bone", -2.0F, -6.0F, -29.0435F, 4, 2, 24, 0.0F, 0, 72);
		bone.addBox("bone", -6.0F, -4.0F, -23.0435F, 12, 2, 8, 0.0F, 0, 14);
		bone.addBox("bone", 6.0F, -6.0F, -5.0435F, 6, 2, 22, 0.0F, 32, 72);
		bone.addBox("bone", -6.0F, -8.0F, -7.0435F, 12, 4, 30, 0.0F, 66, 66);
		bone.addBox("bone", -10.0F, -2.0F, -5.0435F, 20, 2, 28, 0.0F, 0, 42);

		left = new ModelRenderer(this);
		left.setPos(0.0F, -4.0F, 0.0F);
		bone.addChild(left);
		left.addBox("left", 7.0F, 0.0F, 6.9565F, 28, 2, 8, 0.0F, 68, 42);
		left.addBox("left", 7.0F, 0.0F, 0.9565F, 24, 2, 6, 0.0F, 72, 32);
		left.addBox("left", 7.0F, 0.0F, -3.0435F, 18, 2, 4, 0.0F, 64, 106);
		left.addBox("left", 7.0F, 0.0F, -7.0435F, 10, 2, 4, 0.0F, 0, 30);
		left.addBox("left", 7.0F, 0.0F, -11.0435F, 6, 2, 4, 0.0F, 0, 60);
		left.addBox("left", 7.0F, 0.0F, 18.9565F, 10, 2, 4, 0.0F, 0, 24);
		left.addBox("left", 7.0F, 0.0F, 14.9565F, 22, 2, 4, 0.0F, 0, 98);

		right = new ModelRenderer(this);
		right.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(right);
		right.addBox("right", -35.0F, -4.0F, 6.9565F, 28, 2, 8, 0.0F, 68, 52);
		right.addBox("right", -31.0F, -4.0F, 0.9565F, 24, 2, 6, 0.0F, 72, 24);
		right.addBox("right", -25.0F, -4.0F, -3.0435F, 18, 2, 4, 0.0F, 0, 104);
		right.addBox("right", -17.0F, -4.0F, -7.0435F, 10, 2, 4, 0.0F, 0, 48);
		right.addBox("right", -13.0F, -4.0F, -11.0435F, 6, 2, 4, 0.0F, 0, 54);
		right.addBox("right", -29.0F, -4.0F, 14.9565F, 22, 2, 4, 0.0F, 64, 100);
		right.addBox("right", -17.0F, -4.0F, 18.9565F, 10, 2, 4, 0.0F, 0, 42);
	}

	@Override
	public void prepareMobModel(Ray p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		super.prepareMobModel(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
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
	public void setupAnim(Ray entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.bone.xRot = headPitch * ((float) Math.PI / 720F);
		this.bone.yRot = (netHeadYaw + 180) * ((float) Math.PI / 180F);

		if (Entity.getHorizontalDistanceSqr(entityIn.getDeltaMovement()) > 1.0E-7D) {
//	         this.tail_1.xRot += -0.05F + -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.right.zRot = -0.11F * MathHelper.cos(ageInTicks * 0.3F) + 0.05F;
			this.left.zRot = 0.11F * MathHelper.cos(ageInTicks * 0.3F) - 0.05F;

		} else {
			this.right.zRot = -0.11F * MathHelper.cos(ageInTicks * 0.2F) + 0.05F;
			this.left.zRot = 0.11F * MathHelper.cos(ageInTicks * 0.2F) - 0.05F;
		}
	}
}