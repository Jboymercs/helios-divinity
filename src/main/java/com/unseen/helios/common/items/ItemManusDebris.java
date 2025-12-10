package com.unseen.helios.common.items;

import com.unseen.helios.common.blocks.BlockManusDebris;
import com.unseen.helios.init.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

public class ItemManusDebris extends ItemBase{


    public ItemManusDebris(String name, CreativeTabs tab) {
        super(name, tab);
    }

    //I have looked into the deeper depths and sculk veins have stared back
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
   //     ItemStack stack = player.getHeldItem(hand);
   //     if (player.isSneaking() |! player.canPlayerEdit(pos, side, stack) || stack.getItem() != this)  return EnumActionResult.PASS;
   //     pos = pos.offset(side);
   //     IBlockState state = world.getBlockState(pos);
   //     EnumFacing facing = side.getOpposite();
   //     if (state.getBlock() instanceof BlockManusDebris)
   //     {
    //        if (BlockManusDebris.hasFacing(state, facing)) return EnumActionResult.PASS;
            /* Added a bonus check, so facing only gets updated if the face can allow the Resin to be placed against it. */
    //        if (ModBlocks.MANUS_DEBRIS[0].canPlaceBlockOnSide(world, pos, side)) state = BlockManusDebris.getBlockState(ArrayUtils.add(BlockManusDebris.getFacings(state), facing));
     //   }
     //   else if (!world.mayPlace(ModBlocks.MANUS_DEBRIS[0], pos, false, side, player)) return EnumActionResult.PASS;
    //    else state = BlockManusDebris.getBlockState(facing);
    //    if (!world.setBlockState(pos, state, 11)) return EnumActionResult.PASS;

        SoundType sound = SoundType.SLIME;
        world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1) / 2f, sound.getPitch() * 0.8f);
   //     stack.shrink(1);

        return EnumActionResult.SUCCESS;
    }
}
