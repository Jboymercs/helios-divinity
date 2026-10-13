package com.unseen.helios.events;

import com.unseen.helios.Main;
import com.unseen.helios.capability.CapabilityPlayerGreed;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.entities.effects.greed_hand.EntityGreedHand;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.message.PacketGreedCapability;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import com.unseen.helios.util.PlayerGreedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class EventPlayerGreed {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        { event.addCapability(CapabilityPlayerGreed.ID, new CapabilityPlayerGreed.Provider(new CapabilityPlayerGreed.HeliosPlayerGreedMethods(), CapabilityPlayerGreed.PLAYER_GREED_CAP, null)); }
    }


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        CapabilityPlayerGreed.IPlayerGreedCapability capFog = PlayerGreedUtils.getCapability(event.player);
        if (capFog == null) return;

        Main.network.sendTo(new PacketGreedCapability(event.player.getEntityId(), true), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        EntityPlayer player = event.player;

        /* Only update every second. Interpolation of the fog makes this doable. */
        if (player.ticksExisted % 20 != 0) return;

        if (!player.world.isRemote)
        {

            //
            int greedCounter = GeneralConfig.mod_greed_cap;
            //
            if(player.isPotionActive(ModPotions.GREED) && !player.isPotionActive(ModPotions.STAGNATION)) {
                int level = Objects.requireNonNull(player.getActivePotionEffect(ModPotions.GREED)).getAmplifier() + 1;
                PlayerGreedUtils.setPlayerGreedProgress(player, (int) (PlayerGreedUtils.getPlayerGreedProgress(player) + level));
            }

            if(player.isPotionActive(MobEffects.REGENERATION) && PlayerGreedUtils.getPlayerGreedProgress(player) > 0 && !player.isPotionActive(ModPotions.STAGNATION)) {
                int level = Objects.requireNonNull(player.getActivePotionEffect(MobEffects.REGENERATION)).getAmplifier() + 1;
                PlayerGreedUtils.setPlayerGreedProgress(player, (int) PlayerGreedUtils.getPlayerGreedProgress(player) - level);
            }

            //armor bonuses
            if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.MANUS_HELMET &&
                    player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.MANUS_CHESTPLATE &&
                    player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.MANUS_BOOTS) {

                if(PlayerGreedUtils.getPlayerGreedProgress(player) > 0 && PlayerGreedUtils.getPlayerGreedProgress(player) < GeneralConfig.mod_greed_cap - 2) {
                    int weakModifier = (int) ((GeneralConfig.mod_greed_cap - PlayerGreedUtils.getPlayerGreedProgress(player)));
                    if(weakModifier < 5) {
                        weakModifier = 2;
                    }
                    if(player.world.rand.nextInt(weakModifier) == 0) {
                        List<EntityLivingBase> nearbyManus = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(16.0D), e -> !e.getIsInvulnerable());
                        if(!nearbyManus.isEmpty()) {
                            for(EntityLivingBase base : nearbyManus) {
                                if(!(base instanceof EntityHeliosMobBase) && base != player) {
                                    base.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0, false, true));
                                }
                            }
                        }
                        player.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 0, false, false));
                    }
                }

            }

            //weird side effects
            if(PlayerGreedUtils.getPlayerGreedProgress(player) > (greedCounter * 0.25)) {

                if(player.world.rand.nextInt(15) == 0) {
                    ModUtils.performNTimes(1, (i) -> {
                        Main.proxy.spawnParticle(1, player.posX + ModRand.getFloat(3), player.posY + ModRand.getFloat(3), player.posZ + ModRand.getFloat(3), 0, 0.06, 0, 9320921);
                    });
                }
            }

            //slight warnings
            if(PlayerGreedUtils.getPlayerGreedProgress(player) > (greedCounter * 0.5)) {

                if(player.world.rand.nextInt(75) == 0) {
                    player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 0, false, false));
                }

                if(player.world.rand.nextInt(10) == 0) {
                    ModUtils.performNTimes(3, (i) -> {
                        Main.proxy.spawnParticle(1, player.posX + ModRand.getFloat(3), player.posY + ModRand.getFloat(3), player.posZ + ModRand.getFloat(3), 0, 0.06, 0, 9320921);
                    });
                }
            }

            //big warnings
            if(PlayerGreedUtils.getPlayerGreedProgress(player) > (greedCounter * 0.75)) {

                //random chance for added potion effects to occur
                if(player.world.rand.nextInt(250) == 0) {
                    player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1, false, false));
                    player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0, false, false));
                    player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0, false, false));
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 600, 1, false, false));
                }

                if(player.world.rand.nextInt(5) == 0) {
                    ModUtils.performNTimes(5, (i) -> {
                        Main.proxy.spawnParticle(1, player.posX + ModRand.getFloat(3), player.posY + ModRand.getFloat(3), player.posZ + ModRand.getFloat(3), 0, 0.06, 0, 9320921);
                    });
                }
            }

            //death and desolation
            if (PlayerGreedUtils.getPlayerGreedProgress(player) > (greedCounter)) {
                PlayerGreedUtils.setPlayerGreedProgress(player, (int) PlayerGreedUtils.getPlayerGreedProgress(player) - 100);
                EntityGreedHand hand_of_greed = new EntityGreedHand(player.world, (float) player.posX - 1, (float) player.posY, (float) player.posZ, player);
                player.world.spawnEntity(hand_of_greed);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerWearingManus(LivingHurtEvent event) {
            if(event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = ((EntityPlayer) event.getEntityLiving());
                //armor bonuses
                if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.MANUS_HELMET &&
                        player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.MANUS_CHESTPLATE &&
                        player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.MANUS_BOOTS) {
                    if(ModRand.range(1, 5) == 3) {
                        double newAmount = event.getAmount() * 0.67;
                        event.setAmount((float) newAmount);
                        player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem(2, player);
                        player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).damageItem(2, player);
                        player.getItemStackFromSlot(EntityEquipmentSlot.FEET).damageItem(2, player);
                        return;
                }
                }
            }
        float damage = event.getAmount();
            event.setAmount(damage);
    }



}
