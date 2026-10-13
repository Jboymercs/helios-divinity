package com.unseen.helios.config;

import com.unseen.helios.util.ModReference;
import net.minecraftforge.common.config.Config;

@Config(modid = ModReference.MOD_ID, name = "Helios Divinity/general_config")
public class GeneralConfig {

    @Config.Name("Player Greed Cap")
    @Config.Comment("Change the Greed Cap before all negative effects are applied to the player. The greed cap gets added when the Greedy Potion is active")
    @Config.RequiresMcRestart
    public static int mod_greed_cap = 600;

    @Config.Name("High Greed Delete Items")
    @Config.Comment("Does having above 75% of the Greed Cap delete most items related to the mod upon death. This is for balancing purposes.")
    @Config.RequiresMcRestart
    public static boolean greed_deletes_items = true;

    @Config.Name("Manus Goop Block Spread Chance")
    @Config.Comment("When the random chance of 0-1 goes below this value. The Manus Goop Block will try to spread itself to nearby blocks that are not Manus Blocks")
    @Config.RequiresMcRestart
    public static double manus_spread_chance = 0.06;

    @Config.Name("Boss Music Volume")
    @Config.Comment("Change the volume of boss music used in the mod")
    @Config.RequiresMcRestart
    public static double boss_music_volume = 1;

    @Config.Name("Finger Shield Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Finger Shield")
    @Config.RequiresMcRestart
    public static int finger_shield_cooldown = 20;

    @Config.Name("Finger Shield Damage")
    @Config.Comment("Change the damage of the Finger Shield's ability")
    @Config.RequiresMcRestart
    public static int finger_shield_damage = 7;

    @Config.Name("Champion's Spear Dash Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Champion's Spear Dash ability")
    @Config.RequiresMcRestart
    public static int champion_spear_dash_cooldown = 15;

    @Config.Name("Champion's Spear Dash Damage Bonus")
    @Config.Comment("Change the Champion's Spear bonus damage when using the ability. It is base weapon damage + bonus damage")
    @Config.RequiresMcRestart
    public static int champion_spear_bonus_damage = 9;

    @Config.Name("Champion's Spear AOE Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Champion's Spear AOE ability")
    @Config.RequiresMcRestart
    public static int champion_spear_aoe_cooldown = 30;

    @Config.Name("Champion's Spear Base Damage")
    @Config.Comment("Change the base damage of the Champion's Spear")
    @Config.RequiresMcRestart
    public static double champion_spear_base_damage = 6;

    @Config.Name("Great Finger's Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Great Finger weapon ability")
    @Config.RequiresMcRestart
    public static int great_finger_cooldown = 20;

    @Config.Name("Great Finger's Damage Bonus")
    @Config.Comment("Change the damage bonus of using the Great Finger's weapon ability. It is base damage + bonus damage")
    @Config.RequiresMcRestart
    public static int great_finger_damage = 3;

    @Config.Name("Great Finger Base Damage")
    @Config.Comment("Change the base damage of the Great Finger")
    @Config.RequiresMcRestart
    public static double great_finger_base_damage = 8.5;

    @Config.Name("Manus Goop Covered Sword Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Manus Goop Covered Sword's ability")
    @Config.RequiresMcRestart
    public static int manus_sword_cooldown = 60;

    @Config.Name("Manus Goop Covered Sword Damage Cap")
    @Config.Comment("When using the Manus Goop Covered Sword's ability, change the damage cap for it")
    @Config.RequiresMcRestart
    public static int manus_sword_damage_cap = 15;

    @Config.Name("Manus Goop Covered Sword Base Damage")
    @Config.Comment("Change the base damage of the Manus Goop Covered Sword")
    @Config.RequiresMcRestart
    public static double manus_sword_base_damage = 5.5;

    @Config.Name("Scroll of Living Greed Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Scroll of living Greed")
    @Config.RequiresMcRestart
    public static int greed_scroll_cooldown = 5;

    @Config.Name("Scroll of Living Greed Damage Cap")
    @Config.Comment("Change the damage cap of the Scroll of Living Greed")
    @Config.RequiresMcRestart
    public static int greed_scroll_damage_cap = 25;

    @Config.Name("Trinket of the Hand Cooldown")
    @Config.Comment("Change the cooldown in seconds of the Trinket of the Hand")
    @Config.RequiresMcRestart
    public static int hand_trinket_cooldown = 45;

    @Config.Name("Trinket of the Hand Damage Negative")
    @Config.Comment("For Balancing purposes, you can have it to where when x damage is done by this trinket. It will not excee this cap value. 0 sets it to no cap")
    @Config.RequiresMcRestart
    public static int hand_trinket_cap = 0;

    @Config.Name("Manus Helmet Face Overlay")
    @Config.Comment("Should the Manus Helmet show a face overlay when equipped. This is the version where it overrides the players face.")
    @Config.RequiresMcRestart
    public static boolean manus_helmet_face_overlay = false;
}
