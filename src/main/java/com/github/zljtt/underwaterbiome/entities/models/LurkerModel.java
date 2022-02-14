package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.Lurker;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LurkerModel extends EntityModel<Lurker> {
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;
	private final ModelRenderer bone8;
	private final ModelRenderer bone7;
	private final ModelRenderer bone9;
	private final ModelRenderer bone10;

	public LurkerModel() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);// fin+body
		bone.setPos(0.0F, 22.0F, 1.0F);
		setRotationAngle(bone, -0.1745F, 0.0F, 0.0F);
		bone.addBox("bone", -4.0F, -3.0F, -7.0F, 8, 5, 7, 0.0F, 0, 0);
		bone.addBox("bone", 0.0F, -8.0F, -7.0F, 0, 5, 7, 0.0F, 18, 5);

		bone2 = new ModelRenderer(this);// body2+fin
		bone2.setPos(0.0F, 22.0F, 1.0F);
		bone2.addBox("bone", -3.0F, -2.0F, -0.25F, 6, 4, 6, 0.0F, 0, 12);
		bone2.addBox("bone", 0.0F, -6.0F, -0.25F, 0, 4, 6, 0.0F, 18, 27);

		bone3 = new ModelRenderer(this);// mouth base
		bone3.setPos(0.0F, 21.0F, -5.0F);
		bone3.addBox("bone", -3.0F, -3.0F, -6.0F, 6, 3, 6, 0.0F, 18, 18);
		bone3.addBox("bone", -3.0F, 0.0F, -6.0F, 6, 1, 6, 0.0F, 23, 0);

		bone4 = new ModelRenderer(this);// mouth up
		bone4.setPos(0.0F, 3.0F, 5.0F);
		setRotationAngle(bone4, 0.0873F, 0.0F, 0.0F);
		bone3.addChild(bone4);
		bone4.addBox("bone", -2.0F, -7.25F, -8.25F, 4, 2, 5, 0.0F, 0, 27);

		bone5 = new ModelRenderer(this);// mouse down
		bone5.setPos(0.0F, 23.0F, -5.0F);
		setRotationAngle(bone5, 0.3491F, 0.0F, 0.0F);
		bone5.addBox("bone", -2.0F, -0.5F, -5.0F, 4, 1, 5, 0.0F, 27, 12);
		bone5.addBox("bone", -2.0F, -1.5F, -5.0F, 4, 1, 5, 0.0F, 18, 27);

		bone6 = new ModelRenderer(this); // tail
		bone6.setPos(0.0F, 22.0F, 1.0F);
		bone6.addBox("tail", 0.0F, -3.0F, 4.75F, 0, 5, 9, 0.0F, 0, 13);

		bone8 = new ModelRenderer(this);// side fin 2
		bone8.setPos(4.0F, 22.0F, -3.0F);
		setRotationAngle(bone8, 0.0F, -0.3491F, 0.6109F);
		bone8.addBox("fin", 0.0F, 0.0F, -2.0F, 8, 0, 4, 0.0F, 32, 55);

		bone7 = new ModelRenderer(this);// side fin
		bone7.setPos(-4.0F, 22.0F, -3.0F);
		setRotationAngle(bone7, 0.0F, 0.3491F, -0.6109F);
		bone7.addBox("fin", -6.0F, 0.0F, -2.0F, 6, 0, 4, 0.0F, 0, 46);

		bone9 = new ModelRenderer(this);
		bone9.setPos(0.0F, 22.0F, 1.0F);
		setRotationAngle(bone9, 0.0F, 0.3491F, 0.3491F);
		bone9.addBox("fin", -8.0F, 1.0F, -1.0F, 6, 0, 4, 0.0F, 0, 46);

		bone10 = new ModelRenderer(this);
		bone10.setPos(0.0F, 22.0F, 1.0F);
		setRotationAngle(bone10, 0.0F, -0.3491F, -0.3491F);
		bone10.addBox("fin", 2.0F, 1.0F, -1.0F, 6, 0, 4, 0.0F, 32, 55);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		bone10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

	}

	public void setRotationAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Lurker entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

		this.bone2.yRot = 0.09F * MathHelper.cos(0.4F * ageInTicks);
		this.bone9.yRot = 0.3491F + 0.09F * MathHelper.cos(0.4F * ageInTicks);
		this.bone10.yRot = -0.3491F + 0.09F * MathHelper.cos(0.4F * ageInTicks);
//		this.bone9.zRot = 0.3491F + 0.07F * MathHelper.cos(0.4F * ageInTicks);
//		this.bone10.zRot = -0.3491F - 0.07F * MathHelper.cos(0.4F * ageInTicks);

		this.bone7.zRot = -0.6109F + 0.09F * MathHelper.cos(0.4F * ageInTicks);
		this.bone8.zRot = 0.6109F - 0.09F * MathHelper.cos(0.4F * ageInTicks);

		this.bone6.yRot = 0.14F * MathHelper.cos(0.4F * ageInTicks);

//		float f = 1.0F;
//		if (!entityIn.isInWater()) {
//			f = 1.5F;
//		}
//		this.bone6.yRot = -f * 0.1F * MathHelper.sin(0.4F * ageInTicks);
	}

}
