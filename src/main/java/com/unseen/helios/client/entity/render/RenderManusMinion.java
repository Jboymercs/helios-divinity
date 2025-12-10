package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelManusAvaritia;
import com.unseen.helios.client.entity.model.ModelManusMinion;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.common.entity.entities.EntityManusMinion;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderManusMinion extends RenderGeoExtended<EntityManusMinion> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_minion.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/manus/geo.manus_minion.json");

    public RenderManusMinion(RenderManager renderManager) {
        super(renderManager, new ModelManusMinion(MODEL_RESLOC, TEXTURE, "manus_minion"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityManusMinion currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityManusMinion currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityManusMinion currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityManusMinion currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityManusMinion currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityManusMinion currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityManusMinion currentEntity) {
        return null;
    }
}
