package com.unseen.helios.capability;

import com.unseen.helios.util.ModReference;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityPlayerGreed {
    @CapabilityInject(IPlayerGreedCapability.class)
    public static Capability<IPlayerGreedCapability> PLAYER_GREED_CAP;
    public static final ResourceLocation ID = new ResourceLocation(ModReference.MOD_ID, "heliosPlayerGreed");

    private static final String PLAYER_GREED = "heliosPlayerGreedProgress";
    private static final String PLAYER_GREED_BOOL = "heliosPlayerGreedBoolean";

    public interface IPlayerGreedCapability
    {
        boolean isGreedEnabled();
        void setGreedEnabled(boolean val);
        int getPlayerGreedProgress();
        void setPlayerGreedProgress(int val);
    }

    public static class HeliosPlayerGreedMethods implements IPlayerGreedCapability {

        private int playerGreedCurrent;
        private boolean playerGreedEnabled;

        @Override
        public boolean isGreedEnabled() {
            return playerGreedEnabled;
        }

        @Override
        public void setGreedEnabled(boolean val) {
            playerGreedEnabled = val;
        }

        @Override
        public int getPlayerGreedProgress() {
            return playerGreedCurrent;
        }

        @Override
        public void setPlayerGreedProgress(int val) {
            this.playerGreedCurrent = val;
        }
    }

    public static class Storage implements Capability.IStorage<IPlayerGreedCapability>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerGreedCapability> capability, IPlayerGreedCapability instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger(PLAYER_GREED, instance.getPlayerGreedProgress());
            compound.setBoolean(PLAYER_GREED_BOOL, instance.isGreedEnabled());
            return compound;
        }

        @Override
        public void readNBT(Capability<IPlayerGreedCapability> capability, IPlayerGreedCapability instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setPlayerGreedProgress(compound.getInteger(PLAYER_GREED));
            instance.setGreedEnabled(compound.getBoolean(PLAYER_GREED_BOOL));
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTBase>
    {
        final Capability<IPlayerGreedCapability> capability;
        final EnumFacing facing;
        final IPlayerGreedCapability instance;

        public Provider(final IPlayerGreedCapability instance, final Capability<IPlayerGreedCapability> capability, @Nullable final EnumFacing facing)
        {
            this.instance = instance;
            this.capability = capability;
            this.facing = facing;
        }

        @Override
        public boolean hasCapability(@Nullable final Capability<?> capability, final EnumFacing facing)
        { return capability == getCapability(); }

        @Override
        public <T> T getCapability(@Nullable Capability<T> capability, EnumFacing facing)
        { return capability == getCapability() ? getCapability().cast(this.instance) : null; }

        final Capability<IPlayerGreedCapability> getCapability()
        { return capability; }

        EnumFacing getFacing()
        { return facing; }

        final IPlayerGreedCapability getInstance()
        { return instance; }

        @Override
        public NBTBase serializeNBT()
        { return getCapability().writeNBT(getInstance(), getFacing()); }

        @Override
        public void deserializeNBT(NBTBase nbt)
        { getCapability().readNBT(getInstance(), getFacing(), nbt); }
    }
}
