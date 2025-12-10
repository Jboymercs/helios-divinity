package com.unseen.helios.common.entity.tileEntities;

import com.google.common.collect.Lists;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.lang.ref.WeakReference;
import java.util.List;

public class TileEntityManusAltar extends TileEntity implements ITickable {

    private List<WeakReference<EntityManusAvaritia>> current_hand = Lists.newArrayList();

    @Override
    public void update() {

    }
}
