package com.unseen.helios.common.items.food;

import com.unseen.helios.Main;
import com.unseen.helios.init.ModCreativeTab;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.IHasModel;
import com.unseen.helios.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemStagaFruit extends ItemFood implements IHasModel {

    private String info_loc;


    public ItemStagaFruit(String name, int amount, float saturation, String info_loc) {
        super(amount, saturation, false);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.ITEMS);
        this.info_loc = info_loc;
        ModItems.ITEMS.add(this);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityLivingBase living) {
        if (living instanceof EntityPlayer && !world.isRemote) {
            living.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 500, 0, false, false));
            living.addPotionEffect(new PotionEffect(ModPotions.STAGNATION, 600, 0, false, false));

        }
        return super.onItemUseFinish(itemStack, world, living);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 15;
    }
}
