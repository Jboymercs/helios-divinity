package com.unseen.helios.client.item.model;

import com.unseen.helios.common.items.shield.ManusShield;
import com.unseen.helios.common.items.tools.ItemGreatFinger;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelGreatFinger extends AnimatedGeoModel<ItemGreatFinger> {
    @Override
    public ResourceLocation getModelLocation(ItemGreatFinger object) {
        return new ResourceLocation(ModReference.MOD_ID, "geo/item/geo.great_finger.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ItemGreatFinger object) {
        return new ResourceLocation(ModReference.MOD_ID, "textures/items/great_finger.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemGreatFinger animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.great_finger.json");
    }
}
