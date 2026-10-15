package com.unseen.helios.client.item.animation;

import com.unseen.helios.client.entity.animation.AnimationStrings;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ADispatcherItem {

    public void performItemAnimation(Entity entity, ItemStack stack, boolean loop, String animationString) {
        AzCommand command = AzCommand.create(AnimationStrings.STATES_CONTROLLER, animationString, loop ? AzPlayBehaviors.LOOP : AzPlayBehaviors.PLAY_ONCE);
        command.sendForItem(entity, stack);
    }

}
