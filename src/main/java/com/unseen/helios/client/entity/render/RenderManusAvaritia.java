package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelManusAvaritia;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderManusAvaritia extends RenderGeoExtended<EntityManusAvaritia> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_avaritia.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/manus/geo.manus_avaritia.json");

    public RenderManusAvaritia(RenderManager renderManager) {
        super(renderManager, new ModelManusAvaritia(MODEL_RESLOC, TEXTURE, "manus_avaritia"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityManusAvaritia currentEntity) {
        ItemStack stackInhand = currentEntity.getItemFromRoyalHand(EntityManusAvaritia.TRADER_HAND.getFromBoneName(boneName));
        if(stackInhand != null) {
            return stackInhand;
        }
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityManusAvaritia currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityManusAvaritia currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityManusAvaritia currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityManusAvaritia currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityManusAvaritia currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityManusAvaritia currentEntity) {
        return null;
    }
}
