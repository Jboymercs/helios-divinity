package com.unseen.helios.common.blocks;

import com.unseen.helios.Main;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.ModRand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockManusGoop extends BlockBase{
    public BlockManusGoop(String name, Material material, float hardness, float resistance, SoundType soundType) {
        super(name, material, hardness, resistance, soundType);
        this.fullBlock = true;
        this.needsRandomTick = true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        return BlockFaceShape.SOLID;
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
            drops.add(new ItemStack(ModItems.MANUS_GOOP, ModRand.range(1, 2)));
    }

    /**
     * Randomly places Manus Goop blocks with a rare chance on blocks with a low Hardness
     * @param world
     * @param pos
     * @param state
     * @param random
     */

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.isRemote) return;
        if (world.rand.nextFloat() <= (float) GeneralConfig.manus_spread_chance) {
            EnumFacing facing = EnumFacing.values()[random.nextInt(6)];
            BlockPos pos1 = pos.offset(facing);
            IBlockState state1 = world.getBlockState(pos1);
            if (state1.getBlockHardness(world, pos1) < 1.5 && state1.isFullBlock() && state1.getBlock() != this &&
                    !(state1 instanceof ModBlocks)) {
                world.setBlockState(pos1, ModBlocks.MANUS_BLOCk.getDefaultState());
            }
        }

        if(world.rand.nextFloat() <= (float) GeneralConfig.manus_spread_chance * 0.8 && world.rand.nextInt(9) == 0) {
            BlockPos pos1 = pos.up();
            if(world.isAirBlock(pos1)) {
                if(world.rand.nextInt(4) == 0) {
                    world.setBlockState(pos1, ModBlocks.MANUS_VINES.getDefaultState());
                } else {
                    world.setBlockState(pos1, ModBlocks.MANUS_GRASS.getDefaultState());
                }
            }
        }
        return;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        if (worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState() && rand.nextInt(95) == 0) {
                Vec3d particlePos = new Vec3d(pos.getX() + 0.5 + ModRand.getFloat(1), pos.getY() + 1.1 + ModRand.range(0, 4), pos.getZ() + 0.5 + ModRand.getFloat(1));
                Main.proxy.spawnParticle(1, particlePos.x, particlePos.y, particlePos.z, worldIn.rand.nextFloat()/8 - worldIn.rand.nextFloat()/8, 0.06, worldIn.rand.nextFloat()/8 - worldIn.rand.nextFloat()/8, 9331650);
        }
    }


}
