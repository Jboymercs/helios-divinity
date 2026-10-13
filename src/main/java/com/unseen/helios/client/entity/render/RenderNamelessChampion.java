package com.unseen.helios.client.entity.render;

import com.unseen.helios.common.entity.entities.boss.AnimatorNamelessChampion;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderNamelessChampion extends AzEntityRenderer<EntityNamelessChampion> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/champion/geo.nameless_champion.json");

    public RenderNamelessChampion(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityNamelessChampion>builder(MODEL_RESLOC, TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setRenderEntry(contextPipeline -> {
                            // contextPipeline.animatable().animator.summonEntity();

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorNamelessChampion::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(1F)
                        .build()
                , renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityNamelessChampion animatable) {
        if(animatable.isGreedy()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion_greed.png");
        } else if(animatable.isSleepMode()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion_sleep.png");
        } else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion.png");
        }
    }
}
