package com.unseen.helios.common.entity.entities.manus_avatiria;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

public class ADManusAvaritia {

    private final String ANIM_IDLE = "idle_awake";
    private final String ANIM_IDLE_ROOTS = "idle_roots";

    private final EntityManusAvaritia mob;
    private final AzCommand idleRoots = AzCommand.create(AnimationStrings.IDLE_LOWER_CONTROLLER, ANIM_IDLE_ROOTS, AzPlayBehaviors.LOOP);
    private final AzCommand idleMode = AzCommand.create(AnimationStrings.IDLE_UPPER_CONTROLLER, ANIM_IDLE, AzPlayBehaviors.LOOP);
    private final AzCommand idleAsleep = AzCommand.create(AnimationStrings.IDLE_UPPER_CONTROLLER, ANIM_IDLE, AzPlayBehaviors.LOOP);
    public ADManusAvaritia(EntityManusAvaritia mob) {
        this.mob = mob;
    }

    public void setIdleMode(boolean isAsleep) {
        if(isAsleep) {
            idleAsleep.sendForEntity(mob);
        } else {
            idleMode.sendForEntity(mob);
        }
    }

    public void setRootsIdle() {
        idleRoots.sendForEntity(mob);
    }

    public void setStatusUpdate(String animationString) {
        AzCommand command = AzCommand.create(AnimationStrings.STATES_CONTROLLER, animationString, AzPlayBehaviors.PLAY_ONCE);
        command.sendForEntity(mob);
    }
}
