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

    @Config.Name("Helios Divinity Structures Allowed Dimensions")
    @Config.Comment("Helios Divinity Structures are allowed to spawn in x dimensions. ID numbers only!")
    @Config.RequiresMcRestart
    public static int[] list_of_dimensions = {0};

    @Config.Name("Manus Altar Spacing")
    @Config.Comment("Change the spacing of the Manus Altar structure")
    @Config.RequiresMcRestart
    public static int manus_altar_spacing = 65;

    @Config.Name("Champion's Grave Spacing")
    @Config.Comment("Change the spacing of the Champion's Grave structure")
    @Config.RequiresMcRestart
    public static int champion_grave_spacing = 95;

    @Config.Name("Champion's Grave Min Y height")
    @Config.Comment("Change the minimum Y Height of the Champion's Grave structure")
    @Config.RequiresMcRestart
    public static int champion_grave_min_y = 40;

    @Config.Name("Champion's Grave Max Y height")
    @Config.Comment("Change the maximum Y Height of the Champion's Grave structure")
    @Config.RequiresMcRestart
    public static int champion_grave_max_y = 200;

    @Config.Name("Champion's Grave Static Y Backup")
    @Config.Comment("Change the static Y at which the Champion's Grave is at. The structure will also change to look more floating like")
    @Config.RequiresMcRestart
    public static int champion_grave_static_y = 120;

    @Config.Name("Manus Altar Min Y height")
    @Config.Comment("Change the minimum Y height of the Manus Altar structure")
    @Config.RequiresMcRestart
    public static int manus_altar_min_y= 40;

    @Config.Name("Manus Altar Max Y height")
    @Config.Comment("Change the maximum Y height of the Manus Altar structure")
    @Config.RequiresMcRestart
    public static int manus_altar_max_y= 200;

    @Config.Name("Manus Altar Static Y Backup")
    @Config.Comment("Change the static Y at which the Manus Altar is at. The structure will also change to look more floating like")
    @Config.RequiresMcRestart
    public static int manus_altar_static_y= 95;

    @Config.Name("Static Y Backup Structures Enabled/Disabled")
    @Config.Comment("Change if the y height can't be found, the structure will spawn in the air with it looking more floating like")
    @Config.RequiresMcRestart
    public static boolean backup_structures_enabled = true;

    @Config.Name("Use Flying Structures Instead Enabled/Disabled")
    @Config.Comment("Change if the mod should use its flying structure variants instead. Make sure you have backup structures enabled")
    @Config.RequiresMcRestart
    public static boolean use_flying_structures = false;
}
