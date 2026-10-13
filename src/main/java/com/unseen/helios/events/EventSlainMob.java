package com.unseen.helios.events;

import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.common.entity.entities.manus_avatiria.EntityManusAvaritia;
import com.unseen.helios.common.entity.projectile.ProjectileCyclerSoul;
import com.unseen.helios.common.items.ItemBase;
import com.unseen.helios.common.items.armor.ArmorManus;
import com.unseen.helios.common.items.armor.ArmorManusHelmet;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.ModUtils;
import com.unseen.helios.util.PlayerGreedUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class EventSlainMob {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();
        List<EntityManusAvaritia> nearbyManus = target.world.getEntitiesWithinAABB(EntityManusAvaritia.class, target.getEntityBoundingBox().grow(32.0D), e -> !e.getIsInvulnerable());
        List<EntityApostleHand> nearbyApostle = target.world.getEntitiesWithinAABB(EntityApostleHand.class, target.getEntityBoundingBox().grow(32.0D), e -> !e.getIsInvulnerable());
        boolean hasFoundCycler = false;
        if(!nearbyManus.isEmpty()) {
                BlockPos setTooPos = ModUtils.searchForBlocks(target.getEntityBoundingBox().grow(32), target.world, target, ModBlocks.REMNANT_RECYCLER.getDefaultState());

                if (setTooPos != null && !hasFoundCycler) {
                    ProjectileCyclerSoul soul = new ProjectileCyclerSoul(target.world, target, 0, setTooPos);
                    soul.setPosition(target.posX + 0.5, target.posY + 1.25, target.posZ + 0.5);
                    soul.setTravelRange(30);
                    target.world.spawnEntity(soul);
                    target.world.playSound((EntityPlayer) null, target.posX, target.posY, target.posZ, ModSoundHandler.SOUL_SHOOT, SoundCategory.NEUTRAL, 0.75f, 0.7f / (target.world.rand.nextFloat() * 0.4F + 0.2f));
                    hasFoundCycler = true;
                }
            } else if(!nearbyApostle.isEmpty()) {
            BlockPos setTooPos = ModUtils.searchForBlocks(target.getEntityBoundingBox().grow(32), target.world, target, ModBlocks.REMNANT_RECYCLER.getDefaultState());

            if (setTooPos != null && !hasFoundCycler) {
                ProjectileCyclerSoul soul = new ProjectileCyclerSoul(target.world, target, 0, setTooPos);
                soul.setPosition(target.posX + 0.5, target.posY + 1.25, target.posZ + 0.5);
                soul.setTravelRange(30);
                target.world.spawnEntity(soul);
                target.world.playSound((EntityPlayer) null, target.posX, target.posY, target.posZ, ModSoundHandler.SOUL_SHOOT, SoundCategory.NEUTRAL, 0.75f, 0.7f / (target.world.rand.nextFloat() * 0.4F + 0.2f));
                hasFoundCycler = true;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();
        if(target instanceof EntityPlayer) {
            if(GeneralConfig.greed_deletes_items) {
                EntityPlayer player = ((EntityPlayer) target);
                if (PlayerGreedUtils.getPlayerGreedProgress(player) > GeneralConfig.mod_greed_cap * 0.75) {

                    if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.MANUS_BOOTS) {
                        player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.AIR));
                    }

                    if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.MANUS_CHESTPLATE) {
                        player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.AIR));
                    }

                    if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.MANUS_HELMET) {
                        player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.AIR));
                    }

                    if(player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == ModItems.MANUS_SHIELD) {
                        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
                    }
                    for (ItemStack item : player.inventory.mainInventory) {
                        if(item.getItem() instanceof ItemBase && !(item.getItem() == ModItems.MANUS_GOOP) || item.getItem() instanceof ArmorManus || item.getItem() instanceof ArmorManusHelmet) {
                            if(!target.world.isRemote) {
                                int maxD = item.getMaxDamage();
                                item.damageItem(maxD, player);
                                item.setCount(0);
                            }
                        }
                    }
                }
            }
        }
    }
}
