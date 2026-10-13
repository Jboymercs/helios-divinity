package com.unseen.helios.events;

import com.unseen.helios.common.items.shield.HeliosShieldItem;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.damageSource.ModIndirectDamage;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModReference;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public class EventShieldHandler {

    @SubscribeEvent
    public static void afterShieldAndBeforeArmor(LivingHurtEvent event) {
        if(event.getSource() instanceof ModIndirectDamage) {
            ModIndirectDamage damageSource = ((ModIndirectDamage)event.getSource());
            if(damageSource.getStoppedByArmor()) {
                damageSource.isUnblockable = false;
            }

            if(damageSource.getDisablesShields() && event.getEntityLiving() != null && ModUtils.canBlockDamageSource(damageSource, event.getEntityLiving()) && event.getEntityLiving() instanceof EntityPlayer) {
                ((EntityPlayer)event.getEntityLiving()).disableShield(true);
            }
        }

        //hand Trinket
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = ((EntityPlayer) event.getEntityLiving());
            ItemStack hand_trinket = ModUtils.findTrinket(new ItemStack(ModItems.GREED_TRINKET), player);
            if(!hand_trinket.isEmpty() && !player.getCooldownTracker().hasCooldown(hand_trinket.getItem())) {
                if(event.getSource().getImmediateSource() instanceof EntityLivingBase) {
                    EntityLivingBase base = ((EntityLivingBase) event.getSource().getImmediateSource());
                    if(base.isNonBoss() && !player.world.isRemote) {
                        int randI = ModRand.range(1, 100);
                        if(randI < 16) {
                            hand_trinket.damageItem(1, player);
                            player.getCooldownTracker().setCooldown(hand_trinket.getItem(), GeneralConfig.hand_trinket_cooldown * 20);
                            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 0, false, false));
                            double damage = player.getHealth() / 2;
                            if(GeneralConfig.hand_trinket_cap != 0) {
                                if(damage > GeneralConfig.hand_trinket_cap) {
                                    damage = GeneralConfig.hand_trinket_cap;
                                }
                            }
                            if(base instanceof EntityPlayer) {
                                base.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) damage);
                            } else {
                                base.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), (float) damage);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doShieldUpdates(LivingAttackEvent e) {
        HeliosShieldItem.handleDamageEvent(e);
    }

}
