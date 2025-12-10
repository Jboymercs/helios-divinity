package com.unseen.helios.config;

import com.unseen.helios.util.ModReference;
import net.minecraftforge.common.config.Config;

@Config(modid = ModReference.MOD_ID, name = "Helios Divinity/world_config")
public class WorldConfig {

    @Config.Name("Helios Divinity Blacklisted Biome Types")
    @Config.Comment("Add Biome types that DISALLOW the Helios Divinity structures from spawning in that biome, examples of tags are HOT, COLD, SPOOKY")
    @Config.RequiresMcRestart
    public static String[] biome_types_blacklist_manus = {"DENSE","NETHER","END","MUSHROOM","OCEAN","RIVER","MESA","BEACH"
    };

    @Config.Name("Manus Altar Spacing")
    @Config.Comment("Change the spacing of the Manus Altar structure")
    @Config.RequiresMcRestart
    public static int manus_altar_spacing = 65;

    @Config.Name("Champion's Grave Spacing")
    @Config.Comment("Change the spacing of the Champion's Grave structure")
    @Config.RequiresMcRestart
    public static int champion_grave_spacing = 95;

}
