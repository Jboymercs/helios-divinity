package com.unseen.helios.common.entity.entities.apostle_hand;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.common.entity.entities.effects.greed_hand.EntityGreedHand;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehavior;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

public class ADApostleHand {

    private final String ANIM_IDLE = "idle";
    private final String ANIM_ARISE = "arise";
    private final String ANIM_IDLE_ROOTS = "idle_roots";
    private final String ANIM_CALL_PROJECTILE = "call_projectile";
    private final String ANIM_SELF_AOE = "self_aoe";

    private final EntityApostleHand mob;

    public ADApostleHand(EntityApostleHand mob) {
        this.mob = mob;
    }

    private final AzCommand idleRoots = AzCommand.create(AnimationStrings.IDLE_LOWER_CONTROLLER, ANIM_IDLE_ROOTS, AzPlayBehaviors.LOOP);

    private final AzCommand idleMode = AzCommand.create(AnimationStrings.IDLE_UPPER_CONTROLLER, ANIM_IDLE, AzPlayBehaviors.LOOP);

    private final AzCommand arise = AzCommand.create(AnimationStrings.ATTACK_CONTROLLER, ANIM_ARISE, AzPlayBehaviors.PLAY_ONCE);
    private final AzCommand call_projectile = AzCommand.create(AnimationStrings.ATTACK_CONTROLLER, ANIM_CALL_PROJECTILE, AzPlayBehaviors.PLAY_ONCE);
    private final AzCommand self_aoe = AzCommand.create(AnimationStrings.ATTACK_CONTROLLER, ANIM_SELF_AOE, AzPlayBehaviors.PLAY_ONCE);

    //sets all things needed to be idle
    public void setIdleMode() {
        idleMode.sendForEntity(mob);
    }

    public void setIdleRoots() {
        idleRoots.sendForEntity(mob);
    }

    public void set_Arise_Anim() {
        arise.sendForEntity(mob);
    }

    public void set_CallProjectile() {
        call_projectile.sendForEntity(mob);
    }

    public void set_SelfAOE() {
        self_aoe.sendForEntity(mob);
    }

}
