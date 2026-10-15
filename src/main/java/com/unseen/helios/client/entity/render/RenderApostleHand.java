package com.unseen.helios.client.entity.render;

import com.unseen.helios.common.entity.entities.apostle_hand.AnimatorApostleHand;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.vertex.RenderType;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderApostleHand extends AzEntityRenderer<EntityApostleHand> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand.png");
    private static final ResourceLocation TEXTURE_SLEEP = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand_sleep.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.apostle_hand.json");

    public RenderApostleHand(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityApostleHand>builder($ -> MODEL_RESLOC, entity ->
                                entity.isSleepEyes() ? TEXTURE_SLEEP: TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>()).setRenderType(RenderType.entityTranslucent(TEXTURE))
                        .setRenderEntry(contextPipeline -> {

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorApostleHand::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(0.75F)
                        .build()
                , renderManager);
    }

}
