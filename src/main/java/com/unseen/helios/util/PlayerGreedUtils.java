package com.unseen.helios.util;

import com.unseen.helios.capability.CapabilityPlayerGreed;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerGreedUtils {


    public static float getPlayerGreedProgress(EntityPlayer playerIn)
    {
        CapabilityPlayerGreed.IPlayerGreedCapability greed = getCapability(playerIn);
        if (greed == null) return 0.0F;

        return greed.getPlayerGreedProgress();
    }

    public static void setPlayerGreedProgress(EntityPlayer playerIn, int val) {
        CapabilityPlayerGreed.IPlayerGreedCapability greed = getCapability(playerIn);

        assert greed != null;
        greed.setPlayerGreedProgress(val);
    }


    public static CapabilityPlayerGreed.IPlayerGreedCapability getCapability(EntityPlayer playerIn)
    {
        if (playerIn.hasCapability(CapabilityPlayerGreed.PLAYER_GREED_CAP, null))
        { return playerIn.getCapability(CapabilityPlayerGreed.PLAYER_GREED_CAP, null); }

        return null;
    }
}
