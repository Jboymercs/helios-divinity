package com.unseen.helios.common.entity.entities.attacks;

import com.unseen.helios.Main;
import com.unseen.helios.client.entity.IDirectionalRender;
import com.unseen.helios.client.entity.animation.AnimationDispatcherChampionAOE;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.IScreenShake;
import com.unseen.helios.events.MessageDirectionForRender;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityChampionAOE extends EntityHeliosMobBase implements IDirectionalRender, IScreenShake {

    private Vec3d renderLazerPos;
    private int shakeTime = 0;

    private static final DataParameter<Boolean> SHAKING = EntityDataManager.createKey(EntityChampionAOE.class, DataSerializers.BOOLEAN);
    private void setShaking(boolean value) {this.dataManager.set(SHAKING, Boolean.valueOf(value));}
    public boolean isShaking() {return this.dataManager.get(SHAKING);}
    public final AnimationDispatcherChampionAOE animator;


    public EntityChampionAOE(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.setSize(1.5F, 0.4F);
        this.animator = new AnimationDispatcherChampionAOE(this);
    }

    public EntityChampionAOE(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.setSize(1.5F, 0.3F);
        this.animator = new AnimationDispatcherChampionAOE(this);
    }

    private EntityLivingBase owner;

    public EntityChampionAOE (World worldIn, EntityLivingBase owner) {
        super(worldIn);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.owner = owner;
        this.setSize(1.5F, 0.3F);
        this.animator = new AnimationDispatcherChampionAOE(this);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("Shaking", this.isShaking());
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setShaking(nbt.getBoolean("Shaking"));
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(SHAKING, Boolean.valueOf(false));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX = 0;
        this.motionZ = 0;
        this.rotationYaw = 0;
        this.rotationPitch = 0;
        this.rotationYawHead = 0;
        this.renderYawOffset = 0;
        this.shakeTime--;

        if(ticksExisted == 15) {
            this.setShaking(true);
            this.shakeTime = 40;
        }

        if(ticksExisted == 2) {
            this.playSound(ModSoundHandler.CHAMPION_AOE_PLACE, 0.5f, 0.7f / (rand.nextFloat() * 0.4F + 0.4f));
        }

        if(ticksExisted == 19) {
            this.playSound(ModSoundHandler.CHAMPION_AOE_IMPACT, 0.7f, 0.7f / (rand.nextFloat() * 0.4F + 0.4f));
        }

        if(this.ticksExisted < 24 && world.rand.nextInt(3) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }


        if(this.ticksExisted > 19) {
            if (!this.world.isRemote) {
                if (renderLazerPos == null) {
                    renderLazerPos = this.getPositionVector().add(0, 20, 0);
                }
                Main.network.sendToAllTracking(new MessageDirectionForRender(this, renderLazerPos), this);

                if(this.ticksExisted == 20 || this.ticksExisted == 30 || this.ticksExisted == 40) {
                    if(owner != null) {
                        Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, 0)));
                        DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(owner).disablesShields().build();
                        float damage = 18;
                        ModUtils.handleAreaImpact(1.4f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                    } else {
                        Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, 0)));
                        DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                        float damage = 10;
                        ModUtils.handleAreaImpact(1.4f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                    }
                }
            }
        }

        if(ticksExisted >= 41) {
            this.setDead();
        }

    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ModUtils.performNTimes(3, (i) -> {
                Main.proxy.spawnParticle(1, this.posX + ModRand.getFloat(1.5F), this.posY + ModRand.getFloat(1.5F), this.posZ + ModRand.getFloat(1.5F), 0, 0.06, 0, 9320921);
            });
        }
    }

    public void updateAnimations() {
        animator.summonEntity();
    }

    @Override
    public void setRenderDirection(Vec3d dir) {
        this.renderLazerPos = dir;
    }

    @Override
    public final boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    public Vec3d getRenderDirection() {
        return this.renderLazerPos;
    }

    @Override
    public float getShakeIntensity(Entity viewer, float partialTicks) {
        if(this.isShaking()) {
            double dist = getDistance(viewer);
            float screamMult = (float) (1.0F - dist / 8.0F);
            if (dist >= 8.0F) {
                return 0.0F;
            }
            return (float) ((Math.sin(((partialTicks)/this.shakeTime) * Math.PI) + 0.4F) * 0.25F * screamMult);
        }
        return 0;
    }
}
