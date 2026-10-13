package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.animation.AnimatorChampionAOE;
import com.unseen.helios.client.entity.util.RenderUtil;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.util.ModColors;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderChampionAOE extends AzEntityRenderer<EntityChampionAOE> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/champion_aoe.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/champion/geo.champion_aoe.json");


    public RenderChampionAOE(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityChampionAOE>builder(MODEL_RESLOC, TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setRenderEntry(contextPipeline -> {

                           // contextPipeline.animatable().animator.summonEntity();

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorChampionAOE::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(0F)
                        .build()
                , renderManager);
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
}
