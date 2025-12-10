package com.unseen.helios.init;

import com.unseen.helios.util.tab.HeliosCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;

public class ModCreativeTab {
    public static CreativeTabs ITEMS = new HeliosCreativeTab(CreativeTabs.getNextID(), "helios_divinity", () -> ModItems.CHAMPION_LOCATOR);
}
