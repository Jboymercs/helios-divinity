package com.unseen.helios.client.item.render;

import com.unseen.helios.client.item.animation.AnimatorGreatFinger;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.util.ResourceLocation;

public class RenderGreatFinger  extends AzItemRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/items/great_finger.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/item/geo.great_finger.json");

    public RenderGreatFinger() {
        super(
                AzItemRendererConfig.builder(itemStack -> MODEL_RESLOC, itemStack -> TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>()).setAnimatorProvider(AnimatorGreatFinger::new)
                        .build()
        );
    }
}
