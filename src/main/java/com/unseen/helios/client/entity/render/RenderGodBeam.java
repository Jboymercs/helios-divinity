package com.unseen.helios.client.entity.render;

import com.unseen.helios.client.entity.util.RenderUtil;
import com.unseen.helios.common.entity.entities.attacks.god_beam.AnimatorGodBeam;
import com.unseen.helios.common.entity.entities.attacks.god_beam.EntityGodBeam;
import com.unseen.helios.util.ModColors;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderGodBeam extends AzEntityRenderer<EntityGodBeam> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam.png");
    private static final ResourceLocation TEXTURE_ONE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_1.png");
    private static final ResourceLocation TEXTURE_TWO = new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_2.png");
    private static final ResourceLocation TEXTURE_THREE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_3.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.god_beam.json");

    public RenderGodBeam(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityGodBeam>builder($ -> MODEL_RESLOC, entity ->
                                entity.ticksExisted < 5 ? TEXTURE_ONE :
                                        entity.ticksExisted < 10 && entity.ticksExisted > 5 ? TEXTURE_TWO :
                                                entity.ticksExisted < 15 && entity.ticksExisted > 10 ? TEXTURE_THREE :
                                                        TEXTURE
                                )
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setRenderEntry(contextPipeline -> {

                            // contextPipeline.animatable().animator.summonEntity();

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorGodBeam::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(0F)
                        .build()
                , renderManager);
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
}
