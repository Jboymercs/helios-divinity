package com.unseen.helios.common.blocks;

import com.unseen.helios.util.ModUtils;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class BlockDescription extends BlockBase{

    private String info_loc;

    public BlockDescription(String name, Material material, String info_loc) {
        super(name, material);
        this.info_loc = info_loc;
    }

    public BlockDescription(String name, Material material, float hardness, float resistance, SoundType soundType, String info_loc) {
        super(name, material, hardness, resistance, soundType);
        this.info_loc = info_loc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }
}
