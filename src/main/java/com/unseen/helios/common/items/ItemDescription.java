package com.unseen.helios.common.items;

import com.unseen.helios.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDescription extends ItemBase{

    String info_loc;

    public ItemDescription(String name, CreativeTabs tab, String info_loc, int durability) {
        super(name, tab);
        this.info_loc = info_loc;
        this.maxStackSize = 1;
        this.setMaxDamage(durability);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }
}
