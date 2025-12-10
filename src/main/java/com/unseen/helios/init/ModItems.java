package com.unseen.helios.init;

import com.unseen.helios.common.items.*;
import com.unseen.helios.common.items.armor.ArmorManus;
import com.unseen.helios.common.items.armor.ArmorManusHelmet;
import com.unseen.helios.common.items.food.ItemStagaFruit;
import com.unseen.helios.common.items.shield.ManusShield;
import com.unseen.helios.common.items.tools.ItemChampionSpear;
import com.unseen.helios.common.items.tools.ItemGreatFinger;
import com.unseen.helios.common.items.tools.ManusSword;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.util.ModReference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Where we will define our Items for the mod
 */
public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    private static final Item.ToolMaterial MANUS_SWORD_MATERIAL = EnumHelper.addToolMaterial("manus_sword_material", 1, 460, 6.0F, (float) GeneralConfig.manus_sword_base_damage, 30);
    private static final Item.ToolMaterial GREAT_FINGER_MATERIAL = EnumHelper.addToolMaterial("great_finger_material", 1, 236, 6.0F, (float) GeneralConfig.great_finger_base_damage, 30);
    private static final Item.ToolMaterial CHAMPION_SPEAR_MATERIAL = EnumHelper.addToolMaterial("champion_spear_material", 1, 460, 6.0F, (float) GeneralConfig.champion_spear_base_damage, 10);

    private static final ItemArmor.ArmorMaterial MANUS_ARMOR = EnumHelper.addArmorMaterial("manus_armor_material", ModReference.MOD_ID + ":manus", 90, new int[]{4, 7,9,4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.5F);
    public static final Item INVISIBLE = new ItemBase("invisible", null);
    public static final Item MANUS_GOOP = new ItemBase("manus_goop", ModCreativeTab.ITEMS);
    public static final Item MANUS_STICK = new ItemBase("manus_stick", ModCreativeTab.ITEMS);
    public static final Item MANUS_CORE = new ItemBase("manus_core", ModCreativeTab.ITEMS);
    public static final Item HEMOGLEPHIN = new ItemBase("hemoglephin", ModCreativeTab.ITEMS);
    public static final Item MANUS_BRAIN = new ItemBase("manus_brain", ModCreativeTab.ITEMS);
    public static final Item MANUS_FINGER = new ItemBase("manus_finger", ModCreativeTab.ITEMS);
    public static final Item SCROLL_OF_GREED = new ItemGreedScroll("scroll_of_greed", ModCreativeTab.ITEMS, "scroll_desc");
    public static final Item GREED_COMPASS = new ItemManusCompass("greed_compass", ModCreativeTab.ITEMS, "greed_compass_desc");
    public static final Item GREED_TRINKET = new ItemDescription("hand_trinket", ModCreativeTab.ITEMS, "hand_trinket_desc", 96);

    //shield
    public static final Item MANUS_SHIELD = new ManusShield("manus_shield", ModCreativeTab.ITEMS, "manus_shield_desc");

    //weapons
    public static final Item MANUS_SWORD = new ManusSword("manus_sword", MANUS_SWORD_MATERIAL, ModCreativeTab.ITEMS, "manus_sword_desc");
    public static final Item GREAT_FINGER = new ItemGreatFinger("great_finger", GREAT_FINGER_MATERIAL, ModCreativeTab.ITEMS, "great_finger_desc");
    public static final Item CHAMPION_SPEAR = new ItemChampionSpear("champion_spear", CHAMPION_SPEAR_MATERIAL, ModCreativeTab.ITEMS, "champion_spear_desc");

    //armor
    public static Item MANUS_HELMET = new ArmorManusHelmet("manus_helmet", MANUS_ARMOR, 1, EntityEquipmentSlot.HEAD, "manus", "manus_armor_desc");
    public static Item MANUS_CHESTPLATE = new ArmorManus("manus_chestplate", MANUS_ARMOR, 1, EntityEquipmentSlot.CHEST, "manus", "manus_armor_desc");
    public static Item MANUS_BOOTS = new ArmorManus("manus_boots", MANUS_ARMOR, 1, EntityEquipmentSlot.FEET, "manus", "manus_armor_desc");

    //locator
    public static Item CHAMPION_LOCATOR = new ItemChampionLocator("champion_locator", "champion_locator_desc");

    //food
    public static Item STAGA_FRUIT = new ItemStagaFruit("staga_fruit", 4, 0.1F, "staga_fruit_desc");


    //projectile
    public static Item TRACKING_PROJECTILE = new ItemBase("tracking_orb", null);

}
