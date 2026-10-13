package com.unseen.helios.common.entity.entities.effects.greed_hand;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import com.unseen.helios.common.entity.entities.attacks.god_beam.EntityGodBeam;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

public class ADGreedHand {

    private final EntityGreedHand mob;

    public ADGreedHand(EntityGreedHand mob) {
        this.mob = mob;
    }

    private final AzCommand idleCommand = AzCommand
            .create(AnimationStrings.IDLE_CONTROLLER, "grab_player", AzPlayBehaviors.PLAY_ONCE);

    public void summonEntity() {
        idleCommand.sendForEntity(mob);
    }


}
