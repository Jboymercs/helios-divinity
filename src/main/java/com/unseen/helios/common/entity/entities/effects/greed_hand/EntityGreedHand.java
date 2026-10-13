package com.unseen.helios.common.entity.entities.effects.greed_hand;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.IScreenShake;
import com.unseen.helios.common.entity.entities.attacks.god_beam.ADGodBeam;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityGreedHand extends EntityHeliosMobBase implements IScreenShake {
    private static final DataParameter<Boolean> SHAKING = EntityDataManager.createKey(EntityGreedHand.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GRAB_PLAYER_ATTACK = EntityDataManager.createKey(EntityGreedHand.class, DataSerializers.BOOLEAN);
    private int shakeTime = 0;
    private void setShaking(boolean value) {this.dataManager.set(SHAKING, Boolean.valueOf(value));}
    public boolean isShaking() {return this.dataManager.get(SHAKING);}
    private void setGrabPlayerAttack(boolean value) {this.dataManager.set(GRAB_PLAYER_ATTACK, Boolean.valueOf(value));}
    public boolean isGrabPlayerAttack() {return this.dataManager.get(GRAB_PLAYER_ATTACK);}
    public final ADGreedHand animator;
    public EntityGreedHand(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setSize(1.75F, 4.5F);
        this.isNonHealableCreature = true;
        this.experienceValue = 75;
        this.setImmovable(true);
        this.summonMob();
        this.animator = new ADGreedHand(this);
    }

    private EntityLivingBase playerIn;

    public EntityGreedHand(World worldIn, float x, float y, float z, EntityLivingBase playerIn) {
        super(worldIn, x, y, z);
        this.setSize(1.75F, 4.5F);
        this.isNonHealableCreature = true;
        this.experienceValue = 75;
        this.noClip = true;
        this.setNoGravity(true);
        this.setImmovable(true);
        this.playerIn = playerIn;
        this.summonMob();
        this.animator = new ADGreedHand(this);
    }

    public EntityGreedHand(World worldIn) {
        super(worldIn);
        this.setSize(1.75F, 4.5F);
        this.isNonHealableCreature = true;
        this.experienceValue = 75;
        this.setImmovable(true);
        this.summonMob();
        this.animator = new ADGreedHand(this);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("Shaking", this.isShaking());
        nbt.setBoolean("Grab_Player", this.isGrabPlayerAttack());
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setGrabPlayerAttack(nbt.getBoolean("Grab_Player"));
        this.setShaking(nbt.getBoolean("Shaking"));
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(GRAB_PLAYER_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SHAKING, Boolean.valueOf(false));
    }

    private void summonMob() {
        this.setGrabPlayerAttack(true);
        this.setFightMode(true);
        this.setImmovable(true);
        this.shakeTime = 80;
        this.setShaking(true);
        ModUtils.destroyBlocksInAABB(this.getEntityBoundingBox().grow(1.25D).offset(0, 1.25,0), world, this);

        addEvent(()-> {
            this.playSound(ModSoundHandler.APOSTLE_SUMMON, 1.5f, 1.0F);
        }, 5);

        addEvent(()-> {
             for(int i = 0; i < 20; i+= 5) {
                 addEvent(()-> {
                     if(playerIn != null) {
                         this.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 0.65f, 1.0F);
                         playerIn.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 6);
                         playerIn.hurtResistantTime = 0;

                     }
                 }, i);
             }
        }, 45);
        addEvent(()-> {
            this.setShaking(false);
        }, 90);
        addEvent(()-> {
            this.setGrabPlayerAttack(false);
            this.setFightMode(false);
            this.setDead();
        }, 120);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setRotation(0, 0);
        this.setRotationYawHead(0);
        this.shakeTime--;
        this.lockLook = true;

        if(world.rand.nextInt(2) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
        if(!world.isRemote) {
            if(playerIn != null) {
                if(playerIn.canBePushed()) {
                    Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 0.1, 0)));
                    playerIn.setPosition(offset.x, offset.y, offset.z);
                    playerIn.setPositionAndUpdate(offset.x, offset.y, offset.z);
                }
            }
        }
    }

    @Override
    public float getShakeIntensity(Entity viewer, float partialTicks) {
        if(this.isShaking()) {
            double dist = getDistance(viewer);
            float screamMult = (float) (1.0F - dist / 24.0F);
            if (dist >= 24.0F) {
                return 0.0F;
            }
            return (float) ((Math.sin(((partialTicks)/this.shakeTime) * Math.PI) + 0.5F) * 1.1F * screamMult);
        }
        return 0;
    }

    public void updateAnimations() {
        animator.summonEntity();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
            return false;
    }


    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ModUtils.performNTimes(5, (i) -> {
                Main.proxy.spawnParticle(1, this.posX + ModRand.getFloat(6), this.posY + ModRand.getFloat(4), this.posZ + ModRand.getFloat(6), 0, 0.1, 0, 9320921);
            });
        }
    }
}
