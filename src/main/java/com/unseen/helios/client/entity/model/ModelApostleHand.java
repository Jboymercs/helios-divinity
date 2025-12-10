package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;

public class ModelApostleHand extends GeoModelExtended<EntityApostleHand> {
    public ModelApostleHand(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityApostleHand animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.apostle_hand.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityApostleHand animatable) {
        if(animatable.isSleepEyes()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand_sleep.png");
        } else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/apostle_hand.png");
        }
    }
}
