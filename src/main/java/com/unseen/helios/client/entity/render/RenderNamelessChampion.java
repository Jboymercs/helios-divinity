package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelManusMinion;
import com.unseen.helios.client.entity.model.ModelNamelessChampion;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderNamelessChampion extends RenderGeoExtended<EntityNamelessChampion> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/champion/geo.nameless_champion.json");

    public RenderNamelessChampion(RenderManager renderManager) {
        super(renderManager, new ModelNamelessChampion(MODEL_RESLOC, TEXTURE, "nameless_champion"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityNamelessChampion currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityNamelessChampion currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityNamelessChampion currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityNamelessChampion currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityNamelessChampion currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityNamelessChampion currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityNamelessChampion currentEntity) {
        return null;
    }
}
