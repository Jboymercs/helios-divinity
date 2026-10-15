package com.unseen.helios.common.entity.entities.boss;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

/**
 * We want to see what a simplified manner of creating this class looks like for further implementation across other mods.
 */
public class ADNamelessChampion {

    //states
    private final String ANIM_IDLE = "idle";
    private final String ANIM_WALK = "walk";

    private final EntityNamelessChampion mob;

    public ADNamelessChampion(EntityNamelessChampion mob) {
        this.mob = mob;
    }

    private final AzCommand idleMode = AzCommand.create(AnimationStrings.IDLE_LOWER_CONTROLLER, ANIM_IDLE, AzPlayBehaviors.LOOP);
    private final AzCommand walkMode = AzCommand.create(AnimationStrings.WALK_LOWER_CONTROLLER, ANIM_WALK, AzPlayBehaviors.LOOP);

    public void handleIdleStates(boolean walking) {
        if(walking) {
            walkMode.sendForEntity(mob);
        } else {
            idleMode.sendForEntity(mob);
        }
    }

    public void sendTooWalk() {walkMode.sendForEntity(mob);}
    public void sendTooIdle() {idleMode.sendForEntity(mob);}

    public void performAttack(String animationString) {
        AzCommand command = AzCommand.create(AnimationStrings.ATTACK_CONTROLLER, animationString, AzPlayBehaviors.PLAY_ONCE);
        command.sendForEntity(mob);
    }

    public void setStateAnimation(String animationString) {
        AzCommand command = AzCommand.create(AnimationStrings.STATES_CONTROLLER, animationString, AzPlayBehaviors.PLAY_ONCE);
        command.sendForEntity(mob);
    }

    public void setStateAnimation(String animationString, boolean loop) {
        AzCommand command = AzCommand.create(AnimationStrings.STATES_CONTROLLER, animationString, loop ? AzPlayBehaviors.LOOP : AzPlayBehaviors.PLAY_ONCE);
        command.sendForEntity(mob);
    }
}
