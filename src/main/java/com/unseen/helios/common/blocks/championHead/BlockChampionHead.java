package com.unseen.helios.common.blocks.championHead;

import com.google.common.base.Predicate;
import com.unseen.helios.Main;
import com.unseen.helios.common.entity.tileEntities.TileEntityChampionHead;
import com.unseen.helios.common.items.championHead.ItemChampionHead;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModCreativeTab;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.IHasModel;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockChampionHead extends BlockSkull implements IHasModel {

    private String info_loc;
    private static final AxisAlignedBB[] AABBS = {new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.5D, 0.8125D),
            new AxisAlignedBB(0.1875D, 0.25D, 0.5D, 0.8125D, 0.75D, 1.0D),
            new AxisAlignedBB(0.1875D, 0.25D, 0.0D, 0.8125D, 0.75D, 0.5D),
            new AxisAlignedBB(0.5D, 0.25D, 0.1875D, 1.0D, 0.75D, 0.8125D),
            new AxisAlignedBB(0.0D, 0.25D, 0.1875D, 0.5D, 0.75D, 0.8125D)};

    public BlockChampionHead(String name, String info_loc)
    {
        setHardness(1.0F);
        setSoundType(SoundType.STONE);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(ModCreativeTab.ITEMS);
        setDefaultState(blockState.getBaseState().withProperty(BlockSkull.NODROP, false).withProperty(BlockSkull.FACING, EnumFacing.UP));
        ModBlocks.BLOCKS.add(this);
        this.info_loc = info_loc;
        ModItems.ITEMS.add(new ItemChampionHead(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityChampionHead) return new ItemStack(ModBlocks.CHAMPION_HEAD, 1, ((TileEntityChampionHead)tileentity).getSkullType());

        return new ItemStack(ModBlocks.CHAMPION_HEAD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    { return AABBS[Math.max(0, state.getValue(FACING).getIndex() - 1)]; }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    { return new TileEntityChampionHead(); }

    @Override
    public boolean canDispenserPlace(World world, BlockPos pos, ItemStack stack)
    { return false; }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess worldIn, BlockPos pos, IBlockState state, int fortune)
    {
        if (!(Boolean) state.getValue(NODROP))
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityChampionHead)
            {
                TileEntityChampionHead tileentityskull = (TileEntityChampionHead) tileentity;
                ItemStack itemstack = new ItemStack(ModBlocks.CHAMPION_HEAD, 1, tileentityskull.getSkullType());
                drops.add(itemstack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        if (rand.nextInt(80) == 0) {
            Vec3d particlePos = new Vec3d(pos.getX() + 0.5 + ModRand.getFloat(1), pos.getY() + 0.5 + ModRand.getFloat(0.3F), pos.getZ() + 0.5 + ModRand.getFloat(1));
            Main.proxy.spawnParticle(1, particlePos.x, particlePos.y, particlePos.z, worldIn.rand.nextFloat()/8 - worldIn.rand.nextFloat()/8, 0.06, worldIn.rand.nextFloat()/8 - worldIn.rand.nextFloat()/8, 16749027);
        }
    }

    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
