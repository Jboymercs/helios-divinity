package com.unseen.helios.common.items;

import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.util.ModUtils;
import com.unseen.helios.util.PlayerGreedUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis;

import java.util.List;

public class ItemManusCompass extends ItemBase{

    String info_loc;

    public ItemManusCompass(String name, CreativeTabs tab, String info_loc) {
        super(name, tab);
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.info_loc = info_loc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!player.getCooldownTracker().hasCooldown(this) && !worldIn.isRemote) {
            int greedCounter = GeneralConfig.mod_greed_cap;

            if(PlayerGreedUtils.getPlayerGreedProgress(player) < (greedCounter * 0.25)) {
                player.sendStatusMessage(new TextComponentTranslation("helios.low_greed", new Object[0]), true);
            } else if (PlayerGreedUtils.getPlayerGreedProgress(player) < (greedCounter * 0.5)) {
                player.sendStatusMessage(new TextComponentTranslation("helios.mid_greed", new Object[0]), true);
            } else if (PlayerGreedUtils.getPlayerGreedProgress(player) < (greedCounter * 0.75)) {
                player.sendStatusMessage(new TextComponentTranslation("helios.high_greed", new Object[0]), true);
            } else if (PlayerGreedUtils.getPlayerGreedProgress(player) < (greedCounter)) {
                player.sendStatusMessage(new TextComponentTranslation("helios.very_high_greed", new Object[0]), true);
            }
            stack.damageItem(1, player);
            player.getCooldownTracker().setCooldown(this, 2 * 20);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }


}
