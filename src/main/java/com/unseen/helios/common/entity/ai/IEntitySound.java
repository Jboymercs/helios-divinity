package com.unseen.helios.common.entity.ai;

import com.unseen.helios.Main;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public interface IEntitySound {
    @Nullable
    public SoundEvent getBossMusic();

    default void playMusic(Entity entity) {
        if (getBossMusic() != null)
            Main.proxy.playMusic(getBossMusic(), entity);
    }

    default void stopMusic() {
        if(getBossMusic() != null) {
            Main.proxy.stopMusic();
        }
    }
}
