package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.effects.EntityGreedHand;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;

public class ModelGreedHand extends GeoModelExtended<EntityGreedHand> {

    public ModelGreedHand(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGreedHand animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.apostle_hand.json");
    }
}
