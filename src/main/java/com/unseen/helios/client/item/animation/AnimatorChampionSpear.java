package com.unseen.helios.client.item.animation;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzItemAnimator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnimatorChampionSpear extends AzItemAnimator {

    public AnimatorChampionSpear() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.STATES_CONTROLLER)
                        .build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(ItemStack stack) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.champion_spear.json");
    }
}
