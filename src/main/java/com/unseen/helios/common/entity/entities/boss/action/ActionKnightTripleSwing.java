package com.unseen.helios.common.entity.entities.boss.action;

import com.unseen.helios.common.entity.ai.action.IAction;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ActionKnightTripleSwing implements IAction {
    Supplier<Projectile> projectileSupplier;
    float velocity;

    public ActionKnightTripleSwing(Supplier<Projectile> projectileSupplier, float velocity) {
        this.projectileSupplier = projectileSupplier;
        this.velocity = velocity;
    }


    @Override
    public void performAction(EntityHeliosMobBase actor, EntityLivingBase target) {
            Vec3d targetPos = target.getPositionEyes(1);
            Vec3d fromTargetToActor = actor.getPositionVector().subtract(targetPos);
            // actor.playSound(SoundsHandler.BLOSSOM_PETAL_WAVE, 2.0f, 1.0f);
            Vec3d lineDirection = ModUtils.rotateVector2(
                            fromTargetToActor.crossProduct(ModUtils.Y_AXIS),
                            fromTargetToActor,
                            ModRand.range(-45, 45))
                    .normalize()
                    .scale(4);

            Vec3d lineStart = targetPos.subtract(lineDirection);
            Vec3d lineEnd = targetPos.add(lineDirection);

            ModUtils.lineCallback(lineStart, lineEnd, 7, (pos, i) -> {
                Projectile projectile = projectileSupplier.get();
                projectile.setTravelRange(30);
                projectile.setNoGravity(true);
                ModUtils.throwProjectile(actor, pos, projectile, 0, velocity);
            });
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
