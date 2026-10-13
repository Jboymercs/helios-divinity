package com.unseen.helios.client.entity.render;

import com.unseen.helios.common.entity.entities.apostle_hand.AnimatorApostleHand;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderApostleHand extends AzEntityRenderer<EntityApostleHand> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/apostle/geo.apostle_hand.json");

    public RenderApostleHand(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityApostleHand>builder(MODEL_RESLOC, TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setRenderEntry(contextPipeline -> {
                            if(!contextPipeline.animatable().isArise()) {
                                contextPipeline.animatable().animator.setIdleRoots();
                            }
                            if(!contextPipeline.animatable().isFightMode()) {
                                contextPipeline.animatable().animator.setIdleMode();
                            }
                            // contextPipeline.animatable().animator.summonEntity();

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorApostleHand::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(1F)
                        .build()
                , renderManager);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(EntityApostleHand animatable) {
        if(animatable.isSleepEyes()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand_sleep.png");
        } else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand.png");
        }
    }

}
