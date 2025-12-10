package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelApostleHand;
import com.unseen.helios.client.entity.model.ModelGreedHand;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.effects.EntityGreedHand;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderGreedHand extends RenderGeoExtended<EntityGreedHand> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand_sleep.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.apostle_hand.json");

    public RenderGreedHand(RenderManager renderManager) {
        super(renderManager, new ModelGreedHand(MODEL_RESLOC, TEXTURE, "greed_hand"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityGreedHand currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityGreedHand currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityGreedHand currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityGreedHand currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityGreedHand currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityGreedHand currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityGreedHand currentEntity) {
        return null;
    }
}
