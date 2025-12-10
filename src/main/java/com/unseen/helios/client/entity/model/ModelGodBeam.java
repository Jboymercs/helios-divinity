package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.attacks.EntityGodBeam;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;

public class ModelGodBeam extends GeoModelExtended<EntityGodBeam> {


    public ModelGodBeam(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGodBeam animatable) {
        if(animatable.ticksExisted < 5) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_1.png");
        } else if(animatable.ticksExisted < 10 && animatable.ticksExisted > 5) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_2.png");
        } else if(animatable.ticksExisted < 15 && animatable.ticksExisted > 10) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam_3.png");
        }else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/god_beam/god_beam.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGodBeam animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.god_beam.json");
    }
}
