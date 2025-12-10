package com.unseen.helios.client.entity;

import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDirectionalRender {
    @SideOnly(Side.CLIENT)
    public void setRenderDirection(Vec3d dir);
}
