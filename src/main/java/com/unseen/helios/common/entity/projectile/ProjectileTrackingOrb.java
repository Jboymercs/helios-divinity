package com.unseen.helios.common.entity.projectile;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProjectileTrackingOrb extends Projectile {
    private boolean isImmune;
    public ProjectileTrackingOrb(World worldIn, EntityLivingBase throwerIn, float damage, EntityLivingBase target) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
        this.isImmuneToFire = true;
        this.selectedPlayer = target;
        setExplodeOnImpact = true;
        this.setSize(0.5F, 0.5F);


    }

    public ProjectileTrackingOrb(World worldIn, EntityLivingBase throwerIn, float damage, EntityLivingBase target, boolean isImmune) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
        this.isImmuneToFire = true;
        this.selectedPlayer = target;
        this.setSize(0.5F, 0.5F);
        this.isImmune = isImmune;
    }

    public ProjectileTrackingOrb(World worldIn) {
        super(worldIn);
    }

    public ProjectileTrackingOrb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    private int randomMovement = 20;
    private int haltMovement = 15;
    private boolean setExplodeOnImpact = false;
    private Vec3d holdPos;
    private boolean setMovement;

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(this.isImmune) {
            if(randomMovement > 0) {
                this.motionX += (world.rand.nextFloat() - world.rand.nextFloat())/3;
                this.motionZ += (world.rand.nextFloat() - world.rand.nextFloat())/3;
                this.motionY += 0.05;
                randomMovement--;
            } else {
                this.isImmune = false;
                this.setMovement = true;
                holdPos = this.getPositionVector();
            }
        } else if(this.setMovement){
            if(haltMovement > 0) {
                this.motionX = 0;
                this.motionY = 0;
                this.motionZ = 0;
                if(holdPos != null) {
                    ModUtils.setEntityPosition(this, holdPos);
                }
                haltMovement--;
            } else {
                setMovement = false;
            }
        }
        this.onTrackUpdate();
    }

    public static final int PARTICLE_AMOUNT = 1;

    @Override
    protected void spawnParticles() {
        if(world.rand.nextInt(3) == 0) {
            for (int i = 0; i < PARTICLE_AMOUNT; i++) {
                Main.proxy.spawnParticle(1, this.posX, this.posY, this.posZ, 0, 0, 0, 9320921);
            }
        }
    }

    public float getBrightness()
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    protected void spawnImpactParticles() {
        Main.proxy.spawnParticle(2, this.posX, this.posY, this.posZ, 0, 0, 0, 9320921);
    }

    private EntityLivingBase selectedPlayer;

    public void onTrackUpdate() {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(30, 5, 30));
        if(!list.isEmpty()) {
            for(Entity entityIn : list) {
                if(entityIn == selectedPlayer) {
                    // this.selectedPlayer = (EntityLivingBase) entityIn;
                }
            }
        }

        List<Entity> listNearby = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5D, 0.5D, 0.5D));
        if(!listNearby.isEmpty()) {
            for(Entity entityIn : listNearby) {
             if(!(entityIn instanceof ProjectileTrackingOrb) && !(entityIn instanceof EntityNamelessChampion)) {
                    if(this.isImmune) {
                        if(!(entityIn instanceof EntityArrow)) {
                            if(!world.isRemote && this.shootingEntity != null) {
                                DamageSource source = ModDamageSource.builder()
                                        .type(ModDamageSource.EXPLOSION)
                                        .directEntity(this.shootingEntity)
                                        .stoppedByArmorNotShields().disablesShields().build();

                                ModUtils.handleAreaImpact(2.5F, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), source);
                                this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75F, 1.2f + ModRand.getFloat(0.3f));
                            }
                        }
                    } else {
                        if (!world.isRemote && this.shootingEntity != null) {
                            DamageSource source = ModDamageSource.builder()
                                    .type(ModDamageSource.EXPLOSION)
                                    .directEntity(this.shootingEntity)
                                    .stoppedByArmorNotShields().disablesShields().build();

                            ModUtils.handleAreaImpact(2.5F, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), source);
                            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75F, 1.2f + ModRand.getFloat(0.3f));
                        }
                    }
                    this.setDead();
                }
            }
        }
        //this.setFire(3);
        if(selectedPlayer != null && !this.isImmune) {
            Vec3d posToTravelToo = selectedPlayer.getPositionVector().add(ModUtils.yVec(1.4D));
            double d0 = ((posToTravelToo.x - this.posX) * 0.0010);
            double d1 = (((posToTravelToo.y) - this.posY) * 0.0008);
            double d2 = ((posToTravelToo.z - this.posZ) * 0.0010);
            this.addVelocity(d0, d1, d2);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if(this.setExplodeOnImpact && !world.isRemote && this.shootingEntity != null) {
          //  this.world.newExplosion(this, this.posX, this.posY, this.posZ, 1, false, false);
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.EXPLOSION)
                    .directEntity(this.shootingEntity)
                    .stoppedByArmorNotShields().disablesShields().build();

            ModUtils.handleAreaImpact(2.5F, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), source);
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75F, 1.2f + ModRand.getFloat(0.3f));
            if(result.entityHit != null) {
                if(result.entityHit instanceof EntityLivingBase) {
                    ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 1, false, false));
                }
            }
        }

        if(!this.isImmune) {
            super.onHit(result);
        }

    }
}
