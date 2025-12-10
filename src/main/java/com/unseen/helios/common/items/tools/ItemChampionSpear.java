package com.unseen.helios.common.items.tools;

import com.unseen.helios.common.entity.player.ActionPlayerSummonAOE;
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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class ItemChampionSpear extends ToolSword implements IAnimatable {
    String info_loc;
    private int playerTickDamage;
    private final String ANIM_FINGER_ATTACK = "attack";
    private String controllerName = "attack_controller";
    public AnimationFactory factory = new AnimationFactory(this);

    public ItemChampionSpear(String name, ToolMaterial material, CreativeTabs tab, String info_loc) {
        super(name, material, tab);
        this.info_loc = info_loc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(info_loc));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, ModSoundHandler.CHAMPION_SWING, SoundCategory.NEUTRAL, 0.4f, 0.7f / (attacker.world.rand.nextFloat() * 0.4F + 0.2f));
        return true;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(player.isSneaking() && !player.getCooldownTracker().hasCooldown(this)) {
            worldIn.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.CHAMPION_SUMMON_AOE, SoundCategory.NEUTRAL, 0.75f, 0.7f / (worldIn.rand.nextFloat() * 0.4F + 0.2f));
            new ActionPlayerSummonAOE().performAction(player);
            player.getCooldownTracker().setCooldown(this, GeneralConfig.champion_spear_aoe_cooldown * 20);
            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 200, 1, false, false));
            stack.damageItem(5, player);
        } else if(!player.getCooldownTracker().hasCooldown(this)) {
            AnimationController<?> controller = GeckoLibUtil.getControllerForStack(this.factory, stack, controllerName);
            if(controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation(ANIM_FINGER_ATTACK, false));
            }
            worldIn.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.CHAMPION_DASH, SoundCategory.NEUTRAL, 0.75f, 0.7f / (worldIn.rand.nextFloat() * 0.4F + 0.2f));
            playerTickDamage = player.ticksExisted + 15;
            player.addPotionEffect(new PotionEffect(ModPotions.GREED, 100, 1, false, false));
            Vec3d moveVec = player.getLookVec().scale(1.7F);
            if(player.canBePushed()) {
                player.motionX = moveVec.x;
                player.motionY = moveVec.y * 0.3;
                player.motionZ = moveVec.z;
                player.velocityChanged = true;
            }
            player.getCooldownTracker().setCooldown(this, GeneralConfig.champion_spear_dash_cooldown * 20);
            stack.damageItem(2, player);
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
                Vec3d playerPos = new Vec3d(player.posX + playerLookVec.x * 3.4D,player.posY + playerLookVec.y + player.getEyeHeight(), player. posZ + playerLookVec.z * 3.4D);
                Vec3d offsetPos = playerPos.subtract(player.getPositionVector());
                List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(1.6).offset(offsetPos), e -> e != player);
                if(!targets.isEmpty()) {
                    for(EntityLivingBase base: targets) {
                        double healthDamage = this.getAttackDamage() + GeneralConfig.champion_spear_bonus_damage;
                        base.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 160, 1, false, false));
                        base.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player).setDamageBypassesArmor(), (float) healthDamage);
                        Vec3d dir = player.getPositionVector().subtract(base.getPositionVector());
                        this.onEnemyHit(player, base, dir);
                    }
                    player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSoundHandler.CHAMPION_SWING, SoundCategory.NEUTRAL, 1.0f, 0.7f / (player.world.rand.nextFloat() * 0.4F + 0.2f));
                }
                playerTickDamage = player.ticksExisted - 10;
            }
        }
    }

    public void onEnemyHit(EntityLivingBase user, EntityLivingBase enemy, Vec3d rammingDir) {
        enemy.knockBack(user, 1.9F, rammingDir.x, rammingDir.z);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ItemChampionSpear> controller = new AnimationController<ItemChampionSpear>(this,
                controllerName, 5, this::predicateAttack);

        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicateAttack(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public EnumRarity getRarity(ItemStack stack)
    { return EnumRarity.UNCOMMON; }

    @Override
    protected double getAttackSpeed() {
        return -3.0000000953674316D;
    }
}
