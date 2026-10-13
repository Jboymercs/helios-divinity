package com.unseen.helios.common.entity.entities.apostle_hand;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnimatorApostleHand extends AzEntityAnimator<EntityApostleHand> {

    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityApostleHand> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.IDLE_UPPER_CONTROLLER).build(),
                AzAnimationController.builder(this, AnimationStrings.STATES_CONTROLLER).build(),
                AzAnimationController.builder(this, AnimationStrings.IDLE_LOWER_CONTROLLER).build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(EntityApostleHand entityApostleHand) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.apostle_hand.json");
    }
}
