package com.unseen.helios.common.entity.entities.boss.action;

import com.unseen.helios.common.entity.ai.action.IAction;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.common.entity.projectile.ProjectileChampionOrb;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class ActionProjectileSplay implements IAction {

    @Override
    public void performAction(EntityHeliosMobBase actor, EntityLivingBase target) {
        ProjectileChampionOrb orb = new ProjectileChampionOrb(actor.world, actor, (float) (actor.getAttack()));
        ProjectileChampionOrb orb2 = new ProjectileChampionOrb(actor.world, actor, (float) (actor.getAttack()));
        ProjectileChampionOrb orb3 = new ProjectileChampionOrb(actor.world, actor, (float) (actor.getAttack()));
        ProjectileChampionOrb orb4 = new ProjectileChampionOrb(actor.world, actor, (float) (actor.getAttack()));
        ProjectileChampionOrb orb5 = new ProjectileChampionOrb(actor.world, actor, (float) (actor.getAttack()));

        Vec3d relPos = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(1.5, 1.5, 0)));

        orb.setPosition(relPos.x, relPos.y, relPos.z);
        orb2.setPosition(relPos.x, relPos.y, relPos.z);
        orb3.setPosition(relPos.x, relPos.y, relPos.z);
        orb4.setPosition(relPos.x, relPos.y, relPos.z);
        orb5.setPosition(relPos.x, relPos.y, relPos.z);

        orb.setTravelRange(30);
        orb2.setTravelRange(30);
        orb3.setTravelRange(30);
        orb4.setTravelRange(30);
        orb5.setTravelRange(30);
        orb.setNoGravity(true);
        orb2.setNoGravity(true);
        orb3.setNoGravity(true);
        orb4.setNoGravity(true);
        orb5.setNoGravity(true);

        float inaccuracy = 0.0f;
        float speed = 0.7f;


        orb.shoot(actor, 0, actor.rotationYaw, 0.0F, speed, inaccuracy);
        orb2.shoot(actor, 0, actor.rotationYaw + 15, 0.0F, speed, inaccuracy);
        orb3.shoot(actor, 0, actor.rotationYaw + 30, 0.0F, speed, inaccuracy);
        orb4.shoot(actor, 0, actor.rotationYaw - 15, 0.0F, speed, inaccuracy);
        orb5.shoot(actor, 0, actor.rotationYaw - 30, 0.0F, speed, inaccuracy);

        actor.world.spawnEntity(orb);
        actor.world.spawnEntity(orb2);
        actor.world.spawnEntity(orb3);
        actor.world.spawnEntity(orb4);
        actor.world.spawnEntity(orb5);

    }
}
