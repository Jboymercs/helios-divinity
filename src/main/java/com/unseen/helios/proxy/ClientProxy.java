package com.unseen.helios.proxy;

import com.unseen.helios.client.entity.base.util.GlowingMetadataSection;
import com.unseen.helios.client.item.model.ModelManusHelmet;
import com.unseen.helios.client.particle.*;
import com.unseen.helios.events.EventBossMusic;
import com.unseen.helios.handlers.RenderHandler;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.CameraPositionHandler;
import com.unseen.helios.util.EntityMusicPlayerHelios;
import com.unseen.helios.util.GlowingMetadataSectionSerializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy{

    private final ModelBiped MODEL_MANUS_HELMET = new ModelManusHelmet(0f);

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().world;
    }


    @Override
    public void init() {
        //Handles Renders of Entities
        RenderHandler.registerAllEntityRenders();
        RenderHandler.registerGeoEntityRenderers();
        RenderHandler.registerProjectileRenderers();
        // Add custom metadataserializers
        Minecraft mc = Minecraft.getMinecraft();
        mc.metadataSerializer.registerMetadataSectionType(new GlowingMetadataSectionSerializer(), GlowingMetadataSection.class);
        super.init();
    }

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(CameraPositionHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(EventBossMusic.INSTANCE);
    }

    @Override
    public boolean doesPlayerHaveXAdvancement(EntityPlayer player, ResourceLocation Id) {
        if(player instanceof EntityPlayerSP) {
            ClientAdvancementManager manager = ((EntityPlayerSP) player).connection.getAdvancementManager();
            Advancement advancement = manager.getAdvancementList().getAdvancement(Id);
            if(advancement == null) {
                return false;
            }
            AdvancementProgress progress = manager.advancementToProgress.get(advancement);
            return progress != null && progress.isDone();
        }

        return super.doesPlayerHaveXAdvancement(player, Id);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void spawnParticle(int particle, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, int... parameters)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        World world = minecraft.world;
        minecraft.effectRenderer.addEffect(getFactory(particle).createParticle(0, world, posX, posY, posZ, speedX, speedY, speedZ, parameters));
    }

    @SideOnly(Side.CLIENT)
    public static IParticleFactory getFactory(int particleId) {
        switch (particleId) {
            default:
            case 1:
                return new ParticleEffectDot.Factory();
            case 2:
                return new ParticleImpact.Factory();
            case 3:
                return new ParticleLargeImpact.Factory();
            case 4:
                return new ParticleManusRune.Factory();
            case 5:
                return new ParticleMaxDamage.Factory();
        }
    }

    @Override
    public void playMusic(SoundEvent soundEvent, @Nullable Entity linkedEntity) {
        if (Minecraft.getMinecraft().world != null) {
            SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

            if (!EntityMusicPlayerHelios.isPlaying(soundEvent, linkedEntity)) {
                stopMusic();
                soundHandler.playSound(new EntityMusicPlayerHelios(linkedEntity, soundEvent));
            }
        }
    }


    @Override
    public void stopMusic() {
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
    }

    @Override
    public Object getArmorModel(Item item, EntityLivingBase entity) {
        if(item == ModItems.MANUS_HELMET) {
            return MODEL_MANUS_HELMET;
        }
        return null;
    }
}
