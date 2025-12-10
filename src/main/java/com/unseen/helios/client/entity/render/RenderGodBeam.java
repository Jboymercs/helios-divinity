package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.base.RenderGeoExtended;
import com.unseen.helios.client.entity.base.layer.GeoSpecificGlow;
import com.unseen.helios.client.entity.model.ModelApostleHand;
import com.unseen.helios.client.entity.model.ModelGodBeam;
import com.unseen.helios.client.entity.util.RenderUtil;
import com.unseen.helios.common.entity.entities.attacks.EntityGodBeam;
import com.unseen.helios.util.ModColors;
import com.unseen.helios.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class RenderGodBeam extends RenderGeoExtended<EntityGodBeam> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.god_beam.json");

    public RenderGodBeam(RenderManager renderManager) {
        super(renderManager, new ModelGodBeam(MODEL_RESLOC, TEXTURE, "god_beam"));
        this.addLayer(new GeoSpecificGlow<>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));
    }

    @Override
    public void doRender(EntityGodBeam entity, double x, double y, double z, float entityYaw, float partialTicks) {
        renderManager.renderEngine.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
        if (entity.getRenderDirection() != null && entity.ticksExisted > 15 ) {
            double scale = 1;
            if(entity.ticksExisted > 20) {
                scale -= 0.11;
            }
            RenderUtil.drawBeam(renderManager, entity.getPositionVector(), entity.getRenderDirection(), new Vec3d(x, y, z), ModColors.MAELSTROM, entity, partialTicks, new Vec3d(scale * 3, 1, scale * 3));
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityGodBeam currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityGodBeam currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityGodBeam currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityGodBeam currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityGodBeam currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityGodBeam currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityGodBeam currentEntity) {
        return null;
    }
}
