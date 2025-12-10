package com.unseen.helios.common.items;

import com.unseen.helios.Main;
import com.unseen.helios.init.ModCreativeTab;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name, CreativeTabs tab) {
        setTranslationKey(name);
        setRegistryName(name);
        if (tab != null) {
            setCreativeTab(tab);
        }

        ModItems.ITEMS.add(this);
    }

    public ItemBase(String name) {
        this(name, ModCreativeTab.ITEMS);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
