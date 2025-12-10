package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class ModelManusAvaritia extends GeoModelExtended<EntityManusAvaritia> {
    public ModelManusAvaritia(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityManusAvaritia animatable) {
        if(animatable.isSleepMode()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_avaritia_sleep.png");
        } else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_avaritia.png");
        }
    }


    @Override
    public ResourceLocation getAnimationFileLocation(EntityManusAvaritia animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.manus_avaritia.json");
    }
}
