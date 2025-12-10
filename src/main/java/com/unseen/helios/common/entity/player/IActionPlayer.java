package com.unseen.helios.common.entity.player;

import com.unseen.helios.common.entity.ai.action.IAction;
import net.minecraft.entity.player.EntityPlayer;

public interface IActionPlayer {

    void performAction(EntityPlayer actor);

    IAction NONE = (actor, target) -> {
    };

}
