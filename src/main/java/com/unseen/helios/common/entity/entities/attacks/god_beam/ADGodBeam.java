package com.unseen.helios.common.entity.entities.attacks.god_beam;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

public class ADGodBeam {

    private final EntityGodBeam championAOE;

    public ADGodBeam(EntityGodBeam championAOE) {
        this.championAOE = championAOE;
    }

    private final AzCommand idleCommand = AzCommand
            .create(AnimationStrings.IDLE_CONTROLLER, "summon", AzPlayBehaviors.PLAY_ONCE);

    public void summonEntity() {
        idleCommand.sendForEntity(championAOE);
    }
}
