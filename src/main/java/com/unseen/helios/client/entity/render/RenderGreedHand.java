package com.unseen.helios.client.entity.render;

import com.unseen.helios.common.entity.entities.effects.greed_hand.AnimatorGreedHand;
import com.unseen.helios.common.entity.entities.effects.greed_hand.EntityGreedHand;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.vertex.RenderType;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGreedHand extends AzEntityRenderer<EntityGreedHand> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand_sleep.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.apostle_hand.json");

    public RenderGreedHand(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityGreedHand>builder(MODEL_RESLOC, TEXTURE)
                        .setRenderType(RenderType.entityTranslucent(TEXTURE))
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setRenderEntry(contextPipeline -> {

                            // contextPipeline.animatable().animator.summonEntity();

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorGreedHand::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(0.75F)
                        .build()
                , renderManager);
    }

}
