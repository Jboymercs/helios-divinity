package com.unseen.helios.common.entity.entities;

import com.unseen.helios.common.entity.ai.attack_manager.EntityAITimedAttack;
import com.unseen.helios.common.entity.ai.attack_manager.IAttack;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

import java.util.function.Consumer;

public class EntityManusMinion extends EntityHeliosMobBase implements IAnimatable, IAnimationTickable, IAttack {

    private final AnimationFactory factory = new AnimationFactory(this);

    private final String ANIM_ATTACK = "attack";
    private final String ANIM_WALK = "walk";
    private final String ANIM_IDLE = "idle";
    private final String ANIM_SUMMON = "summon";

    private static final DataParameter<Boolean> ATTACK_MODE = EntityDataManager.createKey(EntityManusMinion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SUMMON_MODE = EntityDataManager.createKey(EntityManusMinion.class, DataSerializers.BOOLEAN);

    private void setAttackMode(boolean value) {this.dataManager.set(ATTACK_MODE, Boolean.valueOf(value));}
    public boolean isAttackMode() {return this.dataManager.get(ATTACK_MODE);}
    private void setSummonMode(boolean value) {this.dataManager.set(SUMMON_MODE, Boolean.valueOf(value));}
    public boolean isSummonMode() {return this.dataManager.get(SUMMON_MODE);}

    public EntityManusMinion(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setSize(0.75F, 1.9F);
        this.isNonHealableCreature = true;
        this.summonMob();
    }

    public EntityManusMinion(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 1.9F);
        this.isNonHealableCreature = true;
        this.summonMob();
    }

    private void summonMob() {
        this.setSummonMode(true);
        this.setFightMode(true);
        this.setImmovable(true);

        addEvent(()-> {
            this.setSummonMode(false);
            this.setFightMode(false);
            this.setImmovable(false);
        }, 30);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("Attack_Mode", this.isAttackMode());
        nbt.setBoolean("Summon_Mode", this.isSummonMode());
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setAttackMode(nbt.getBoolean("Attack_Mode"));
        this.setSummonMode(nbt.getBoolean("Summon_Mode"));
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACK_MODE, Boolean.valueOf(false));
        this.dataManager.register(SUMMON_MODE, Boolean.valueOf(true));
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
       // this.tasks.addTask(4, new EntityDraugrMeleeAI<>(this, 1, 30, 4, 0.15F));
        this.tasks.addTask(4, new EntityAITimedAttack<EntityManusMinion>(this, 1, 30, 2, 0.2F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
    }


    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    private Consumer<EntityLivingBase> prevAttack;

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if(!this.isFightMode()) {
            prevAttack = swing_one;
            prevAttack.accept(target);
        }
        return 30;
    }

    private final Consumer<EntityLivingBase> swing_one = (target) -> {
      this.setFightMode(true);
      this.setImmovable(true);
      this.setAttackMode(true);

      addEvent(()-> this.lockLook = true, 15);

      addEvent(()-> {
          Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 1, 0)));
          DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
          float damage = this.getAttack();
          ModUtils.handleAreaImpact(0.75f, (e) -> damage, this, offset, source, 0.4f, 0, false);
          this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
      }, 23);

      addEvent(()-> this.lockLook = false, 30);

      addEvent(() -> {
          this.setFightMode(false);
          this.setImmovable(false);
          this.setAttackMode(false);
      }, 40);
    };

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "idle_controller", 0, this::predicateIdle));
        data.addAnimationController(new AnimationController(this, "legs_controller", 0, this::predicateLegs));
        data.addAnimationController(new AnimationController(this, "attack_controller", 0, this::predicateAttacks));
    }

    private <E extends IAnimatable> PlayState predicateAttacks(AnimationEvent<E> event) {
        if(this.isFightMode()) {
            if(this.isAttackMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_ATTACK, false));
                return PlayState.CONTINUE;
            }
            if(this.isSummonMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SUMMON, false));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private<E extends IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {
        if(event.getLimbSwingAmount() >= -0.09F && event.getLimbSwingAmount() <= 0.09F && !this.isFightMode() && !this.isSummonMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicateLegs(AnimationEvent<E> event) {
        if(!(event.getLimbSwingAmount() >= -0.10F && event.getLimbSwingAmount() <= 0.10F) && !this.isFightMode() && !this.isSummonMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALK, true));
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isSummonMode()) {
            return false;
        }

        return super.attackEntityFrom(source, (float) (amount * 0.75));
    }

}
