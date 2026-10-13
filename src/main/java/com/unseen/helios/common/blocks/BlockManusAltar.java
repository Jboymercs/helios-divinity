package com.unseen.helios.common.blocks;

import com.unseen.helios.common.entity.entities.manus_avatiria.EntityManusAvaritia;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockManusAltar extends BlockBase{


    public BlockManusAltar(String name, Material material) {
        super(name, material);
        this.setBlockUnbreakable();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 2, pos.getY() + 1, pos.getZ() + 1);
        List<EntityManusAvaritia> nearbyHand = worldIn.getEntitiesWithinAABB(EntityManusAvaritia.class, box.grow(1), e -> !e.getIsInvulnerable());
        if(nearbyHand.isEmpty()) {
            EntityManusAvaritia hand = new EntityManusAvaritia(worldIn);
            hand.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            worldIn.spawnEntity(hand);
        }
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 2, pos.getY() + 1, pos.getZ() + 1);
        List<EntityManusAvaritia> nearbyHand = worldIn.getEntitiesWithinAABB(EntityManusAvaritia.class, box.grow(1), e -> !e.getIsInvulnerable());
        if(!nearbyHand.isEmpty()) {
            for(EntityManusAvaritia hand : nearbyHand) {
                hand.setDead();
            }
        }
    }
}
