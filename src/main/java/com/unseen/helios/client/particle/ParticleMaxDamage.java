package com.unseen.helios.client.particle;


import com.unseen.helios.util.ModReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMaxDamage extends ParticleSSBase {
    private static final ResourceLocation PIXEL_TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/particle/max_damage.png");

    public ParticleMaxDamage(TextureManager textureManager, World world, double x, double y, double z, double movementX, double movementY, double movementZ, int color)
    {
        super(textureManager, world, x, y, z, movementX, movementY, movementZ, PIXEL_TEXTURE, 0);
        this.textureManager = textureManager;
        this.motionX = movementX;
        this.motionY = movementY;
        this.motionZ = movementZ;
        this.particleMaxAge = (int)25;
        this.texSheetSeg = 1;
        this.renderYOffset = 0.01F;
        this.particleScale =  0.5F;
        this.setAlphaF(0.95F);
        float[] colors = decimalIntToRGB(color);
        float shade = 0.9F;
        setRBGColorF( 1.0F - (1.0F - colors[0]) * shade, 1.0F - (1.0F - colors[1]) * shade, 1.0F - (1.0F - colors[2]) * shade );
    }

    public void onUpdate()
    {
        super.onUpdate();
        // this.texSpot = this.particleAge * 3 / (this.particleMaxAge);
        this.particleScale+= 0.06;
        this.texSpot = Math.min(this.particleAge * 5 / (this.particleMaxAge), 3);
        this.prevParticleAngle = this.particleAngle;

    }

    public Vec3d[] particleVertexRendering(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, float particleSize)
    {
        Vec3d[] wow = new Vec3d[]{
                new Vec3d(-rotationX * particleSize - rotationXY * particleSize, -rotationZ * particleSize, -rotationYZ * particleSize - rotationXZ * particleSize),
                new Vec3d(-rotationX * particleSize + rotationXY * particleSize, rotationZ * particleSize, -rotationYZ * particleSize + rotationXZ * particleSize),
                new Vec3d(rotationX * particleSize + rotationXY * particleSize, rotationZ * particleSize, rotationYZ * particleSize + rotationXZ * particleSize),
                new Vec3d(rotationX * particleSize - rotationXY * particleSize, -rotationZ * particleSize, rotationYZ * particleSize - rotationXZ * particleSize)
        };

        float angle = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
        float f9 = MathHelper.cos(angle * 0.5F);
        float f10 = MathHelper.sin(angle * 0.5F) * (float)cameraViewDir.x;
        float f11 = MathHelper.sin(angle * 0.5F) * (float)cameraViewDir.y;
        float f12 = MathHelper.sin(angle * 0.5F) * (float)cameraViewDir.z;
        Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

        for (int l = 0; l < 4; ++l)
        {
            wow[l] = vec3d.scale(2.0D * wow[l].dotProduct(vec3d)).add(wow[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(wow[l]).scale((double)(2.0F * f9)));
        }

        return wow;
    }

    @Override
    public int getBrightnessForRender(float partialTicks)
    { return brightnessIncreaseToFull(partialTicks); }


    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        @Override
        public Particle createParticle(int particleId, World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, int... parameters)
        {
            return new ParticleMaxDamage(Minecraft.getMinecraft().getTextureManager(), world, posX, posY, posZ, 0, 0, 0, parameters[0]); }
    }
}
