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


    @Config.Name("Hand of Greed Iron Tier")
    @Config.Comment("Change what block must be placed near the Manus Avaritia for Iron Tier trades. Works with 'modId:blockName'.")
    @Config.RequiresMcRestart
    public static String iron_tier_block = "minecraft:iron_block";

    @Config.Name("Hand of Greed Gold Tier")
    @Config.Comment("Change what block must be placed near the Manus Avaritia for Gold Tier trades. Works with 'modId:blockName'.")
    @Config.RequiresMcRestart
    public static String gold_tier_block = "minecraft:gold_block";

    @Config.Name("Hand of Greed Diamond Tier")
    @Config.Comment("Change what block must be placed near the Manus Avaritia for Diamond Tier trades. Works with 'modId:blockName'.")
    @Config.RequiresMcRestart
    public static String diamond_tier_block = "minecraft:diamond_block";

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Manus Goop. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] manus_goop_trade_items = new String[] {
            "minecraft:iron_ingot"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Manus Stick. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] manus_stick_trade_items = new String[] {
            "minecraft:coal"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Ferment Stonebricks. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] ferment_stonebricks_trade_items = new String[] {
            "minecraft:leather",
            "minecraft:flint"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Great Finger. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] great_finger_trade_items = new String[] {
            "minecraft:gold_ingot"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Grafted Core. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] grafted_core_trade_items = new String[] {
            "minecraft:blaze_rod"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Trinket of Destiny. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] champion_locator_trade_items = new String[] {
            "minecraft:ender_pearl"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Hemoglehpin. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] hemoglephin_trade_items = new String[] {
            "minecraft:diamond"
    };

    @Config.RequiresMcRestart
    @Config.Comment(value = "What Items can be traded for Depths Brain. Works with 'modID:itemName'. Must be an item, and not a block.")
    public static String[] depths_brain_trade_items = new String[] {
            "minecraft:emerald"
    };
}
