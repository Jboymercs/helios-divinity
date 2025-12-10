package com.unseen.helios.client.entity.model;

import com.unseen.helios.client.entity.base.GeoModelExtended;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.common.entity.entities.EntityManusMinion;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelNamelessChampion extends GeoModelExtended<EntityNamelessChampion> {
    public ModelNamelessChampion(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityNamelessChampion animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.nameless_champion.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNamelessChampion animatable) {
        if(animatable.isGreedy()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion_greed.png");
        } else if(animatable.isSleepMode()) {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion_sleep.png");
        } else {
            return new ResourceLocation(ModReference.MOD_ID, "textures/entity/nameless_champion.png");
        }
    }

    @Override
    public void setLivingAnimations(EntityNamelessChampion entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if(!entity.isSleepMode() && !entity.isIdleWake() && !entity.isCastGreed() && !entity.isTransformPhase()) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }

    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
