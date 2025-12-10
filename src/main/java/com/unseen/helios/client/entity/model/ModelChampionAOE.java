package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;

public class ModelChampionAOE extends GeoModelExtended<EntityChampionAOE> {

    public ModelChampionAOE(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityChampionAOE animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.champion_aoe.json");
    }
}
