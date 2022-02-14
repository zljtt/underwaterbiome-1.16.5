package com.github.zljtt.underwaterbiome.entities.models;

import com.github.zljtt.underwaterbiome.entities.ConchSeaGrass;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConchSeaGrassModel extends EntityModel<ConchSeaGrass> {
	private final ModelRenderer bone;
	private final ModelRenderer shell;
	private final ModelRenderer body;

	public ConchSeaGrassModel() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone, 0.0F, -2.3562F, 0.0F);

		shell = new ModelRenderer(this);
		shell.setPos(0.0F, 0.0F, 0.0F);
		setRotationAngle(shell, 0.3491F, 0.0F, 0.3491F);
		bone.addChild(shell);
		shell.addBox("shell", -6.0F, -3.0F, -4.0F, 10, 2, 10, 0.0F, 0, 0);
		shell.addBox("shell", -5.0F, -5.0F, -3.0F, 8, 2, 8, 0.0F, 0, 12);
		shell.addBox("shell", -4.0F, -4.0F, 5.0F, 7, 1, 1, 0.0F, 18, 22);
		shell.addBox("shell", -4.0F, -7.0F, -1.0F, 6, 1, 5, 0.0F, 24, 12);
		shell.addBox("shell", -3.0F, -6.0F, -2.0F, 5, 1, 1, 0.0F, 24, 18);
		shell.addBox("shell", -4.0F, -6.0F, -1.0F, 6, 1, 6, 0.0F, 0, 22);
		shell.addBox("shell", -3.0F, -8.0F, 0.0F, 4, 1, 3, 0.0F, 7, 29);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		bone.addChild(body);
		body.addBox("body", -3.0F, -3.0F, -2.0F, 5, 3, 5, 0.0F, 19, 24);
		body.addBox("body", -2.0F, -2.0F, 3.0F, 5, 2, 1, 0.0F, 30, 0);
		body.addBox("body", -4.0F, -2.0F, -3.0F, 1, 2, 5, 0.0F, 0, 29);
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
	public void setupAnim(ConchSeaGrass entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.bone.yRot = (netHeadYaw - 90) * ((float) Math.PI / 180F);
	}

}
