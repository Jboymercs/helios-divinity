package com.unseen.helios.common.blocks.championHead;// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelChampionHelmet extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer TopCap_r1;

	public ModelChampionHelmet() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, 0.0F, -21.5F, -1.0F, 0, 12, 12, 0.0F, false));

		TopCap_r1 = new ModelRenderer(this);
		TopCap_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(TopCap_r1);
		setRotationAngle(TopCap_r1, 0.0F, -0.7854F, 0.0F);
		TopCap_r1.cubeList.add(new ModelBox(TopCap_r1, 24, 16, -1.0F, -10.5F, -1.0F, 2, 2, 2, 0.0F, false));
		TopCap_r1.cubeList.add(new ModelBox(TopCap_r1, 24, 0, -3.5F, -9.0F, -3.5F, 7, 9, 7, -0.4F, false));
		TopCap_r1.cubeList.add(new ModelBox(TopCap_r1, 0, 24, -3.5F, -9.0F, -3.5F, 7, 9, 7, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float netHeadYaw, float headPitch, float f5) {
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		head.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}