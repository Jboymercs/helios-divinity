package com.unseen.helios.client.item.model;

import com.unseen.helios.common.items.shield.ManusShield;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelManusShield extends AnimatedGeoModel<ManusShield> {
    @Override
    public ResourceLocation getModelLocation(ManusShield object) {
        return new ResourceLocation(ModReference.MOD_ID, "geo/item/geo.manus_shield.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ManusShield object) {
        return new ResourceLocation(ModReference.MOD_ID, "textures/items/manus_shield.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ManusShield animatable) {
        return null;
    }
}
