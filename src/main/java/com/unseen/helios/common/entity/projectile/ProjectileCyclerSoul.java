package com.unseen.helios.common.entity.projectile;

import com.unseen.helios.Main;
import com.unseen.helios.common.blocks.BlockRemnantCycler;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.common.entity.tileEntities.TileEntityRemnantCycler;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ProjectileCyclerSoul extends Projectile {

    private BlockPos posMoveToo;

    public ProjectileCyclerSoul(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
    }

    public ProjectileCyclerSoul(World worldIn, EntityLivingBase throwerIn, float damage, BlockPos posTooMove) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
        this.isLocatorItem = true;
        this.posMoveToo = posTooMove;
    }

    public ProjectileCyclerSoul(World worldIn) {
        super(worldIn);
    }

    public ProjectileCyclerSoul(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(ticksExisted < 6) {
            ModUtils.setEntityPosition(this, new Vec3d(this.posX, this.posY, this.posZ));
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        } else if (posMoveToo != null){
            this.onTrackUpdate();
        }

    }

    public void onTrackUpdate() {

        if(world.getBlockState(posMoveToo).getBlock() == ModBlocks.REMNANT_RECYCLER) {
            Vec3d posToTravelToo = new Vec3d(posMoveToo.getX() + 0.5, posMoveToo.getY() + 0.3, posMoveToo.getZ() + 0.5);
            Vec3d currPos = this.getPositionVector();
            Vec3d dir = posToTravelToo.subtract(currPos).normalize();
            ModUtils.addEntityVelocity(this, dir.scale(0.01 * 0.4));
            TileEntity tileentity = world.getTileEntity(posMoveToo);
            if(this.getDistanceSq(posMoveToo) < 3) {
                if(tileentity instanceof TileEntityRemnantCycler) {
                    TileEntityRemnantCycler cycler = ((TileEntityRemnantCycler)tileentity);
                    cycler.timesUsed++;
                    this.setDead();
                }
            }
        } else {
            this.setDead();
        }
    }

    public static final int PARTICLE_AMOUNT = 1;

    @Override
    protected void spawnParticles() {
        for (int i = 0; i < PARTICLE_AMOUNT; i++) {
            if(world.rand.nextInt(2) == 0) {
                Main.proxy.spawnParticle(1, this.posX, this.posY, this.posZ, 0, 0, 0, 14614527);
            }
        }
    }
}
