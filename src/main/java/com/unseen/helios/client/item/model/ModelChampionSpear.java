package com.unseen.helios.client.item.model;

import com.unseen.helios.common.items.tools.ItemChampionSpear;
import com.unseen.helios.common.items.tools.ItemGreatFinger;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelChampionSpear extends AnimatedGeoModel<ItemChampionSpear> {
    @Override
    public ResourceLocation getModelLocation(ItemChampionSpear object) {
        return new ResourceLocation(ModReference.MOD_ID, "geo/item/geo.champion_spear.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ItemChampionSpear object) {
        return new ResourceLocation(ModReference.MOD_ID, "textures/items/champion_spear.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemChampionSpear animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.champion_spear.json");
    }
}
