package com.unseen.helios.common.items.championHead;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.tileEntities.TileEntityChampionHead;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.util.IHasModel;
import com.unseen.helios.util.ModReference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemChampionHead extends ItemBlock implements IHasModel {
    private static final String[] VARIANT_KEYS = new String[] { "piglin_head", "piglin_brute_head", "piglin_zombie_head" };

    public ItemChampionHead(Block block)
    {
        super(block);
        //this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing == EnumFacing.DOWN)
        { return EnumActionResult.FAIL; }
        else
        {
            if (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos))
            {
                facing = EnumFacing.UP;
                pos = pos.down();
            }

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(worldIn, pos);

            if (!flag)
            {
                if (!worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.isSideSolid(pos, facing, true))
                { return EnumActionResult.FAIL; }

                pos = pos.offset(facing);
            }

            ItemStack itemstack = playerIn.getHeldItem(hand);

            if (playerIn.canPlayerEdit(pos, facing, itemstack) && ModBlocks.CHAMPION_HEAD.canPlaceBlockAt(worldIn, pos))
            {
                SoundType soundtype = this.block.getSoundType(ModBlocks.CHAMPION_HEAD.getDefaultState(), worldIn, pos, playerIn);
                worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                worldIn.setBlockState(pos, ModBlocks.CHAMPION_HEAD.getDefaultState().withProperty(BlockSkull.FACING, facing), 11);
                int i = 0;

                if (facing == EnumFacing.UP)
                { i = MathHelper.floor((double) (playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15; }

                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChampionHead)
                {
                    TileEntityChampionHead tileentityskull = (TileEntityChampionHead) tileentity;

                    tileentityskull.setType(itemstack.getMetadata());
                    tileentityskull.setSkullRotation(i);
                }

                if (playerIn instanceof EntityPlayerMP)
                { CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) playerIn, pos, itemstack); }
                /* Runs any `onBlockPlacedBy` logic associated with the given block*/
                iblockstate.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate, playerIn, itemstack);

                itemstack.shrink(1);

                return EnumActionResult.SUCCESS;
            }
            else
            { return EnumActionResult.FAIL; }
        }
    }

    /** Override name for the other head variants! */
    public String getTranslationKey(ItemStack stack)
    { return "tile.champion_head"; }

    public int getMetadata(int damage)
    { return damage; }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity)
    { return false; }

    @Override
    @Nullable
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    { return null; }

    @SuppressWarnings("deprecated")
    public EnumRarity getRarity(ItemStack stack)
    { return EnumRarity.UNCOMMON; }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
