package com.unseen.helios.proxy;

import com.unseen.helios.Main;
import com.unseen.helios.events.MessageDirectionForRender;
import com.unseen.helios.events.ParticleSSMesage;
import com.unseen.helios.message.PacketGreedCapability;
import com.unseen.helios.util.ModReference;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class CommonProxy {


    public EntityPlayer getClientPlayer() {
        return null;
    }

    public World getClientWorld() {
        return getClientPlayer().world;
    }

    public void init() {
        int packetId = 0;
        Main.network = NetworkRegistry.INSTANCE.newSimpleChannel(ModReference.CHANNEL_NETWORK_NAME);
        Main.network.registerMessage(ParticleSSMesage.Handler.class, ParticleSSMesage.class, packetId++, Side.CLIENT);
        Main.network.registerMessage(MessageDirectionForRender.Handler.class, MessageDirectionForRender.class, packetId++, Side.CLIENT);
        Main.network.registerMessage(PacketGreedCapability.Handler.class, PacketGreedCapability.class, packetId++, Side.SERVER);

    }

    public void registerEventHandlers() {

    }

    public Object getArmorModel(Item item, EntityLivingBase entity) {
        return null;
    }

    public boolean doesPlayerHaveXAdvancement(EntityPlayer player, ResourceLocation Id) {
        if (player instanceof EntityPlayerMP) {
            Advancement adv = ((EntityPlayerMP) player).getServerWorld().getAdvancementManager().getAdvancement(Id);
            return adv != null && ((EntityPlayerMP) player).getAdvancements().getProgress(adv).isDone();
        }
        return false;
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void handleAnimationPacket(int entityId, int index) {

    }

    public void stopMusic() {}

    public void playMusic(SoundEvent soundEvent, @Nullable Entity linkedEntity) {}


    /** Handles spawning of Particles */
    public void spawnParticle(int particleId, World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, int... parameters)
    {
        if (world.isRemote)
        { spawnParticle(particleId, posX, posY, posZ, speedX, speedY, speedZ, parameters); }
        else
        {
            System.out.println("Printing Particles");
            Main.network.sendToAllTracking( new ParticleSSMesage(particleId, posX, posY, posZ, speedX, speedY, speedZ, parameters), new NetworkRegistry.TargetPoint(world.provider.getDimension(), posX, posY, posZ, 0.0D)); }
    }

    /** This exists to be overridden in the ClientProxy! */
    public void spawnParticle(int particleId, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, int... parameters) {}
}
