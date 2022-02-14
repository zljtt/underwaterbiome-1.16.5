package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.LavaFish;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaFishModel extends EntityModel<LavaFish> {
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;
	private final ModelRenderer bone7;
	private final ModelRenderer bone8;
	private final ModelRenderer bone9;
	private final ModelRenderer bone10;
	private final ModelRenderer bone11;
	private final ModelRenderer tail3;
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
	private final ModelRenderer bone13;

	public LavaFishModel() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		bone.addBox("bone", -1.0F, -14.0F, 3.0F, 2, 6, 2, 0.0F, 0, 20);

		bone2 = new ModelRenderer(this);
		bone2.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(bone2);
		bone2.addBox("bone", -3.0F, -9.0F, -4.0F, 6, 7, 13, 0.0F, 0, 0);

		bone3 = new ModelRenderer(this);
		bone3.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone3, 0.0873F, 0.0F, 0.0F);
		bone.addChild(bone3);
		bone3.addBox("bone", -2.0F, -7.0F, -6.0F, 4, 2, 4, 0.0F, 0, 31);

		bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone4, 0.0873F, 0.0F, 0.0F);
		bone.addChild(bone4);
		bone4.addBox("bone", -2.0F, -4.0F, -6.0F, 4, 2, 4, 0.0F, 0, 31);

		bone5 = new ModelRenderer(this);
		bone5.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone5, 0.6981F, 0.0F, -0.2618F);
		bone.addChild(bone5);
		bone5.addBox("bone", -1.0F, -12.0F, 2.0F, 2, 5, 2, 0.0F, 37, 37);

		bone6 = new ModelRenderer(this);
		bone6.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone6, 0.6981F, 0.0F, 0.2618F);
		bone.addChild(bone6);
		bone6.addBox("bone", -1.0F, -12.0F, 2.0F, 2, 5, 2, 0.0F, 0, 37);

		bone7 = new ModelRenderer(this);
		bone7.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone7, 0.2618F, 0.0F, 0.0F);
		bone.addChild(bone7);
		bone7.addBox("bone", -1.0F, -13.0F, 0.0F, 2, 6, 2, 0.0F, 14, 36);

		bone8 = new ModelRenderer(this);
		bone8.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(bone8);
		bone8.addBox("bone", -1.0F, -14.0F, 0.0F, 2, 6, 2, 0.0F, 18, 20);

		bone9 = new ModelRenderer(this);
		bone9.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone9, -0.2618F, 0.0F, 0.0F);
		bone.addChild(bone9);
		bone9.addBox("bone", -1.0F, -14.0F, 3.0F, 2, 6, 2, 0.0F, 0, 0);

		bone10 = new ModelRenderer(this);
		bone10.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(bone10);
		bone10.addBox("bone", -2.0F, -10.0F, -2.0F, 4, 1, 10, 0.0F, 0, 20);

		bone11 = new ModelRenderer(this);
		bone11.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone11, 0.0F, -0.4363F, 0.0F);
		bone.addChild(bone11);
		bone11.addBox("bone", 3.0F, -8.0F, 3.0F, 5, 4, 2, 0.0F, 13, 5);

		tail3 = new ModelRenderer(this);
		tail3.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(tail3, -0.0873F, 0.0F, 0.0F);
		bone.addChild(tail3);
		tail3.addBox("bone", -2.0F, -9.0F, 8.0F, 4, 5, 6, 0.0F, 25, 0);

		tail1 = new ModelRenderer(this);
		tail1.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(tail1, 0.0F, 0.6981F, 0.0F);
		bone.addChild(tail1);
		tail1.addBox("tail", -8.0F, -9.0F, 11.0F, 2, 7, 7, 0.0F, 21, 24);

		tail2 = new ModelRenderer(this);
		tail2.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(tail2, 0.0F, -0.6981F, 0.0F);
		bone.addChild(tail2);
		tail2.addBox("tail", 6.0F, -9.0F, 11.0F, 2, 7, 7, 0.0F, 21, 24);

		bone13 = new ModelRenderer(this);
		bone13.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone13, 0.0F, 0.4363F, 0.0F);
		bone.addChild(bone13);
		bone13.addBox("bone", -8.0F, -8.0F, 3.0F, 5, 4, 2, 0.0F, 23, 31);
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
	public void setupAnim(LavaFish entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
//		this.bone.xRot = headPitch * ((float)Math.PI / 640F);
//
//	    this.bone.yRot = netHeadYaw * ((float)Math.PI / 640F);

		{
			this.tail1.yRot = 0.6981F - 0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail2.yRot = -0.6981F - 0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail3.yRot = -0.05F * MathHelper.cos(ageInTicks * 0.3F);
		}
//	      this.field_78148_b.xRot = ((float)Math.PI / 2F);
//	      this.field_78149_c.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//	      this.field_78146_d.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78147_e.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//	      this.field_78144_f.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}
