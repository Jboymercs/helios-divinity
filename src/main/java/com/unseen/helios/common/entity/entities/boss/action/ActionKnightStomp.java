package com.unseen.helios.common.entity.entities.boss.action;

import com.unseen.helios.common.entity.ai.action.IAction;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ActionKnightStomp implements IAction {

    //15,12,9,6,3
    @Override
    public void performAction(EntityHeliosMobBase actor, EntityLivingBase target) {
        if(actor.getDistance(target) > 14) {
            ModUtils.circleCallback(16, 48, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
                int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 5, (int) actor.posY + 4);
                EntityChampionAOE spike = new EntityChampionAOE(actor.world, actor);
                spike.setPosition(pos.x, y + 1, pos.z);
                actor.world.spawnEntity(spike);
            });
        } else if (actor.getDistance(target) > 11) {
            ModUtils.circleCallback(12, 32, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
                int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 5, (int) actor.posY + 4);
                EntityChampionAOE spike = new EntityChampionAOE(actor.world, actor);
                spike.setPosition(pos.x, y + 1, pos.z);
                actor.world.spawnEntity(spike);
            });
        } else if (actor.getDistance(target) > 8) {
            ModUtils.circleCallback(9, 20, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
                int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 5, (int) actor.posY + 4);
                EntityChampionAOE spike = new EntityChampionAOE(actor.world, actor);
                spike.setPosition(pos.x, y + 1, pos.z);
                actor.world.spawnEntity(spike);
            });
        } else if (actor.getDistance(target) > 5) {
            ModUtils.circleCallback(6, 12, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
                int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 5, (int) actor.posY + 4);
                EntityChampionAOE spike = new EntityChampionAOE(actor.world, actor);
                spike.setPosition(pos.x, y + 1, pos.z);
                actor.world.spawnEntity(spike);
            });
        } else if (actor.getDistance(target) > 2) {
            ModUtils.circleCallback(3, 7, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
                int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 5, (int) actor.posY + 4);
                EntityChampionAOE spike = new EntityChampionAOE(actor.world, actor);
                spike.setPosition(pos.x, y + 1, pos.z);
                actor.world.spawnEntity(spike);
            });
        }
    }


    public int getSurfaceHeight(World world, BlockPos pos, int min, int max)
    {
        int currentY = max;

        while(currentY >= min)
        {
            if(!world.isAirBlock(pos.add(0, currentY, 0)) && !world.isRemote && world.getBlockState(pos.add(0, currentY, 0)).isFullBlock()) {
                return currentY;
            }

            currentY--;
        }

        return 0;
    }
}
