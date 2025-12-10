package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelChampionAOE;
import com.unseen.helios.client.entity.model.ModelNamelessChampion;
import com.unseen.helios.client.entity.util.RenderUtil;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.common.entity.entities.attacks.EntityGodBeam;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModColors;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderChampionAOE extends RenderGeoExtended<EntityChampionAOE> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/champion_aoe.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/champion/geo.champion_aoe.json");

    public RenderChampionAOE(RenderManager renderManager) {
        super(renderManager, new ModelChampionAOE(MODEL_RESLOC, TEXTURE, "champion_aoe"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Override
    public void doRender(EntityChampionAOE entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if (entity.getRenderDirection() != null && entity.ticksExisted > 15 ) {
            double scale = 1;
            if(entity.ticksExisted > 20) {
                scale -= 0.11;
            }
          //  RenderUtil.drawBeam(renderManager, entity.getPositionVector(), entity.getRenderDirection(), new Vec3d(x, y, z), ModColors.MAELSTROM, entity, partialTicks, new Vec3d(scale * 3, 1, scale * 3));
            RenderUtil.drawLazer(renderManager, entity.getPositionVector(), entity.getRenderDirection(), new Vec3d(x,y,z), ModColors.MAELSTROM, entity, partialTicks, new Vec3d(4, 1, 4));
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityChampionAOE currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityChampionAOE currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityChampionAOE currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityChampionAOE currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityChampionAOE currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityChampionAOE currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityChampionAOE currentEntity) {
        return null;
    }
}
