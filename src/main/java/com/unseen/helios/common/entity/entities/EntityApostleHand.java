package com.unseen.helios.common.entity.entities;

import com.google.common.base.Optional;
import com.unseen.helios.Main;
import com.unseen.helios.client.entity.IDirectionalRender;
import com.unseen.helios.common.entity.ai.apostle_hand.EntityAIApostleHurtByTarget;
import com.unseen.helios.common.entity.ai.apostle_hand.EntityAIOwnerAttackApostle;
import com.unseen.helios.common.entity.ai.attack_manager.EntityAITimedAttack;
import com.unseen.helios.common.entity.ai.attack_manager.EntityTimedAttackNoSight;
import com.unseen.helios.common.entity.ai.attack_manager.IAttack;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.IScreenShake;
import com.unseen.helios.common.entity.entities.attacks.EntityGodBeam;
import com.unseen.helios.config.MobConfig;
import com.unseen.helios.events.MessageDirectionForRender;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class EntityApostleHand extends EntityHeliosMobBase implements IAnimatable, IAnimationTickable, IScreenShake, IAttack {

    private static final DataParameter<Boolean> ARISE = EntityDataManager.createKey(EntityApostleHand.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SLEEP_EYES = EntityDataManager.createKey(EntityApostleHand.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CALL_PROJECTILE = EntityDataManager.createKey(EntityApostleHand.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SELF_AOE = EntityDataManager.createKey(EntityApostleHand.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SHAKING = EntityDataManager.createKey(EntityApostleHand.class, DataSerializers.BOOLEAN);
    public Vec3d targetedArea;
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityApostleHand.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private final AnimationFactory factory = new AnimationFactory(this);
    private final String ANIM_IDLE = "idle";
    private final String ANIM_ARISE = "arise";
    private final String ANIM_IDLE_ROOTS = "idle_roots";
    private final String ANIM_CALL_PROJECTILE = "call_projectile";
    private final String ANIM_SELF_AOE = "self_aoe";
    private int shakeTime = 0;

    private void setSleepEyes(boolean value) {this.dataManager.set(SLEEP_EYES, Boolean.valueOf(value));}
    public boolean isSleepEyes() {return this.dataManager.get(SLEEP_EYES);}
    private void setArise(boolean value) {this.dataManager.set(ARISE, Boolean.valueOf(value));}
    public boolean isArise() {return this.dataManager.get(ARISE);}

    private void setShaking(boolean value) {this.dataManager.set(SHAKING, Boolean.valueOf(value));}
    public boolean isShaking() {return this.dataManager.get(SHAKING);}
    private void setSelfAoe(boolean value) {this.dataManager.set(SELF_AOE, Boolean.valueOf(value));}
    public boolean isSelfAOE() {return this.dataManager.get(SELF_AOE);}
    private void setCallProjectile(boolean value) {this.dataManager.set(CALL_PROJECTILE, Boolean.valueOf(value));}
    public boolean isCallProjectile() {return this.dataManager.get(CALL_PROJECTILE);}

    @Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    @Nullable
    public EntityLivingBase getOwner()
    {
        UUID uuid = this.getOwnerId();
        if(uuid == null) {
            return null;
        }
        else {
            return this.world.getPlayerEntityByUUID(uuid);
        }
    }

    public EntityApostleHand(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setSize(1.75F, 4.5F);
        this.isNonHealableCreature = true;
        this.experienceValue = 75;
        this.setImmovable(true);
        this.summonMob();
    }

    public EntityApostleHand(World worldIn) {
        super(worldIn);
        this.setSize(1.75F, 4.5F);
        this.isNonHealableCreature = true;
        this.experienceValue = 75;
        this.setImmovable(true);
        this.summonMob();
    }

    private void summonMob() {
        this.setArise(true);
        this.setFightMode(true);
        this.setImmovable(true);
        this.shakeTime = 80;
        this.setShaking(true);
        addEvent(()-> {
            ModUtils.destroyBlocksInAABB(this.getEntityBoundingBox().grow(1.25D).offset(0, 1.25,0), world, this);
            this.playSound(ModSoundHandler.APOSTLE_SUMMON, 1.5f, 1.0F);
        }, 5);
        addEvent(()-> {
            this.setShaking(false);
            this.setArise(false);
            this.setSleepEyes(false);
            this.setFightMode(false);
        }, 100);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("Sleep_Eyes", this.isSleepEyes());
        nbt.setBoolean("Arise", this.isArise());
        nbt.setBoolean("Call_Projectile", this.isCallProjectile());
        nbt.setBoolean("Self_Aoe", this.isSelfAOE());
        nbt.setBoolean("Shaking", this.isShaking());
        if (this.getOwnerId() == null)
        {
            nbt.setString("OwnerUUID", "");
        }
        else
        {
            nbt.setString("OwnerUUID", this.getOwnerId().toString());
        }
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setSleepEyes(nbt.getBoolean("Sleep_Eyes"));
        this.setArise(nbt.getBoolean("Arise"));
        this.setCallProjectile(nbt.getBoolean("Call_Projectile"));
        this.setSelfAoe(nbt.getBoolean("Self_Aoe"));
        this.setShaking(nbt.getBoolean("Shaking"));
        String s;
        if (nbt.hasKey("OwnerUUID", 8))
        {
            s = nbt.getString("OwnerUUID");
        }
        else
        {
            String s1 = nbt.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(Objects.requireNonNull(this.getServer()), s1);
        }

        if (!s.isEmpty())
        {
            this.setOwnerId(UUID.fromString(s));
        }
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
        this.dataManager.register(SLEEP_EYES, Boolean.valueOf(true));
        this.dataManager.register(ARISE, Boolean.valueOf(true));
        this.dataManager.register(SELF_AOE, Boolean.valueOf(false));
        this.dataManager.register(CALL_PROJECTILE, Boolean.valueOf(false));
        this.dataManager.register(SHAKING, Boolean.valueOf(false));
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(MobConfig.apostle_hand_attack_damage);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MobConfig.apostle_hand_health);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public void initEntityAI() {
        this.tasks.addTask(4, new EntityTimedAttackNoSight<>(this, 0, 80, 24, 0));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityMob>(this, EntityMob.class, 1, true, false, e -> !(e instanceof EntityHeliosMobBase)));
        if(this.getOwner() != null) {
            this.targetTasks.addTask(3, new EntityAIApostleHurtByTarget(this));
            this.targetTasks.addTask(4, new EntityAIOwnerAttackApostle(this));
        } else {
            this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
        }
    }

    private Consumer<EntityLivingBase> prevAttack;

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if(!this.isFightMode()) {
            double distance = Math.sqrt(distanceSq);

            if(distance > 6) {
                prevAttack = call_projectile_attack;
                prevAttack.accept(target);
            } else {
                prevAttack = self_aoe;
                prevAttack.accept(target);
            }
        }
        return 120;
    }

    private final Consumer<EntityLivingBase> call_projectile_attack = (target) -> {
      this.setFightMode(true);
      this.setCallProjectile(true);
      addEvent(()-> this.setSleepEyes(true), 20);

      addEvent(()-> {
          this.playSound(ModSoundHandler.APOSTLE_CAST, 2.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        //summon projectile predicting mob position
          world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
          if(!world.isRemote) {
              addEvent(() -> {
                  Vec3d targetOldPos = target.getPositionVector();
                  addEvent(() -> {
                      Vec3d targetedPos = target.getPositionVector();
                      Vec3d predictedPosition = ModUtils.predictPlayerPosition(targetOldPos, targetedPos, 4);
                      this.summonGodLazerOnPosition(predictedPosition, target);
                      targetedArea = predictedPosition;
                  }, 3);
              }, 1);
          }
      }, 35);

      addEvent(()-> this.setSleepEyes(false), 65);
      addEvent(()-> {
          this.setFightMode(false);
          this.setCallProjectile(false);
      }, 70);
    };
    private final Consumer<EntityLivingBase> self_aoe = (target) -> {
        this.setSelfAoe(true);
        this.setFightMode(true);
        addEvent(()-> this.setSleepEyes(true), 10);

        addEvent(()-> {
            this.playSound(ModSoundHandler.APOSTLE_SLAM, 2.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }, 33);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 0, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage = this.getAttack();
            ModUtils.handleAreaImpact(4.5f, (e) -> damage, this, offset, source, 1.1f, 0, false);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            this.setSleepEyes(false);
        }, 39);

        addEvent(()-> {
            this.setFightMode(false);
            this.setSelfAoe(false);
        }, 70);
    };


    private void summonGodLazerOnPosition(Vec3d predicatedPos, EntityLivingBase target) {
        EntityGodBeam beam = new EntityGodBeam(world, this);
        beam.setPosition(predicatedPos.x, target.posY, predicatedPos.z);
        world.spawnEntity(beam);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setRotation(0, 0);
        this.setRotationYawHead(0);
        this.shakeTime--;
        this.lockLook = true;

        //handles blinking of the eye
        if(this.ticksExisted % 200 == 0 && !this.isFightMode()) {
            this.blinkOccasionally();
        }

        if(!world.isRemote && ticksExisted % 60 == 0) {
            List<EntityPlayer> nearbyPlayers = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(20), e -> !e.getIsInvulnerable());
            List<EntityManusMinion> nearbyMinions = this.world.getEntitiesWithinAABB(EntityManusMinion.class, this.getEntityBoundingBox().grow(20), e -> !e.getIsInvulnerable());
            if(!nearbyPlayers.isEmpty()) {
                for(EntityPlayer player : nearbyPlayers) {
                    if(this.getOwner() == player) {
                        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 100, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 1, false, false));

                        if(rand.nextInt(95) == 0) {
                            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 80, 0, false, false));
                        }
                    } else if (world.getBlockState(player.getPosition().down()) == ModBlocks.MANUS_BLOCk.getDefaultState()){
                        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0,  false, false));
                        player.addPotionEffect(new PotionEffect(ModPotions.GREED, 100, 0, false, false));
                    }
                }
            }

            if(!nearbyMinions.isEmpty()) {
                for(EntityManusMinion minion : nearbyMinions) {
                    minion.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 0, false, false));
                    minion.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 0, false, false));
                    minion.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100, 0, false, false));
                }
            }

        }

    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ModUtils.performNTimes(3, (i) -> {
                Main.proxy.spawnParticle(1, this.posX + ModRand.getFloat(6), this.posY + ModRand.getFloat(4), this.posZ + ModRand.getFloat(6), 0, 0.06, 0, 9320921);
            });
        }

        if(id == ModUtils.SECOND_PARTICLE_BYTE) {
            Main.proxy.spawnParticle(3, this.posX, this.posY, this.posZ, 0, 0, 0, 9320921);
        }

        if(id == ModUtils.THIRD_PARTICLE_BYTE) {
            Main.proxy.spawnParticle(2, this.posX, this.posY + 4.5, this.posZ, 0, 0, 0, 9320921);
        }
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!this.isFightMode() && world.rand.nextInt(9) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
    }

    private void blinkOccasionally() {
        this.setSleepEyes(true);

        addEvent(()-> {
            this.setSleepEyes(false);
        }, 15);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "idle_controller", 0, this::predicateIdle));
        data.addAnimationController(new AnimationController(this, "hand_controller", 0, this::predicateHandStates));
        data.addAnimationController(new AnimationController(this, "idle_roots_controller", 0, this::predicateIdleRoots));
    }

    private <E extends IAnimatable> PlayState predicateHandStates(AnimationEvent<E> event) {
        if(this.isArise()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce(ANIM_ARISE));
            return PlayState.CONTINUE;
        }

        if(this.isSelfAOE()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce(ANIM_SELF_AOE));
            return PlayState.CONTINUE;
        }

        if(this.isCallProjectile()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce(ANIM_CALL_PROJECTILE));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private<E extends IAnimatable> PlayState predicateIdleRoots(AnimationEvent<E> event) {
        if(!this.isArise()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE_ROOTS, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private<E extends IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {
        if(!this.isFightMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
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

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundHandler.APOSTLE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundHandler.APOSTLE_IDLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundHandler.APOSTLE_HURT;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.getImmediateSource() == this || this.getOwner() != null && source.getImmediateSource() == this.getOwner()) {
            return false;
        }


        return super.attackEntityFrom(source, amount);
    }
}
