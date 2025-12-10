package com.unseen.helios.common.items.tools;

import com.unseen.helios.Main;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ManusSword extends ToolSword {

    String info_loc;

    public ManusSword(String name, ToolMaterial material, CreativeTabs tab, String info_loc) {
        super(name, material, tab);
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
            List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(12), e -> e != player);
            if(!targets.isEmpty()) {
                for(EntityLivingBase base: targets) {
                    double healthDamage = base.getMaxHealth() * 0.25;
                    if(healthDamage > GeneralConfig.manus_sword_damage_cap) {
                        healthDamage = GeneralConfig.manus_sword_damage_cap;
                    }
                    base.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player).setDamageBypassesArmor(), (float) healthDamage);
                }
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 10);
            }
            //add to the Greed Counter!
            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 2, false, false));
            player.getCooldownTracker().setCooldown(this, GeneralConfig.manus_sword_cooldown * 20);
            stack.damageItem(5, player);
            player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.UTIL_MANUS_SWORD_CAST, SoundCategory.NEUTRAL, 0.75f, 0.7f / (player.world.rand.nextFloat() * 0.4F + 0.2f));
            if(worldIn.isRemote)  {
                Main.proxy.spawnParticle(4, player.posX, player.posY + 2.5, player.posZ, 0, 0, 0, 0);
                ModUtils.circleCallback(12, 12, (pos)-> {
                    pos = new Vec3d(pos.x, 0, pos.y);
                    Main.proxy.spawnParticle(1, player.posX + pos.x, player.posY + 1, player.posZ + pos.z, 0,0.1,0, 9320921);
                });

                ModUtils.circleCallback(12, 13, (pos)-> {
                    pos = new Vec3d(pos.x, 0, pos.y);
                    Main.proxy.spawnParticle(1, player.posX + pos.x, player.posY + 2, player.posZ + pos.z, 0,0.1,0, 9320921);
                });
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if((target.getCreatureAttribute()) != EnumCreatureAttribute.UNDEAD && target.world.rand.nextInt(2) == 0) {
            if(attacker instanceof EntityPlayer) {
                EntityPlayer player = ((EntityPlayer) attacker);
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
                player.addPotionEffect(new PotionEffect(ModPotions.GREED, 80, 0, false, false));
                stack.damageItem(1, player);
            }
        }
        return true;
    }

    @Override
    protected double getAttackSpeed() {
        return -2.3000000953674316D;
    }


}
