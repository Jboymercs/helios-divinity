package com.unseen.helios.common.entity.entities.boss.action;

import com.unseen.helios.common.entity.ai.action.IAction;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ActionKnightLineAOE implements IAction {


    @Override
    public void performAction(EntityHeliosMobBase actor, EntityLivingBase target) {

        for(int i = 0; i <= 21; i += 3) {
            int finalI = i;
            actor.addEvent(()-> {
                EntityChampionAOE bolt5 = new EntityChampionAOE(actor.world, actor);
                Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(finalI, 0, 0)));
                int yVar5 = ModUtils.getSurfaceHeightGeneral(actor.world, new BlockPos(relPos5.x, relPos5.y, relPos5.z), (int) actor.posY - 4, (int) actor.posY + 3);
                bolt5.setPosition(relPos5.x, yVar5, relPos5.z);
                actor.world.spawnEntity(bolt5);
            }, (int) (i));
        }

        if(actor.getHealth() / actor.getMaxHealth() < 0.5) {
            this.doLeftLine(actor);
            this.doRightLine(actor);
        }
    }


    private void doRightLine(EntityHeliosMobBase actor) {
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(3, 0, 3)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 1);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(6, 0, 6)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 3);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(9, 0, 9)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 6);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(12, 0, 12)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 9);
    }

    private void doLeftLine(EntityHeliosMobBase actor) {
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(3, 0, -3)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 1);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(6, 0, -6)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 3);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(9, 0, -9)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 6);
        actor.addEvent(() -> {
            EntityChampionAOE bolt = new EntityChampionAOE(actor.world, actor);
            Vec3d relPos5 = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(12, 0, -12)));
            bolt.setPosition(relPos5.x, relPos5.y, relPos5.z);
            actor.world.spawnEntity(bolt);
        }, 9);
    }
}
