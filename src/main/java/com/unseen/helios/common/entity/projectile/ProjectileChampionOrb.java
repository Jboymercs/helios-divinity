package com.unseen.helios.common.entity.projectile;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.base.Projectile;
import com.unseen.helios.handlers.damageSource.ModDamageSource;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModUtils;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProjectileChampionOrb extends Projectile {

    public ProjectileChampionOrb(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
    }

    public ProjectileChampionOrb(World worldIn) {
        super(worldIn);
    }

    public ProjectileChampionOrb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        if(world.rand.nextInt(3) == 0) {
            for (int i = 0; i < 1; i++) {
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
    protected void onHit(RayTraceResult result) {

        if(result.entityHit instanceof EntityLivingBase) {
            ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 1, false, false));
        }
        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.PROJECTILE)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .stoppedByArmorNotShields().build();

        ModUtils.handleBulletImpact(result.entityHit, this, this.getDamage(), source);
        this.playSound(SoundEvents.BLOCK_STONE_BREAK, 0.8f, 0.8f / (rand.nextFloat() * 0.4f + 0.4f));
        super.onHit(result);
    }


}
