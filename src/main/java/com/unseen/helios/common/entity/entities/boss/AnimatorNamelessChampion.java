package com.unseen.helios.common.entity.entities.boss;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.util.ModReference;
import com.unseen.helios.util.ModUtils;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.state.AzAnimationState;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.model.AzBakedModel;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.model.AzBoneSnapshot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class AnimatorNamelessChampion extends AzEntityAnimator<EntityNamelessChampion> {

    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityNamelessChampion> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, AnimationStrings.IDLE_LOWER_CONTROLLER).build(),
                AzAnimationController.builder(this, AnimationStrings.WALK_LOWER_CONTROLLER).build(),
                AzAnimationController.builder(this, AnimationStrings.ATTACK_CONTROLLER).setTransitionLength(3).build(),
                AzAnimationController.builder(this, AnimationStrings.STATES_CONTROLLER).build()
        );
    }

    @Nonnull
    @Override
    public ResourceLocation getAnimationLocation(EntityNamelessChampion entityNamelessChampion) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.nameless_champion.json");
    }

    @Override
    public void setCustomAnimations(EntityNamelessChampion entity, float partialTicks) {
        super.setCustomAnimations(entity, partialTicks);
        AzBakedModel model = this.context().boneCache().getBakedModel();
        AzBone head = model.getBoneOrNull("Head");

        if (head == null) {
            return;
        }

        // Where the entity is looking, relative to its body, in degrees.
        float bodyYaw = ModUtils.rotLerp(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset);
        float headYaw = MathHelper.wrapDegrees(ModUtils.rotLerp(partialTicks, entity.prevRotationYawHead, entity.rotationYawHead) - bodyYaw);
        float headPitch = ModUtils.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch);

        if(!entity.isSleepMode() && !entity.isIdleWake() && !entity.isCastGreed() && !entity.isTransformPhase()) {
            AzBoneSnapshot initial = head.getInitialAzSnapshot();
            head.setRotX(initial.getRotX() + headPitch * ((float)Math.PI / 180F));
            head.setRotY(initial.getRotY() + headYaw * ((float)Math.PI / 180F));
        }
    }
}
