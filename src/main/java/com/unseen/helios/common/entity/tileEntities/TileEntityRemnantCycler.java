package com.unseen.helios.common.entity.tileEntities;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.util.ModUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityRemnantCycler extends TileEntity implements ITickable {

    public int timesUsed;

    @Override
    public void update() {

        if(!world.isRemote) {
            AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            List<EntityHeliosMobBase> nearbyManus = this.world.getEntitiesWithinAABB(EntityHeliosMobBase.class, box.grow(16D), e -> !e.getIsInvulnerable());

            if(!nearbyManus.isEmpty()) {
                for(EntityHeliosMobBase hand : nearbyManus) {

                    if(hand instanceof EntityApostleHand) {
                        double handHealth = hand.getHealth() / (hand.getMaxHealth() - 4);

                        //heals the hand
                        if(handHealth < 1 && timesUsed > 0 && !world.isDaytime() && hand.ticksExisted % (20 * 20) == 0) {
                            Vec3d currPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5);
                            Vec3d blossomPos = hand.getPositionVector().add(0, 0.4, 0);
                            hand.heal(5);
                            world.playSound((EntityPlayer) null, pos.getX(), pos.getY(), pos.getZ(), ModSoundHandler.SOUL_TRANSFER, SoundCategory.NEUTRAL, 0.75f, 0.7f / (world.rand.nextFloat() * 0.4F + 0.2f));
                            timesUsed--;
                            ModUtils.lineCallback(currPos, blossomPos,(int) (hand.getDistance(currPos.x, currPos.y, currPos.z) * 1.5), (particlePos, j) -> {
                                hand.addEvent(()-> {
                                    Main.proxy.spawnParticle(1, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0, 14614527);
                                }, j * 3);
                            });
                        }
                    } else if (hand instanceof EntityManusAvaritia) {
                        double handHealth = hand.getHealth() / hand.getMaxHealth();

                        //heals the hand
                        if(handHealth < 1 && timesUsed > 0 && !world.isDaytime() && hand.ticksExisted % (10 * 20) == 0) {
                            Vec3d currPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5);
                            Vec3d blossomPos = hand.getPositionVector().add(0, 0.4, 0);
                            hand.heal(1);
                            world.playSound((EntityPlayer) null, pos.getX(), pos.getY(), pos.getZ(), ModSoundHandler.SOUL_TRANSFER, SoundCategory.NEUTRAL, 0.75f, 0.7f / (world.rand.nextFloat() * 0.4F + 0.2f));
                            timesUsed--;
                            ModUtils.lineCallback(currPos, blossomPos,(int) (hand.getDistance(currPos.x, currPos.y, currPos.z) * 1.5), (particlePos, j) -> {
                                hand.addEvent(()-> {
                                    Main.proxy.spawnParticle(1, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0, 14614527);
                                }, j * 3);
                            });
                        }
                    }
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("times_used", this.timesUsed);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.timesUsed = compound.getInteger("times_used");
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    @Override
    public void markDirty() {
        IBlockState state = world.getBlockState(pos);
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, state, state, 3);
        world.scheduleBlockUpdate(pos, getBlockType(), 0, 0);
        super.markDirty();
    }
}
