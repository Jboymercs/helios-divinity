package com.unseen.helios.common.entity.entities.boss;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.ai.IEntitySound;
import com.unseen.helios.common.entity.ai.attack_manager.IAttack;
import com.unseen.helios.common.entity.ai.boss.EntityAIChampionAttack;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.base.IScreenShake;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.common.entity.base.scaling.ServerScaleUtil;
import com.unseen.helios.common.entity.entities.boss.action.*;
import com.unseen.helios.common.entity.entities.effects.greed_hand.EntityGreedHand;
import com.unseen.helios.common.entity.projectile.ProjectileChampionOrb;
import com.unseen.helios.common.entity.projectile.ProjectileTrackingOrb;
import com.unseen.helios.config.MobConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModReference;
import com.unseen.helios.util.ModUtils;
import mod.azure.azurelib.util.MoveAnalysis;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityNamelessChampion extends EntityHeliosMobBase implements IAttack, IScreenShake, IEntitySound {
    private BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PINK, BossInfo.Overlay.NOTCHED_10));
    private Consumer<EntityLivingBase> prevAttack;
    private int shakeTime = 0;
    protected int targetTrackingTimer = MobConfig.nameless_champion_reset_timer * 20;
    private final MoveAnalysis moveAnalysis;
    public final ADNamelessChampion animator = new ADNamelessChampion(this);
    //states
    private final String ANIM_IDLE = "idle";
    private final String ANIM_WALK = "walk";
    private final String ANIM_SLEEP = "idle_sleep";
    private final String ANIM_DEATH = "death";
    private final String ANIM_AWAKE = "idle_awake";
    private final String ANIM_PHASE_TRANSFORMATION = "phase_transformation";

    //attacks
    private final String ANIM_SWING = "swing";
    private final String ANIM_DASH = "fast_dash";
    private final String ANIM_OVERHEAD_SWING = "overhead_slash";
    private final String ANIM_CIRCLE_SWING = "circle_swing";
    private final String ANIM_STOMP = "stomp";
    private final String ANIM_FLY_AOE = "fly_aoe";
    private final String ANIM_CAST_GREED = "cast_greed";
    private final String ANIM_CAST_PROJECTILES = "cast_projectiles";
    private final String ANIM_TRIPLE_SLASH = "triple_slash";
    private final String ANIM_TRIPLE_STOMP = "triple_stomp";
    private final String ANIM_SWING_BACKSTEP = "swing_backstep";

    private static final DataParameter<Boolean> SHAKING = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SWING = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DASH = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> OVERHEAD_SWING = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CIRCLE_SWING = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SLEEP_MODE = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IDLE_WAKE = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRANSFORM_PHASE = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GREEDY = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> STOMP = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLY_AOE = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CAST_GREED = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CAST_PROJECTILES = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRIPLE_SlASH = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRIPLE_STOMP = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAD_PREVIOUS_TARGET = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DEATH_STATE = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SWING_BACKSTEP = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);

    public static DataParameter<BlockPos> SPAWN_LOCATION = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BLOCK_POS);
    public static DataParameter<Boolean> SET_SPAWN_LOC = EntityDataManager.createKey(EntityNamelessChampion.class, DataSerializers.BOOLEAN);
    private void setTransformPhase(boolean value) {this.dataManager.set(TRANSFORM_PHASE, Boolean.valueOf(value));}
    public boolean isTransformPhase() {return this.dataManager.get(TRANSFORM_PHASE);}
    private void setGreedy(boolean value) {this.dataManager.set(GREEDY, Boolean.valueOf(value));}
    public boolean isGreedy() {return this.dataManager.get(GREEDY);}
    private void setStomp(boolean value) {this.dataManager.set(STOMP, Boolean.valueOf(value));}
    public boolean isStomp() {return this.dataManager.get(STOMP);}
    private void setFlyAOE(boolean value) {this.dataManager.set(FLY_AOE, Boolean.valueOf(value));}
    public boolean isFlyAOE() {return this.dataManager.get(FLY_AOE);}
    private void setCastGreed(boolean value) {this.dataManager.set(CAST_GREED, Boolean.valueOf(value));}
    public boolean isCastGreed() {return this.dataManager.get(CAST_GREED);}
    private void setShaking(boolean value) {this.dataManager.set(SHAKING, Boolean.valueOf(value));}
    public boolean isShaking() {return this.dataManager.get(SHAKING);}
    private void setSwing(boolean value) {this.dataManager.set(SWING, Boolean.valueOf(value));}
    public boolean isSwing() {return this.dataManager.get(SWING);}
    private void setDash(boolean value) {this.dataManager.set(DASH, Boolean.valueOf(value));}
    public boolean isDash() {return this.dataManager.get(DASH);}
    private void setOverheadSwing(boolean value) {this.dataManager.set(OVERHEAD_SWING, Boolean.valueOf(value));}
    public boolean isOverheadSwing() {return this.dataManager.get(OVERHEAD_SWING);}
    private void setCircleSwing(boolean value) {this.dataManager.set(CIRCLE_SWING, Boolean.valueOf(value));}
    public boolean isCircleSwing() {return this.dataManager.get(CIRCLE_SWING);}
    private void setIdleWake(boolean value) {this.dataManager.set(IDLE_WAKE, Boolean.valueOf(value));}
    public boolean isIdleWake() {return this.dataManager.get(IDLE_WAKE);}
    private void setSleepMode(boolean value) {this.dataManager.set(SLEEP_MODE, Boolean.valueOf(value));}
    public boolean isSleepMode() {return this.dataManager.get(SLEEP_MODE);}
    private void setCastProjectiles(boolean value) {this.dataManager.set(CAST_PROJECTILES, Boolean.valueOf(value));}
    public boolean isCastProjectiles() {return this.dataManager.get(CAST_PROJECTILES);}
    private void setTripleSlash(boolean value) {this.dataManager.set(TRIPLE_SlASH, Boolean.valueOf(value));}
    public boolean isTripleSlash() {return this.dataManager.get(TRIPLE_SlASH);}
    private void setTripleStomp(boolean value) {this.dataManager.set(TRIPLE_STOMP, Boolean.valueOf(value));}
    public boolean isTripleStomp() {return this.dataManager.get(TRIPLE_STOMP);}
    private void setDeathState(boolean value) {this.dataManager.set(DEATH_STATE, Boolean.valueOf(value));}
    public boolean isDeathState() {return this.dataManager.get(DEATH_STATE);}
    public boolean isHadPreviousTarget() {return this.dataManager.get(HAD_PREVIOUS_TARGET);}
    public void setHadPreviousTarget(boolean value) {this.dataManager.set(HAD_PREVIOUS_TARGET, Boolean.valueOf(value));}
    public boolean isSwingBackStep() {return this.dataManager.get(SWING_BACKSTEP);}
    public void setSwingBackstep(boolean value) {this.dataManager.set(SWING_BACKSTEP, Boolean.valueOf(value));}

    public boolean isSetSpawnLoc() {
        return this.dataManager.get(SET_SPAWN_LOC);
    }
    public void setSetSpawnLoc(boolean value) {
        this.dataManager.set(SET_SPAWN_LOC, Boolean.valueOf(value));
    }
    public void setSpawnLocation(BlockPos pos) {
        this.dataManager.set(SPAWN_LOCATION, pos);
    }

    public BlockPos getSpawnLocation() {
        return this.dataManager.get(SPAWN_LOCATION);
    }

    public EntityNamelessChampion(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.iAmBossMob = true;
        this.setSize(0.7F, 2.4F);
        this.experienceValue = 160;
        this.setSleepMode(true);
        this.bossInfo.setVisible(false);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    public EntityNamelessChampion(World worldIn) {
        super(worldIn);
        this.iAmBossMob = true;
        this.setSize(0.7F, 2.4F);
        this.experienceValue = 160;
        this.setSleepMode(true);
        this.bossInfo.setVisible(false);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Swing", this.isSwing());
        nbt.setBoolean("Dash", this.isDash());
        nbt.setBoolean("Overhead_Swing", this.isOverheadSwing());
        nbt.setBoolean("Circle_Swing", this.isCircleSwing());
        nbt.setBoolean("Shaking", this.isShaking());
        nbt.setBoolean("Sleep_Mode", this.isSleepMode());
        nbt.setBoolean("Idle_Wake", this.isIdleWake());
        nbt.setBoolean("Transform_Phase", this.isTransformPhase());
        nbt.setBoolean("Greedy", this.isGreedy());
        nbt.setBoolean("Stomp", this.isStomp());
        nbt.setBoolean("Fly_Aoe", this.isFlyAOE());
        nbt.setBoolean("Cast_Greed", this.isCastGreed());
        nbt.setBoolean("Cast_Projectiles", this.isCastProjectiles());
        nbt.setBoolean("Triple_Stomp", this.isTripleStomp());
        nbt.setBoolean("Swing_Backstep", this.isSwingBackStep());
        nbt.setBoolean("Triple_Slash", this.isTripleSlash());
        nbt.setBoolean("Death_State", this.isDeathState());
        nbt.setBoolean("Had_Target", this.dataManager.get(HAD_PREVIOUS_TARGET));
        nbt.setInteger("Spawn_Loc_X", this.getSpawnLocation().getX());
        nbt.setInteger("Spawn_Loc_Y", this.getSpawnLocation().getY());
        nbt.setInteger("Spawn_Loc_Z", this.getSpawnLocation().getZ());
        nbt.setBoolean("Set_Spawn_Loc", this.dataManager.get(SET_SPAWN_LOC));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSwing(nbt.getBoolean("Swing"));
        this.setDash(nbt.getBoolean("Dash"));
        this.setOverheadSwing(nbt.getBoolean("Overhead_Swing"));
        this.setCircleSwing(nbt.getBoolean("Circle_Swing"));
        this.setShaking(nbt.getBoolean("Shaking"));
        this.setSleepMode(nbt.getBoolean("Sleep_Mode"));
        this.setIdleWake(nbt.getBoolean("Idle_Wake"));
        this.setDeathState(nbt.getBoolean("Death_State"));
        this.setTransformPhase(nbt.getBoolean("Transform_Phase"));
        this.setGreedy(nbt.getBoolean("Greedy"));
        this.setStomp(nbt.getBoolean("Stomp"));
        this.setSwingBackstep(nbt.getBoolean("Swing_Backstep"));
        this.setFlyAOE(nbt.getBoolean("Fly_Aoe"));
        this.setTripleStomp(nbt.getBoolean("Triple_Stomp"));
        this.setCastGreed(nbt.getBoolean("Cast_Greed"));
        this.setCastProjectiles(nbt.getBoolean("Cast_Projectiles"));
        this.setTripleSlash(nbt.getBoolean("Triple_Slash"));
        this.setHadPreviousTarget(nbt.getBoolean("Had_Target"));
        this.dataManager.set(SET_SPAWN_LOC, nbt.getBoolean("Set_Spawn_Loc"));
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
        this.setSpawnLocation(new BlockPos(nbt.getInteger("Spawn_Loc_X"), nbt.getInteger("Spawn_Loc_Y"), nbt.getInteger("Spawn_Loc_Z")));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(SWING, Boolean.valueOf(false));
        this.dataManager.register(DASH, Boolean.valueOf(false));
        this.dataManager.register(OVERHEAD_SWING, Boolean.valueOf(false));
        this.dataManager.register(CIRCLE_SWING, Boolean.valueOf(false));
        this.dataManager.register(SHAKING, Boolean.valueOf(false));
        this.dataManager.register(IDLE_WAKE, Boolean.valueOf(false));
        this.dataManager.register(SLEEP_MODE, Boolean.valueOf(false));
        this.dataManager.register(TRANSFORM_PHASE, Boolean.valueOf(false));
        this.dataManager.register(GREEDY, Boolean.valueOf(false));
        this.dataManager.register(TRIPLE_STOMP, Boolean.valueOf(false));
        this.dataManager.register(SWING_BACKSTEP, Boolean.valueOf(false));
        this.dataManager.register(DEATH_STATE, Boolean.valueOf(false));
        this.dataManager.register(STOMP, Boolean.valueOf(false));
        this.dataManager.register(FLY_AOE, Boolean.valueOf(false));
        this.dataManager.register(CAST_GREED, Boolean.valueOf(false));
        this.dataManager.register(CAST_PROJECTILES, Boolean.valueOf(false));
        this.dataManager.register(TRIPLE_SlASH, Boolean.valueOf(false));
        this.dataManager.register(HAD_PREVIOUS_TARGET, Boolean.valueOf(false));
        this.dataManager.register(SET_SPAWN_LOC, Boolean.valueOf(false));
        //
        this.dataManager.register(SPAWN_LOCATION, new BlockPos(this.getPositionVector().x, this.getPositionVector().y, this.getPositionVector().z));
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(MobConfig.nameless_champion_attack_damage);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MobConfig.nameless_champion_health);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(MobConfig.nameless_champion_armor);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(MobConfig.nameless_champion_armor_toughness);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIChampionAttack<EntityNamelessChampion>(this, 1, 40, 24, 0.2F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
    }

    private float randomTurn = ModRand.range(1, 360);
    private boolean doingCast = false;
    private int HoverTimeIncrease = 0;
    private boolean hasHoverMovement = false;

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.shakeTime--;
        this.flyCooldown--;
        this.tripleSlashCooldown--;
        this.tripleStompCooldown--;

        double healthFac = this.getHealth() / this.getMaxHealth();
        if(this.bossInfo != null) {
            this.bossInfo.setPercent((float) healthFac);
        }

        if(this.isSleepMode()) {
            this.rotationYaw = randomTurn;
            this.rotationYawHead = randomTurn;
            this.setImmovable(true);
        }

        EntityLivingBase target = this.getAttackTarget();

        if(target != null && !world.isRemote) {

            if(this.isSleepMode() && this.getDistance(target) <= 4) {
                this.endIdleState();
            }

            if(this.getHealth() / this.getMaxHealth() < 0.51 && !this.isGreedy() && !this.isFightMode() && !this.isTransformPhase()) {
                this.doPhaseTransformation();
            }

            if(HoverTimeIncrease > 0) {
                this.motionY = 0.25;
                HoverTimeIncrease--;
            }

            if(this.hasHoverMovement) {
                double d0 = (target.posX - this.posX) * 0.012;
                double d2 = (target.posZ - this.posZ) * 0.012;
                this.addVelocity(d0, 0, d2);
                this.faceEntity(target, 30F, 30F);
            }
        }


        if(!world.isRemote) {
            if(this.doingCast) {
                List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(24), e -> !(e instanceof EntityHeliosMobBase));
                if(!targets.isEmpty()) {
                    for(EntityLivingBase base : targets) {
                        if (base.canBePushed() && !base.isBeingRidden()) {
                            double d0 = (this.posX - base.posX) * 0.0006;
                            double d1 = (this.posY - base.posY) * 0.0006;
                            double d2 = (this.posZ - base.posZ) * 0.0006;
                            base.motionX += d0 * 3;
                            base.motionY += d1 * 2;
                            base.motionZ += d2 * 3;
                            base.velocityChanged = true;
                            //  target.addVelocity(d0 * blackholeSize, d1 * blackholeSize, d2 * blackholeSize);
                        }
                    }
                }
            }

            if (this.getSpawnLocation() != null && this.isSetSpawnLoc()) {
                if (target != null) {
                    if (target instanceof EntityPlayer) {
                        this.setHadPreviousTarget(true);
                    }
                }

                //Creates a Target tracking to ensure if it can despawn or not
                if (target == null && this.isHadPreviousTarget()) {
                    int nearbyPlayers = ServerScaleUtil.getPlayersForReset(this, world);
                    if (nearbyPlayers == 0) {
                        if (targetTrackingTimer > 0) {
                            targetTrackingTimer--;
                        }
                        if (targetTrackingTimer < 1) {
                                this.resetBossTask();
                        }
                    }
                }
            }


            if(this.getSpawnLocation() != null && this.isSetSpawnLoc()) {
                Vec3d SpawnLoc = new Vec3d(this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ());

                double distSq = this.getDistanceSq(SpawnLoc.x, SpawnLoc.y, SpawnLoc.z);
                double distance = Math.sqrt(distSq);
                //This basically makes it so the boss will be teleported if they are too far away from the Arena
                if(!world.isRemote) {
                    if (distance > 28) {
                        this.teleportTarget(SpawnLoc.x, SpawnLoc.y, SpawnLoc.z);
                    }
                }
            }

            //Innate Resistance to these potions
            if(this.ticksExisted % 40 == 0) {
                if(this.isPotionActive(MobEffects.LEVITATION) || this.isPotionActive(MobEffects.POISON)) {
                    this.removePotionEffect(MobEffects.LEVITATION);
                    this.removePotionEffect(MobEffects.POISON);
                }
            }

        }
    }

    public void teleportTarget(double x, double y, double z) {
        this.setPosition(x , y, z);
    }

    private void resetBossTask() {
        this.setGreedy(false);
         double health = this.getMaxHealth();
         this.setHealth((float) health);
         if(this.getSpawnLocation() != null) {
             this.setPosition(this.getSpawnLocation().getX() + 0.5, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5);
         }
         this.setSleepMode(true);
         this.bossInfo.setVisible(false);
    }

    //activates the boss
    private void endIdleState() {
        this.setSleepMode(false);
        this.lockLook = true;
        this.setIdleWake(true);
        animator.setStateAnimation(ANIM_AWAKE);
        this.playSound(ModSoundHandler.CHAMPION_SUMMON, 1.5f, 1.0F);

                this.setSpawnLocation(this.getPosition());
                this.setSetSpawnLoc(true);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 3);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 18);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 40);

        addEvent(()-> {
            this.bossInfo.setVisible(true);
            this.playMusic(this);
            this.setIdleWake(false);
            this.lockLook = false;
            this.setImmovable(false);
        }, 50);
    }

    private void doPhaseTransformation() {
        this.setTransformPhase(true);
        animator.setStateAnimation(ANIM_PHASE_TRANSFORMATION);
        this.setImmovable(true);
        this.lockLook = true;
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 25);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 45);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 95);
        addEvent(()-> {
            this.setGreedy(true);
            List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(24), e -> !(e instanceof EntityHeliosMobBase));
            if(!targets.isEmpty()) {
                for(EntityLivingBase base: targets) {
                    double healthDamage = base.getMaxHealth() * 0.50;
                    if(healthDamage > 25) {
                        healthDamage = 25;
                    }
                    base.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), (float) healthDamage);
                    base.addPotionEffect(new PotionEffect(ModPotions.GREED, 300, 2, false, false));
                }
                world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, ModSoundHandler.UTIL_MANUS_SWORD_CAST, SoundCategory.NEUTRAL, 2.5f, 0.5f / (world.rand.nextFloat() * 0.4F + 0.2f));
                world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            }
        }, 60);

        addEvent(()-> this.lockLook = false, 100);

        addEvent(() -> {
            this.setTransformPhase(false);
            this.setImmovable(false);
        }, 120);
    }

    private int flyCooldown = 0;
    private int tripleSlashCooldown = 0;
    private int tripleStompCooldown = 0;

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if(!this.isFightMode() && !this.isSleepMode() && !this.isIdleWake() && !this.isTransformPhase() && !this.isDeathState()) {
            double distance = Math.sqrt(distanceSq);
            double healthFac = this.getHealth() / this.getMaxHealth();
            List<Consumer<EntityLivingBase>> attacksMelee = new ArrayList<>(Arrays.asList(swing_attack, fast_dash, overhead_swing, circle_swing, stomp_attack, cast_greed, fly_aoe_attack, cast_Projectiles, fly_triple_slash, triple_stomp_attack, swing_backstep));
            double[] weights = {
                    (swing_attack != prevAttack && distance < 8) ? 1/distance : 0, //Swing
                    (fast_dash != prevAttack) ? distance * 0.02 : 0, //Dash
                    (overhead_swing != prevAttack) ? 1/distance : 0, //Overhead Swing
                    (circle_swing != prevAttack && distance < 8 && healthFac < 0.75) ? 1/distance : 0, //Circle Swing 75
                    (stomp_attack != prevAttack && distance < 17 && distance > 1 && healthFac < 0.8) ? distance * 0.02 : 0, //Stomp Attack 80
                    (cast_greed != prevAttack && distance > 5 && healthFac < 0.3) ? distance * 0.02 : 0, //Cast Greed 30
                    (fly_aoe_attack != prevAttack && distance > 3 && healthFac < 0.5 && flyCooldown < 0) ? distance * 0.03 : 0, //Fly Aoe Attack 50
                    (cast_Projectiles != prevAttack && distance > 3 && healthFac < 0.75) ? distance * 0.02 : 0, //Cast Projectiles 75
                    (fly_triple_slash != prevAttack && healthFac < 0.4 && tripleSlashCooldown < 0) ? distance * 0.03 : 0, //Triple Slash 40
                    (triple_stomp_attack != prevAttack && healthFac < 0.5 && tripleStompCooldown < 0) ? distance * 0.02 : 0, //Triple Stomp 50
                    (swing_backstep != prevAttack && distance < 8 && distance > 1 && healthFac < 0.75) ? 1/distance : 0 //Swing Backstep
            };
            prevAttack = ModRand.choice(attacksMelee, rand, weights).next();
            prevAttack.accept(target);
        }
        return (this.getHealth() / this.getMaxHealth() < 0.5) ? MobConfig.nameless_champion_cooldown_two : MobConfig.nameless_champion_cooldown_one;
    }


    private final Consumer<EntityLivingBase> swing_backstep = (target) -> {
      this.setFightMode(true);
      this.setSwingBackstep(true);
      this.setImmovable(true);
        animator.performAttack(ANIM_SWING_BACKSTEP);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 3);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 16);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 40);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 78);
        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(1));
            this.lockLook = true;
            //  this.faceEntity(target, 180, 30);
            addEvent(()-> {
                this.setImmovable(false);
                double distance = this.getPositionVector().distanceTo(targetedPos);
                ModUtils.leapTowards(this, targetedPos, (float) (distance * 0.33),0.1F);
                this.playSound(ModSoundHandler.CHAMPION_DASH, 1f, 1.0F);
            }, 4);
        }, 3);

        addEvent(()-> {
            this.setImmovable(true);
        }, 15);

        //does swing attack
        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.8, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack());
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false, ModPotions.GREED, 1, 200);
            this.playSound(ModSoundHandler.CHAMPION_SWING, 1f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(1.2, 1.2, 0), 0.9);
            this.getNavigator().clearPath();
            //immediately after do a dash away
            addEvent(()-> {
                Vec3d dirToo = this.getPositionVector().subtract(target.getPositionVector()).normalize();
                Vec3d jumpTooPos = this.getPositionVector().add(dirToo.scale(81));
                this.setImmovable(false);
                ModUtils.leapTowards(this, jumpTooPos, 1.5F, 0.25F);
                this.playSound(ModSoundHandler.CHAMPION_DASH, 1f, 1.0F);
            }, 3);
        }, 22);

        addEvent(()-> {
            this.lockLook = false;
            addEvent(()-> {
                this.setImmovable(true);
            }, 3);
        }, 42);

        addEvent(()-> {
           //do projectile Splay
            new ActionProjectileSplay().performAction(this, target);
            this.playSound(ModSoundHandler.CHAMPION_CAST_WAVE, 2f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
        }, 59);

        addEvent(()-> {
            this.setSwingBackstep(false);
            this.setImmovable(false);
            this.setFightMode(false);
        }, 90);

    };

    Supplier<Projectile> trackingOrbProjectile = () -> new ProjectileChampionOrb(this.world, this, (float) (this.getAttack()));

    private final Consumer<EntityLivingBase> fly_triple_slash = (target) -> {
        this.setTripleSlash(true);
        this.setFightMode(true);
        this.setImmovable(true);
        animator.performAttack(ANIM_TRIPLE_SLASH);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 6);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 20);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 170);
        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_TELEPORT, 0.9f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            world.setEntityState(this, ModUtils.FOURTH_PARTICLE_BYTE);
        }, 26);
        //First Iteration
        addEvent(()-> {
            this.setNoGravity(true);
            this.setImmovable(false);
            if(this.getSpawnLocation() != null) {
                this.setPosition(this.getSpawnLocation().getX() + 4.5, this.getSpawnLocation().getY() + 6, this.getSpawnLocation().getZ() + 0.5);
            }
            this.setImmovable(true);
        }, 33);

        //first Wave
        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_CAST_WAVE, 2.5f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleSwing(trackingOrbProjectile, 0.7F).performAction(this, target);
        }, 55);


        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_TELEPORT, 0.9f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            world.setEntityState(this, ModUtils.FOURTH_PARTICLE_BYTE);
        }, 79);

        //Second Iteration
        addEvent(()-> {
            this.setImmovable(false);
            if(this.getSpawnLocation() != null) {
                this.setPosition(this.getSpawnLocation().getX() - 4.5, this.getSpawnLocation().getY() + 6, this.getSpawnLocation().getZ() + 0.5);
            }
            this.setImmovable(true);
        }, 83);

        //second Wave
        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_CAST_WAVE, 2.5f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleSwing(trackingOrbProjectile, 0.7F).performAction(this, target);
        }, 105);

        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_TELEPORT, 0.9f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            world.setEntityState(this, ModUtils.FOURTH_PARTICLE_BYTE);
        }, 129);

        //Third Iteration
        addEvent(()-> {
            this.setImmovable(false);
            if(this.getSpawnLocation() != null) {
                this.setPosition(this.getSpawnLocation().getX() + 0.5, this.getSpawnLocation().getY() + 6, this.getSpawnLocation().getZ() + 0.5);
            }
            this.setImmovable(true);
        }, 133);

        //third Wave
        addEvent(()-> {
            this.playSound(ModSoundHandler.CHAMPION_CAST_WAVE, 2.5f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleSwing(trackingOrbProjectile, 0.7F).performAction(this, target);
        }, 165);

        addEvent(()-> {
            this.setNoGravity(false);
            this.setImmovable(false);
        }, 175);

        addEvent(()-> {
            this.setFightMode(false);
            this.setTripleSlash(false);
            this.tripleSlashCooldown = 20 * 20;
        }, 190);
    };

    private final Consumer<EntityLivingBase> cast_Projectiles = (target) -> {
      this.setCastProjectiles(true);
      this.setFightMode(true);
      this.setImmovable(false);
      animator.performAttack(ANIM_CAST_PROJECTILES);
        this.HoverTimeIncrease = 3;
        this.setNoGravity(true);
        this.lockLook = true;
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 60);
        addEvent(()-> {
            for(int i = 0; i <= 40; i += 5) {
                addEvent(()-> {
                    //summon Tracking Projectiles
                    ProjectileTrackingOrb orb = new ProjectileTrackingOrb(world, this,(float) (this.getAttack() * 0.75), target);
                    orb.setPosition(this.posX + 1 + ModRand.getFloat(1.5F), this.posY + ModRand.getFloat(4), this.posZ + 1 + ModRand.getFloat(1.5F));
                    world.spawnEntity(orb);
                    this.playSound(ModSoundHandler.CHAMPION_CAST_PROJECTILE, 1.25f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
                }, i);
            }
        }, 20);

        addEvent(()-> {
            this.setNoGravity(false);
        }, 65);

        addEvent(()-> {
            this.lockLook = false;
            this.setCastProjectiles(false);
            this.setFightMode(false);
            this.setImmovable(false);
        }, 80);

    };

    private final Consumer<EntityLivingBase> fly_aoe_attack = (target) -> {
      this.setFightMode(true);
      this.setFlyAOE(true);
      animator.performAttack(ANIM_FLY_AOE);
      this.setImmovable(false);
      this.HoverTimeIncrease = 4;
        this.setNoGravity(true);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 4);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 80);
      addEvent(()-> {
        this.hasHoverMovement = true;
      }, 7);

     addEvent(()-> {
         this.hasHoverMovement = false;
         this.setNoGravity(false);
     }, 45);

     addEvent(()-> {
         this.setImmovable(true);
         Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.2, 0)));
         DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).disablesShields().build();
         float damage =(float) (this.getAttack() * 1.4);
         ModUtils.handleAreaImpact(2.75f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 400);
         this.playSound(ModSoundHandler.CHAMPION_SUMMON_AOE, 1.5f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
         this.destroyBlocksInSwing(new Vec3d(0, 1.2, 0), 1.9);
         new ActionKnightLargeAOE().performAction(this, target);
         this.lockLook = true;
     }, 63);

     addEvent(()-> {
        this.lockLook = false;
     }, 85);

      addEvent(()-> {
            this.flyCooldown = 20 * 20;
            this.setFightMode(false);
            this.setFlyAOE(false);
            this.setImmovable(false);
      }, 95);
    };

    private final Consumer<EntityLivingBase> cast_greed = (target) -> {
      this.setFightMode(true);
      this.setCastGreed(true);
      this.setImmovable(true);
      animator.performAttack(ANIM_CAST_GREED);
        this.playSound(ModSoundHandler.CHAMPION_SUCK, 3F, 1.0F);
      this.doingCast = true;
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 3);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 30);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 60);

      addEvent(()-> {
          this.doingCast = false;
      }, 60);

      addEvent(()-> {
          world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
          List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8), e -> !(e instanceof EntityHeliosMobBase));
          if(!targets.isEmpty()) {
              for(EntityLivingBase base: targets) {
                  double healthDamage = base.getMaxHealth() * 0.50;
                  if(healthDamage > 25) {
                      healthDamage = 25;
                  }
                  base.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), (float) healthDamage);
                  base.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 2, false, false));
              }
          }
          this.playSound(ModSoundHandler.UTIL_MANUS_SWORD_CAST, 2.5F, 0.5F / (rand.nextFloat() * 0.4F + 0.2F));
      }, 65);

      addEvent(()-> {
        this.setFightMode(false);
        this.setCastGreed(false);
        this.setImmovable(false);
      }, 90);
    };

    private final Consumer<EntityLivingBase> stomp_attack = (target) -> {
      this.setStomp(true);
      this.setImmovable(true);
      this.setFightMode(true);
      animator.performAttack(ANIM_STOMP);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 20);
      addEvent(()-> {
        //stomp action
          this.playSound(ModSoundHandler.CHAMPION_STOMP, 2.0f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
          new ActionKnightStomp().performAction(this, target);
          world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
          Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.6, 1.2, 0)));
          DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
          float damage =(float) (this.getAttack() * 0.75);
          ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 100);
      }, 20);

      addEvent(()-> {
        this.setStomp(false);
        this.setImmovable(false);
        this.setFightMode(false);
      }, 45);
    };

    private final Consumer<EntityLivingBase> triple_stomp_attack = (target) -> {
        this.setTripleStomp(true);
        this.setImmovable(true);
        this.setFightMode(true);
        animator.performAttack(ANIM_TRIPLE_STOMP);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 20);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 40);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 55);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 87);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 100);
        addEvent(()-> {
            //stomp action
            this.playSound(ModSoundHandler.CHAMPION_STOMP, 2.0f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleStomp().performAction(this, target);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.6, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack() * 0.75);
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 100);
        }, 20);

        addEvent(()-> {
            //stomp action
            this.playSound(ModSoundHandler.CHAMPION_STOMP, 2.0f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleStomp().performAction(this, target);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.6, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack() * 0.75);
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 100);
        }, 63);

        addEvent(()-> {
            //stomp action
            this.playSound(ModSoundHandler.CHAMPION_STOMP, 2.0f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
            new ActionKnightTripleStomp().performAction(this, target);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.6, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack() * 0.75);
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 100);
        }, 105);

        addEvent(()-> {
            this.tripleStompCooldown = 25 * 20;
            this.setTripleStomp(false);
            this.setImmovable(false);
            this.setFightMode(false);
        }, 135);
    };

    private final Consumer<EntityLivingBase> fast_dash = (target) -> {
      this.setDash(true);
      this.setImmovable(true);
      this.setFightMode(true);
      animator.performAttack(ANIM_DASH);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 50);

        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(-6));
            addEvent(()-> {
                this.setImmovable(false);
                world.setEntityState(this, ModUtils.FOURTH_PARTICLE_BYTE);
                this.playSound(ModSoundHandler.CHAMPION_TELEPORT, 0.9f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f));
                ModUtils.attemptTeleport(targetedPos, this);
                this.destroyBlocksInSwing(new Vec3d(0, 1.75, 0), 1.2);
            }, 3);
        }, 15);

        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(1));
          //  this.faceEntity(target, 180, 30);
            addEvent(()-> {
                this.lockLook = true;
                this.setImmovable(false);
                double distance = this.getPositionVector().distanceTo(targetedPos);
                ModUtils.leapTowards(this, targetedPos, (float) (distance * 0.33),0.1F);
                this.playSound(ModSoundHandler.CHAMPION_DASH, 1f, 1.0F);
            }, 4);
        }, 26);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).disablesShields().build();
            float damage =(float) (this.getAttack() * 1.4);
            ModUtils.handleAreaImpact(2.25f, (e) -> damage, this, offset, source, 0.9f, 0, false, ModPotions.GREED, 1, 200);
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(1.5, 1.2, 0), 2.1);
        }, 38);

        addEvent(()-> {
            this.setImmovable(true);
        }, 43);

        addEvent(()-> {
            this.lockLook = false;
        }, 55);

      addEvent(()-> {
        this.setDash(false);
        this.setImmovable(false);
        this.setFightMode(false);
      }, 60);
    };

    //circle swing
    private final Consumer<EntityLivingBase> circle_swing = (target) -> {
      this.setCircleSwing(true);
      this.setImmovable(true);
      this.setFightMode(true);
      animator.performAttack(ANIM_CIRCLE_SWING);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 50);
        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(1.5));
            this.faceEntity(target, 180, 30);
            addEvent(()-> {
                this.lockLook = true;
                this.setImmovable(false);
                double distance = this.getPositionVector().distanceTo(targetedPos);
                ModUtils.leapTowards(this, targetedPos, (float) (distance * 0.29),0.1F);
                this.playSound(ModSoundHandler.CHAMPION_DASH, 1f, 1.0F);
            }, 4);
        }, 20);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).disablesShields().build();
            float damage =(float) (this.getAttack() * 1.25);
            ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            this.playSound(ModSoundHandler.CHAMPION_SWING, 1.3f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(0, 1.2, 0), 2.1);
        }, 30);

        addEvent(()-> {
            this.setImmovable(true);
        }, 43);

        addEvent(()-> {
            this.lockLook = false;
        }, 50);

        addEvent(()-> {
            this.setImmovable(false);
            this.setCircleSwing(false);
            this.setFightMode(false);
        }, 60);
    };

    //overhead Swing
    private final Consumer<EntityLivingBase> overhead_swing = (target) -> {
      this.setImmovable(true);
      this.setFightMode(true);
      this.setOverheadSwing(true);
      animator.performAttack(ANIM_OVERHEAD_SWING);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 2);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 40);
        addEvent(()-> {
            //do Line
            new ActionKnightLineAOE().performAction(this, target);
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.4, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).disablesShields().build();
            float damage =(float) (this.getAttack() * 1.25);
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            this.playSound(ModSoundHandler.CHAMPION_SUMMON_AOE, 1.5f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(1.2, 1.2, 0), 0.9);
            this.lockLook = true;
        }, 24);

        addEvent(()-> {
            this.lockLook = false;
        }, 35);

        addEvent(()-> {
            this.setOverheadSwing(false);
            this.setFightMode(false);
            this.setImmovable(false);
        }, 45);
    };

    //Swing Attack
    private final Consumer<EntityLivingBase> swing_attack = (target) -> {
        this.setImmovable(true);
        this.setFightMode(true);
        this.setSwing(true);
        animator.performAttack(ANIM_SWING);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 5);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 40);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 80);
        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(-1));
            this.faceEntity(target, 180, 30);
            addEvent(()-> {
                this.lockLook = true;
                this.setImmovable(false);
                double distance = this.getPositionVector().distanceTo(targetedPos);
                ModUtils.leapTowards(this, targetedPos, (float) (distance * 0.27),0.1F);
            }, 4);
        }, 14);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack());
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            this.playSound(ModSoundHandler.CHAMPION_SWING, 1f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(1.2, 1.2, 0), 0.9);
        }, 23);

        addEvent(()-> {
            this.setImmovable(true);
            this.lockLook = false;
        }, 35);

        addEvent(()-> {
            Vec3d posSet = target.getPositionVector().subtract(this.getPositionVector()).normalize();
            Vec3d targetedPos = target.getPositionVector().add(posSet.scale(-1));
            this.faceEntity(target, 180, 30);
            addEvent(()-> {
                this.lockLook = true;
                this.setImmovable(false);
                double distance = this.getPositionVector().distanceTo(targetedPos);
                ModUtils.leapTowards(this, targetedPos, (float) (distance * 0.27),0.1F);
            }, 4);
        }, 48);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage =(float) (this.getAttack());
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            this.playSound(ModSoundHandler.CHAMPION_SWING, 1f, 0.7f / (rand.nextFloat() * 0.4f + 0.2f));
            this.destroyBlocksInSwing(new Vec3d(1.2, 1.2, 0), 0.9);
        }, 57);

        addEvent(()-> {
            this.setImmovable(true);
        }, 65);

        addEvent(()-> {
            this.lockLook = false;
        }, 75);

        addEvent(()-> {
            this.setImmovable(false);
            this.setFightMode(false);
            this.setSwing(false);
        }, 90);
    };


    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        //ulta scroll of greed
        if(id == ModUtils.PARTICLE_BYTE) {
            Main.proxy.spawnParticle(4, this.posX, this.posY + 5.5, this.posZ, 0, 0, 0, 0);
            ModUtils.performNTimes(65, (i) -> {
                Main.proxy.spawnParticle(1, this.posX + ModRand.getFloat(16), this.posY + ModRand.getFloat(4), this.posZ + ModRand.getFloat(16), 0, 0.06, 0, 9320921);
            });
        }

        if(id == ModUtils.SECOND_PARTICLE_BYTE) {
            Main.proxy.spawnParticle(3, this.posX, this.posY + 0.1, this.posZ, 0, 0, 0, 9320921);
        }

        if(id == ModUtils.THIRD_PARTICLE_BYTE) {
            Main.proxy.spawnParticle(4, this.posX, this.posY + 5.5, this.posZ, 0, 0, 0, 0);
            ModUtils.circleCallback(8, 16, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y);
                Main.proxy.spawnParticle(1, this.posX + pos.x, this.posY + 1, this.posZ + pos.z, 0,0.1,0, 9320921);
            });

            ModUtils.circleCallback(8, 14, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y);
                Main.proxy.spawnParticle(1, this.posX + pos.x, this.posY + 2, this.posZ + pos.z, 0,0.1,0, 9320921);
            });
        }

        if(id == ModUtils.FOURTH_PARTICLE_BYTE) {
            Main.proxy.spawnParticle(1, this.posX, this.posY + 0.5, this.posZ, 0, 0.06, 0, 16749027);
            Main.proxy.spawnParticle(1, this.posX, this.posY + 1.5, this.posZ, 0, 0.06, 0, 16749027);
            Main.proxy.spawnParticle(1, this.posX, this.posY + 2.5, this.posZ, 0, 0.06, 0, 16749027);
            ModUtils.circleCallback(1, 20, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y);
                Main.proxy.spawnParticle(1, this.posX + pos.x, this.posY + 1, this.posZ + pos.z, 0,0,0, 16749027);
                Main.proxy.spawnParticle(1, this.posX + pos.x, this.posY + 2, this.posZ + pos.z, 0,0,0, 16749027);
            });
        }

        if(id == ModUtils.FIFTH_PARTICLE_BYTE) {
            ModUtils.performNTimes(6, (i) -> {
                Main.proxy.spawnParticle(5, this.posX + ModRand.getFloat(1F), this.posY + 1 + ModRand.getFloat(1F), this.posZ + ModRand.getFloat(1), 0, -0.03, 0, 16749027);
            });
        }
    }


    private void destroyBlocksInSwing(Vec3d offset, double size) {
        AxisAlignedBB box = getEntityBoundingBox().grow(size, 0.1, size).offset(ModUtils.getRelativeOffset(this, new Vec3d(offset.x, offset.y, offset.z)));
        ModUtils.destroyBlocksInAABB(box, world, this);
    }

    //We want to ensure that idle and walking is still handled
    //While making sure our other states and animations still take priority in there execution
    public void updateAnimations() {
        boolean isMovingOnGround = moveAnalysis.isMovingHorizontally() && this.onGround;

        if(!this.isFightMode() && !this.isIdleWake() && !this.isSleepMode() && !this.isTransformPhase() && !this.isDeathState()) {
            animator.handleIdleStates(isMovingOnGround);
        }

        if(this.isSleepMode()) {
            animator.setStateAnimation(ANIM_SLEEP, true);
        }

    }

    @Override
    public void setCustomNameTag(@Nonnull String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundHandler.CHAMPION_HURT;
    }


    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundHandler.CHAMPION_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(ModSoundHandler.CHAMPION_STEP, 0.25F, 0.8f + ModRand.getFloat(0.3F));
    }

    @Override
    public float getShakeIntensity(Entity viewer, float partialTicks) {
        if(this.isShaking()) {
            double dist = getDistance(viewer);
            float screamMult = (float) (1.0F - dist / 24.0F);
            if (dist >= 24.0F) {
                return 0.0F;
            }
            return (float) ((Math.sin(((partialTicks)/this.shakeTime) * Math.PI) + 0.5F) * 0.6F * screamMult);
        }
        return 0;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(this.isIdleWake() || this.isSleepMode() || source.getImmediateSource() instanceof EntityHeliosMobBase || this.isTransformPhase() || this.isDeathState()) {
            return false;
        }

        if(source.isProjectile()) {
            return super.attackEntityFrom(source, (float) (amount * 0.5));
        }

        if(amount > 12) {
            world.setEntityState(this, ModUtils.FIFTH_PARTICLE_BYTE);
            //do some particles and play a sound to indicate shielding resistance
            return super.attackEntityFrom(source, 12);
        }

        return super.attackEntityFrom(source, amount);
    }


    @Override
    public void onDeath(DamageSource cause) {
        this.setHealth(0.00001F);
        if(!this.isDeathState()) {
            this.clearEvents();
        }
        this.setDeathState(true);
        animator.setStateAnimation(ANIM_DEATH);
        this.setImmovable(true);
        this.lockLook = true;
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 1);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 12);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 60);
        addEvent(()-> this.playSound(ModSoundHandler.CHAMPION_ARMOR, 0.6f, 0.6f / (rand.nextFloat() * 0.4f + 0.2f)), 140);
        addEvent(()-> {
            if(!world.isRemote) {
                this.setImmovable(true);
                this.lockLook = true;
                EntityGreedHand hand = new EntityGreedHand(world, (float) this.posX - 1, (float) this.posY, (float) this.posZ, this);
                world.spawnEntity(hand);
            }
        }, 80);

        addEvent(()-> {
            if(!world.isRemote) {
                for(int i = 0; i <= 100; i+=10) {
                    if(!world.isRemote) {
                        addEvent(() -> {
                            EntityXPOrb orb = new EntityXPOrb(world, this.posX, this.posY, this.posZ, 200 / 10);
                            orb.setPosition(this.posX, this.posY + 1, this.posZ);
                            world.spawnEntity(orb);
                        }, i);
                    }
                }
            }
        }, 100);

        addEvent(()-> {
            this.setDead();
            this.setDropItemsWhenDead(true);
            this.setDeathState(false);
        }, 199);

        super.onDeath(cause);
    }

    private static final ResourceLocation LOOT_MOB = new ResourceLocation(ModReference.MOD_ID, "nameless_champion");

    @Override
    protected ResourceLocation getLootTable() {
        return LOOT_MOB;
    }

    @Override
    protected boolean canDropLoot() {
        return true;
    }

    @Nullable
    @Override
    public SoundEvent getBossMusic() {
        return ModSoundHandler.CHAMPION_SOUND_TRACK;
    }
}
