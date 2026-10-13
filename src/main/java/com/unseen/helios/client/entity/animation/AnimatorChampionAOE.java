package com.unseen.helios.client.entity.animation;

import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnimatorChampionAOE extends AzEntityAnimator<EntityChampionAOE> {

    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityChampionAOE> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.IDLE_UPPER_CONTROLLER).build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(EntityChampionAOE entityChampionAOE) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.champion_aoe.json");
    }
}
