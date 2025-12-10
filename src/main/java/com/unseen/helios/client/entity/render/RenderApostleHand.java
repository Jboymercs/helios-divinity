package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelApostleHand;
import com.unseen.helios.client.entity.model.ModelManusAvaritia;
import com.unseen.helios.client.entity.util.RenderUtil;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.util.ModColors;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderApostleHand  extends RenderGeoExtended<EntityApostleHand> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.apostle_hand.json");

    public RenderApostleHand(RenderManager renderManager) {
        super(renderManager, new ModelApostleHand(MODEL_RESLOC, TEXTURE, "apostle_hand"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }


    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityApostleHand currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityApostleHand currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityApostleHand currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityApostleHand currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityApostleHand currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityApostleHand currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityApostleHand currentEntity) {
        return null;
    }
}
