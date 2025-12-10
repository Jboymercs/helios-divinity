package com.unseen.helios.config;

import com.unseen.helios.util.ModReference;
import net.minecraftforge.common.config.Config;

@Config(modid = ModReference.MOD_ID, name = "Helios Divinity/mob_config")
public class MobConfig {

    @Config.Name("Nameless Champion Health")
    @Config.Comment("Change the Health of the Nameless Champion")
    @Config.RequiresMcRestart
    public static double nameless_champion_health = 400;

    @Config.Name("Nameless Champion Armor")
    @Config.Comment("Change the Armor of the Nameless Champion")
    @Config.RequiresMcRestart
    public static double nameless_champion_armor = 16;

    @Config.Name("Nameless Champion Armor Toughness")
    @Config.Comment("Change the Armor Toughness of the Nameless Champion")
    @Config.RequiresMcRestart
    public static double nameless_champion_armor_toughness = 6;

    @Config.Name("Nameless Champion Attack Damage")
    @Config.Comment("Change the Attack Damage of the Nameless Champion")
    @Config.RequiresMcRestart
    public static double nameless_champion_attack_damage = 22;

    @Config.Name("Nameless Champion Cooldown First Phase")
    @Config.Comment("Change the cooldown when the Nameless Champion is above half health. In ticks, 20 ticks equals 1 second.")
    @Config.RequiresMcRestart
    public static int nameless_champion_cooldown_one = 20;

    @Config.Name("Nameless Champion Cooldown Second Phase")
    @Config.Comment("Change the cooldown when the Nameless Champion is below half health. In ticks, 20 ticks equals 1 second.")
    @Config.RequiresMcRestart
    public static int nameless_champion_cooldown_two = 10;

    @Config.Name("Nameless Champion Reset Time")
    @Config.Comment("In seconds, How long does it take before the Nameless Champion resets itself after defeating all players nearby")
    @Config.RequiresMcRestart
    public static int nameless_champion_reset_timer = 25;

    @Config.Name("Apostle Hand Health")
    @Config.Comment("Change the Health of the Apostle Hand")
    @Config.RequiresMcRestart
    public static double apostle_hand_health = 130;

    @Config.Name("Apostle Hand Attack Damage")
    @Config.Comment("Change the Attack Damage of the Apostle Hand")
    @Config.RequiresMcRestart
    public static double apostle_hand_attack_damage = 20;

    @Config.Name("Hand of Greed Health")
    @Config.Comment("Change the Health of the Hand of Greed")
    @Config.RequiresMcRestart
    public static double hand_of_greed_health = 15;
}
