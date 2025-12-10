package com.unseen.helios.common.entity.entities.attacks;

import com.google.common.base.Optional;
import com.unseen.helios.Main;
import com.unseen.helios.client.entity.IDirectionalRender;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.IScreenShake;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.events.MessageDirectionForRender;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.UUID;

public class EntityGodBeam extends EntityHeliosMobBase implements IAnimationTickable, IAnimatable, IDirectionalRender, IScreenShake {

    private Vec3d renderLazerPos;
    private final String ANIM_SUMMON = "summon";
    private int shakeTime = 0;

    private static final DataParameter<Boolean> SHAKING = EntityDataManager.createKey(EntityGodBeam.class, DataSerializers.BOOLEAN);
    private void setShaking(boolean value) {this.dataManager.set(SHAKING, Boolean.valueOf(value));}
    public boolean isShaking() {return this.dataManager.get(SHAKING);}
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityGodBeam(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.setSize(0.6F, 0.4F);
    }

    public EntityGodBeam(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.setSize(0.6F, 0.4F);
    }

    private EntityLivingBase owner;

    public EntityGodBeam (World worldIn, EntityLivingBase owner) {
        super(worldIn);
        this.setNoGravity(true);
        this.setImmovable(true);
        this.noClip = true;
        this.setNoAI(true);
        this.owner = owner;
        this.setSize(0.6F, 0.4F);
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
            this.playSound(ModSoundHandler.APOSTLE_CAST_IMPACT, 2.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }


        if(this.ticksExisted > 15) {
            if (!this.world.isRemote) {
                if (renderLazerPos == null) {
                    renderLazerPos = this.getPositionVector().add(0, 20, 0);
                }
                Main.network.sendToAllTracking(new MessageDirectionForRender(this, renderLazerPos), this);

                if(this.ticksExisted == 20 || this.ticksExisted == 40 || this.ticksExisted == 60) {
                    if(owner != null) {
                        Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, 0)));
                        DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(owner).build();
                        float damage = 15;
                        ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                    } else {
                        Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, 0)));
                        DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                        float damage = 15;
                        ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                    }
                    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
                }
            }
        }

        if(ticksExisted == 70) {
            this.setDead();
        }

    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            Main.proxy.spawnParticle(2, this.posX, this.posY + 0.2, this.posZ, 0, 0, 0, 9320921);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "idle_controller", 0, this::predicateIdle));
    }

    private<E extends IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {
            event.getController().setAnimation(new AnimationBuilder().playOnce(ANIM_SUMMON));
            return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void tick() {

    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
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
            float screamMult = (float) (1.0F - dist / 16.0F);
            if (dist >= 16.0F) {
                return 0.0F;
            }
            return (float) ((Math.sin(((partialTicks)/this.shakeTime) * Math.PI) + 0.5F) * 0.6F * screamMult);
        }
        return 0;
    }
}
