package com.unseen.helios.common.items.shield;

import com.unseen.helios.Main;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.IHasModel;
import com.unseen.helios.util.ModUtils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ManusShield extends HeliosShieldItem implements IHasModel {

    private String info_loc;
    public int hitCounter = 0;

    public ManusShield(String name, CreativeTabs tabs, String info_loc) {
        super(name);
        setCreativeTab(tabs);
        this.setMaxDamage(128);
        this.info_loc = info_loc;
        this.maxStackSize = 1;
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getSubCompound("BlockEntityTag") != null)
        {
            EnumDyeColor enumdyecolor = TileEntityBanner.getColor(stack);
            return I18n.translateToLocal("helios:item.manus_shield." + enumdyecolor.getTranslationKey() + ".name");
        }
        else
        {
            return I18n.translateToLocal("helios.desc.manus_shield_name");
        }
    }

    @Override
    public void onBlockingDamage(ItemStack shield, EntityPlayer player) {
        if(hitCounter > 4 && !player.world.isRemote) {
            player.resetActiveHand();
            player.disableShield(true);
            player.getCooldownTracker().setCooldown(shield.getItem(), GeneralConfig.finger_shield_cooldown * 20);
            this.hitCounter = 0;
        } else if(!player.getCooldownTracker().hasCooldown(shield.getItem())) {
            if(!player.world.isRemote) {
                hitCounter++;
                player.addPotionEffect(new PotionEffect(ModPotions.GREED, 40, 0, false, false));
                List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(3), e -> e != player);
                if(!targets.isEmpty()) {
                    for(EntityLivingBase entity : targets) {
                        if(entity.canBeCollidedWith()) {
                            Vec3d dir = player.getPositionVector().subtract(entity.getPositionVector());
                            this.onEnemyHit(player, entity, dir);
                        }
                    }
                }
            }
            player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.UTIL_MANUS_SHIELD_BLOCK, SoundCategory.NEUTRAL, 0.75f, 0.7f / (player.world.rand.nextFloat() * 0.4F + 0.2f));
            Main.proxy.spawnParticle(2, player.posX, player.posY + 1, player.posZ, 0, 0, 0, 9320921);
        }
        super.onBlockingDamage(shield, player);
    }

    public void onEnemyHit(EntityLivingBase user, EntityLivingBase enemy, Vec3d rammingDir) {
        enemy.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)user).setDamageBypassesArmor(), GeneralConfig.finger_shield_damage);
        enemy.knockBack(user, 0.9F, rammingDir.x, rammingDir.z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
        ItemBanner.appendHoverTextFromTileEntityTag(stack, tooltip);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
