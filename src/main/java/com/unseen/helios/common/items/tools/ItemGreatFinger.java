package com.unseen.helios.common.items.tools;

import com.unseen.helios.client.item.animation.ADispatcherItem;
import com.unseen.helios.config.GeneralConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemGreatFinger extends ToolSword {
    String info_loc;
    private int playerTickDamage;
    private final String ANIM_FINGER_ATTACK = "attack";
    private String controllerName = "attack_controller";
    private final ADispatcherItem animator;

    public ItemGreatFinger(String name, ToolMaterial material, CreativeTabs tab, String info_loc) {
        super(name, material, tab);
        this.info_loc = info_loc;
        this.animator = new ADispatcherItem();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!player.getCooldownTracker().hasCooldown(this)) {
            if(!worldIn.isRemote) {
                animator.performItemAnimation(player, stack, false, ANIM_FINGER_ATTACK);
            }
            worldIn.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.HAND_AWAKE, SoundCategory.NEUTRAL, 0.75f, 0.7f / (worldIn.rand.nextFloat() * 0.4F + 0.2f));
            playerTickDamage = player.ticksExisted + 15;
            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 100, 1, false, false));
            player.getCooldownTracker().setCooldown(this, GeneralConfig.great_finger_cooldown * 20);
            stack.damageItem(5, player);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if(entityIn instanceof EntityPlayer && !worldIn.isRemote) {
            EntityPlayer player = ((EntityPlayer) entityIn);

            //intiates the attack at a delayed method
            if(player.ticksExisted == playerTickDamage) {
                Vec3d playerLookVec = player.getLookVec();
                Vec3d playerPos = new Vec3d(player.posX + playerLookVec.x * 4.4D,player.posY + playerLookVec.y + player.getEyeHeight(), player. posZ + playerLookVec.z * 4.4D);
                Vec3d offsetPos = playerPos.subtract(player.getPositionVector());
                List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(2).offset(offsetPos), e -> e != player);
                if(!targets.isEmpty()) {
                    for(EntityLivingBase base: targets) {
                        double healthDamage = this.getAttackDamage() + GeneralConfig.great_finger_damage;
                        base.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 160, 1, false, false));
                        base.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player).setDamageBypassesArmor(), (float) healthDamage);
                        Vec3d dir = player.getPositionVector().subtract(base.getPositionVector());
                        this.onEnemyHit(player, base, dir);
                    }
                }
                if(player.isSneaking()) {
                    ModUtils.destroyBlocksInAABBDropBlocks(player.getEntityBoundingBox().grow(1.5).offset(offsetPos), worldIn, player);
                }
                playerTickDamage = player.ticksExisted - 10;
            }
        }
    }

    public void onEnemyHit(EntityLivingBase user, EntityLivingBase enemy, Vec3d rammingDir) {
        enemy.knockBack(user, 1.9F, rammingDir.x, rammingDir.z);
    }

    @Override
    protected double getAttackSpeed() {
        return -3.2000000953674316D;
    }
}
