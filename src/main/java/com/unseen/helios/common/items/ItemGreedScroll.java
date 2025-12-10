package com.unseen.helios.common.items;

import com.unseen.helios.Main;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis;

import java.util.List;

public class ItemGreedScroll extends ItemBase{

    String info_loc;
    public ItemGreedScroll(String name, CreativeTabs tab, String info_loc) {
        super(name, tab);
        this.setMaxStackSize(4);
        this.info_loc = info_loc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!player.getCooldownTracker().hasCooldown(this)) {
            List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5), e -> e != player);
            if(!targets.isEmpty()) {
                for(EntityLivingBase base: targets) {
                    double healthDamage = base.getMaxHealth() * 0.50;
                    if(healthDamage > GeneralConfig.greed_scroll_damage_cap) {
                        healthDamage = GeneralConfig.greed_scroll_damage_cap;
                    }
                    base.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player).setDamageBypassesArmor(), (float) healthDamage);
                }
            }
            player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.UTIL_MANUS_SCROLL_CAST, SoundCategory.NEUTRAL, 0.75f, 0.7f / (player.world.rand.nextFloat() * 0.4F + 0.2f));
            //particles
            if(worldIn.isRemote)  {
                Main.proxy.spawnParticle(4, player.posX, player.posY + 3.5, player.posZ, 0, 0, 0, 0);
                ModUtils.circleCallback(6, 12, (pos)-> {
                    pos = new Vec3d(pos.x, 0, pos.y);
                    Main.proxy.spawnParticle(1, player.posX + pos.x, player.posY + 1, player.posZ + pos.z, 0,0.1,0, 9320921);
                });

                ModUtils.circleCallback(6, 13, (pos)-> {
                    pos = new Vec3d(pos.x, 0, pos.y);
                    Main.proxy.spawnParticle(1, player.posX + pos.x, player.posY + 2, player.posZ + pos.z, 0,0.1,0, 9320921);
                });
            }
            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 5);
            if(!worldIn.isRemote) {
                stack.shrink(1);
                player.getCooldownTracker().setCooldown(this, GeneralConfig.greed_scroll_cooldown * 20);
            }
            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 3, false, false));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
