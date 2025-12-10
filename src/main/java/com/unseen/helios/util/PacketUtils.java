package com.unseen.helios.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PacketUtils {
    public static World getWorld() {
        return Minecraft.getMinecraft().world;
    }



    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
