package com.unseen.helios.common.entity.entities.attacks.god_beam;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnimatorGodBeam extends AzEntityAnimator<EntityGodBeam> {
    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityGodBeam> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.IDLE_CONTROLLER).build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(EntityGodBeam entityGodBeam) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.god_beam.json");
    }
}
