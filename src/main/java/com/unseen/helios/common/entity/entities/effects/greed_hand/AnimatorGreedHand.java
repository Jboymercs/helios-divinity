package com.unseen.helios.common.entity.entities.effects.greed_hand;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnimatorGreedHand  extends AzEntityAnimator<EntityGreedHand> {

    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityGreedHand> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.IDLE_CONTROLLER).build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(EntityGreedHand entityGreedHand) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.apostle_hand.json");
    }
}
