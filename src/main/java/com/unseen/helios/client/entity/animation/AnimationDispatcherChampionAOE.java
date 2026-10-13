package com.unseen.helios.client.entity.animation;

import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

public class AnimationDispatcherChampionAOE {

    private final EntityChampionAOE championAOE;

    public AnimationDispatcherChampionAOE(EntityChampionAOE championAOE) {
        this.championAOE = championAOE;
    }

    private final AzCommand idleCommand = AzCommand
            .create(AnimationStrings.IDLE_CONTROLLER, "summon", AzPlayBehaviors.PLAY_ONCE);

    public void summonEntity() {
        idleCommand.sendForEntity(championAOE);
    }

}
