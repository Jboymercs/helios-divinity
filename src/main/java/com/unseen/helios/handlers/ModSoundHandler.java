package com.unseen.helios.handlers;

import com.unseen.helios.util.ModReference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSoundHandler {


    //APOSTLE
    public static SoundEvent APOSTLE_HURT;
    public static SoundEvent APOSTLE_IDLE;
    public static SoundEvent APOSTLE_CAST;
    public static SoundEvent APOSTLE_SLAM;
    public static SoundEvent APOSTLE_CAST_IMPACT;
    public static SoundEvent APOSTLE_SUMMON;

    //HAND
    public static SoundEvent HAND_AWAKE;
    public static SoundEvent HAND_SLEEP;
    public static SoundEvent HAND_TRADE;
    public static SoundEvent HAND_TRADE_DONE;
    public static SoundEvent HAND_TRANSFORM;
    //SOUL
    public static SoundEvent SOUL_SHOOT;
    public static SoundEvent SOUL_TRANSFER;

    //Champion
    public static SoundEvent CHAMPION_HURT;
    public static SoundEvent CHAMPION_ARMOR;
    public static SoundEvent CHAMPION_STEP;
    public static SoundEvent CHAMPION_SWING;
    public static SoundEvent CHAMPION_TELEPORT;
    public static SoundEvent CHAMPION_DASH;
    public static SoundEvent CHAMPION_SUCK;
    public static SoundEvent CHAMPION_CAST_PROJECTILE;
    public static SoundEvent CHAMPION_CAST_WAVE;
    public static SoundEvent CHAMPION_STOMP;
    public static SoundEvent CHAMPION_AOE_PLACE;
    public static SoundEvent CHAMPION_SUMMON_AOE;
    public static SoundEvent CHAMPION_AOE_IMPACT;
    public static SoundEvent CHAMPION_SUMMON;


    //BLOCKS

    //Manus Goop
    public static SoundEvent MANUS_GOOP_BREAK;
    public static SoundEvent MANUS_GOOP_STEP;
    //MANUS GRASS
    public static SoundEvent MANUS_GRASS_BREAK;
    public static SoundEvent MANUS_GRASS_STEP;

    //WEAPONS
    public static SoundEvent UTIL_MANUS_SHIELD_BLOCK;
    public static SoundEvent UTIL_MANUS_SWORD_CAST;
    public static SoundEvent UTIL_MANUS_SCROLL_CAST;

    //Music
    public static SoundEvent CHAMPION_SOUND_TRACK;

    public static void registerSounds() {
        //MANUS GOOP
        MANUS_GOOP_BREAK = registerSound("goop.break","block");
        MANUS_GOOP_STEP = registerSound("goop.step", "block");
        //MANUS GRASS
        MANUS_GRASS_BREAK = registerSound("manus_grass.break","block");
        MANUS_GRASS_STEP = registerSound("manus_grass.step", "block");
        //UTIL
        UTIL_MANUS_SCROLL_CAST = registerSound("util.scroll_cast","entity");
        UTIL_MANUS_SWORD_CAST = registerSound("util.sword_cast", "entity");
        UTIL_MANUS_SHIELD_BLOCK = registerSound("util.block","entity");
        //APOSTLE
        APOSTLE_HURT = registerSound("apostle.hurt","entity");
        APOSTLE_IDLE = registerSound("apostle.idle","entity");
        APOSTLE_SLAM = registerSound("apostle.slam","entity");
        APOSTLE_CAST = registerSound("apostle.cast","entity");
        APOSTLE_CAST_IMPACT = registerSound("apostle.cast_impact","entity");
        APOSTLE_SUMMON = registerSound("apostle.summon", "entity");
        //HAND
        HAND_AWAKE = registerSound("hand.awaken", "entity");
        HAND_SLEEP = registerSound("hand.sleep","entity");
        HAND_TRADE = registerSound("hand.trade","entity");
        HAND_TRADE_DONE = registerSound("hand.trade_done","entity");
        HAND_TRANSFORM = registerSound("hand.transform","entity");
        //CHAMPION
        CHAMPION_ARMOR = registerSound("champion.movement","entity");
        CHAMPION_STEP = registerSound("champion.step","entity");
        CHAMPION_HURT = registerSound("champion.hurt","entity");
        CHAMPION_SUMMON_AOE = registerSound("champion.summon_aoe", "entity");
        CHAMPION_AOE_IMPACT = registerSound("champion.aoe_impact","entity");
        CHAMPION_TELEPORT = registerSound("champion.teleport", "entity");
        CHAMPION_SWING = registerSound("champion.swing","entity");
        CHAMPION_CAST_PROJECTILE = registerSound("champion.cast_projectile","entity");
        CHAMPION_CAST_WAVE =registerSound("champion.cast_wave","entity");
        CHAMPION_STOMP = registerSound("champion.stomp","entity");
        CHAMPION_AOE_PLACE = registerSound("champion.aoe_place","entity");
        CHAMPION_SUCK = registerSound("champion.suck", "entity");
        CHAMPION_DASH = registerSound("champion.dash","entity");
        CHAMPION_SUMMON = registerSound("champion.summon", "entity");
        //SOUL
        SOUL_SHOOT = registerSound("soul.shoot","entity");
        SOUL_TRANSFER = registerSound("soul.transfer","entity");
        //MUSIC
        CHAMPION_SOUND_TRACK = registerSound("ambient.champion", "music");
    }


    private static SoundEvent registerSound(String name, String category) {
        String fullName = category + "." + name;
        ResourceLocation location = new ResourceLocation(ModReference.MOD_ID, fullName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(fullName);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }
}
