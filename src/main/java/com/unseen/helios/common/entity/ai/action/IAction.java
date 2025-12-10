package com.unseen.helios.common.entity.ai.action;

import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import net.minecraft.entity.EntityLivingBase;

/**
 * Works with the Attack Manager and helps condese code into there separate classes for attack sequences
 */
public interface IAction {
    void performAction(EntityHeliosMobBase actor, EntityLivingBase target);

    IAction NONE = (actor, target) -> {
    };
}
