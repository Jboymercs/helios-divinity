package com.unseen.helios.common.entity.entities.manus_avatiria;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.base.EntityHeliosMobBase;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.config.MobConfig;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.init.ModPotions;
import com.unseen.helios.util.ModRand;
import com.unseen.helios.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class EntityManusAvaritia extends EntityHeliosMobBase {
    private final String ANIM_SUMMON_APOSTLE = "apostle";
    private final String ANIM_TRADE_ACTION = "trade";
    private static final DataParameter<Boolean> SLEEP_MODE = EntityDataManager.createKey(EntityManusAvaritia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SUMMON_APOSTLE = EntityDataManager.createKey(EntityManusAvaritia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRADE_ACTION = EntityDataManager.createKey(EntityManusAvaritia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> ITEM_HAND = EntityDataManager.<ItemStack>createKey(EntityManusAvaritia.class, DataSerializers.ITEM_STACK);
    public final ADManusAvaritia animator = new ADManusAvaritia(this);
    private void setSleepMode(boolean value) {this.dataManager.set(SLEEP_MODE, Boolean.valueOf(value));}
    public boolean isSleepMode() {return this.dataManager.get(SLEEP_MODE);}
    private void setTradeAction(boolean value) {this.dataManager.set(TRADE_ACTION, Boolean.valueOf(value));}
    private boolean isTradeAction() {return this.dataManager.get(TRADE_ACTION);}

    private void setSummonApostle(boolean value) {this.dataManager.set(SUMMON_APOSTLE, Boolean.valueOf(value));}
    private boolean isSummonApostle() {return this.dataManager.get(SUMMON_APOSTLE);}



    public EntityManusAvaritia(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        this.setImmovable(true);
        this.setSize(0.6F, 0.75F);
        this.isNonHealableCreature = true;
    }

    public EntityManusAvaritia(World worldIn) {
        super(worldIn);
        this.setImmovable(true);
        this.setSize(0.6F, 0.75F);
        this.isNonHealableCreature = true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("Sleep_Mode", this.isSleepMode());
        nbt.setBoolean("Trade_Action", this.isTradeAction());
        nbt.setBoolean("Summon_Apostle", this.isSummonApostle());
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setSleepMode(nbt.getBoolean("Sleep_Mode"));
        this.setTradeAction(nbt.getBoolean("Trade_Action"));
        this.setSummonApostle(nbt.getBoolean("Summon_Apostle"));
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(ITEM_HAND, ItemStack.EMPTY);
        this.dataManager.register(SLEEP_MODE, Boolean.valueOf(true));
        this.dataManager.register(TRADE_ACTION, Boolean.valueOf(false));
        this.dataManager.register(SUMMON_APOSTLE, Boolean.valueOf(false));
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MobConfig.hand_of_greed_health);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public void initEntityAI() {
    }

    private boolean ironTier = false;
    private boolean goldTier = false;
    private boolean diamondTier = false;

    private boolean setDeathPlay = false;

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setRotation(0, 0);
        this.setRotationYawHead(0);

        if(!world.isRemote) {

            if(setDeathPlay && !this.isSummonApostle()) {
                world.setBlockToAir(this.getPosition().down());
                this.world.newExplosion(this, this.posX, this.posY, this.posZ, 3, true, true);
                this.setDeathPlay = false;
            }

            if(!this.isTradeAction() && !this.isSummonApostle()) {
                if(this.ticksExisted % 40 == 0) {
                    AxisAlignedBB box = getEntityBoundingBox().grow(8, 6, 8).offset(0, -2, 0);
                    BlockPos setTooPos = ModUtils.searchForBlocksObsdilith(box, world, this, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MobConfig.iron_tier_block))).getDefaultState());
                    BlockPos setTooPosTwo = ModUtils.searchForBlocksObsdilith(box, world, this, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MobConfig.gold_tier_block))).getDefaultState());
                    BlockPos setTooPosThree = ModUtils.searchForBlocksObsdilith(box, world, this, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MobConfig.diamond_tier_block))).getDefaultState());

                    if(setTooPos != null) {
                        ironTier = true;
                    } else {
                        ironTier = false;
                    }

                    if(setTooPosTwo != null) {
                        goldTier = true;
                    } else {
                        goldTier = false;
                    }

                    if(setTooPosThree != null) {
                        diamondTier = true;
                    } else {
                        diamondTier = false;
                    }

                    if(this.isSleepMode()) {
                        if(setTooPos != null || setTooPosTwo != null || setTooPosThree != null) {
                            this.setSleepMode(false);
                            this.playSound(ModSoundHandler.HAND_AWAKE, 1.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
                        }
                    } else if (!ironTier && !goldTier && !diamondTier) {
                        if(!this.isSleepMode()) {
                            this.playSound(ModSoundHandler.HAND_SLEEP, 1.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
                        }
                        this.setSleepMode(true);
                        this.equipItemInHand(TRADER_HAND.HAND, new ItemStack(ModItems.INVISIBLE));
                    }

                }
            }


            if(!this.isSleepMode() && !this.isTradeAction()) {
                if(this.ticksExisted % 100 == 0) {
                    if(hasAltarBeenCompleted() && !this.isSummonApostle() && world.getClosestPlayer(this.posX, this.posY, this.posZ, 5, false) != null) {
                        EntityPlayer player = world.getClosestPlayer(this.posX, this.posY, this.posZ, 5, false);
                            this.summonApostleAtPos(player);
                    }
                }
            }
        }
    }

    private void summonApostleAtPos(EntityPlayer owner) {
        this.setSummonApostle(true);
        this.setFightMode(true);
        animator.setStatusUpdate(ANIM_SUMMON_APOSTLE);
        BlockPos pos = ModUtils.searchForBlocks(this.getEntityBoundingBox().grow(2), world, ModBlocks.CHAMPION_HEAD.getDefaultState());
        if(pos != null) {
            world.setBlockToAir(pos);
        }

        addEvent(()-> {
            this.playSound(ModSoundHandler.HAND_TRANSFORM, 1.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }, 5);

        addEvent(()-> {
            this.setDead();
            EntityApostleHand hand = new EntityApostleHand(world);
            hand.setPosition(this.getPosition().getX() + 0.5, this.getPosition().getY(), this.getPosition().getZ() + 0.5);
            hand.setOwnerId(owner.getUniqueID());
            world.spawnEntity(hand);
            this.setSummonApostle(false);
        }, 49);
    }


    private boolean hasAltarBeenCompleted() {
        BlockPos pos = this.getPosition().add(0, -1, 0);

        if(world.getBlockState(pos.add(1,0,0)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(-1,0,0)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(0,0,1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(0,0,-1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(1,0,-1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(1,0,1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(-1,0,1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR &&
                world.getBlockState(pos.add(-1,0,-1)).getBlock() == ModBlocks.MANUS_EXTENSION_ALTAR) {

            if(world.getBlockState(pos.add(1, 1, 0)).getBlock() == ModBlocks.CHAMPION_HEAD || world.getBlockState(pos.add(-1, 1, 0)).getBlock() == ModBlocks.CHAMPION_HEAD ||
                    world.getBlockState(pos.add(0, 1, 1)).getBlock() == ModBlocks.CHAMPION_HEAD || world.getBlockState(pos.add(0, 1, -1)).getBlock() == ModBlocks.CHAMPION_HEAD ||
                    world.getBlockState(pos.add(1, 1, 1)).getBlock() == ModBlocks.CHAMPION_HEAD || world.getBlockState(pos.add(1, 1, -1)).getBlock() == ModBlocks.CHAMPION_HEAD ||
                    world.getBlockState(pos.add(-1, 1, 1)).getBlock() == ModBlocks.CHAMPION_HEAD || world.getBlockState(pos.add(-1, 1, -1)).getBlock() == ModBlocks.CHAMPION_HEAD) {
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!this.isSleepMode() && !world.isRemote && !this.isTradeAction()) {
            //iron tier trades
            if(this.ironTier) {
                if(ModUtils.canItemBeTraded(stack.getItem(), MobConfig.manus_goop_trade_items)) {
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.MANUS_GOOP), ModRand.range(1, 3));
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 40, 0, false, false));
                } else if (ModUtils.canItemBeTraded(stack.getItem(), MobConfig.manus_stick_trade_items)) {
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.MANUS_STICK), 1);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 40, 0, false, false));
                } else if (ModUtils.canItemBeTraded(stack.getItem(), MobConfig.ferment_stonebricks_trade_items)) {
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(Item.getItemFromBlock(ModBlocks.FERMENT_STONE)), 8);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 40, 0, false, false));
                }
            }

            //Gold Tier Trades
            if(this.goldTier) {
                if(ModUtils.canItemBeTraded(stack.getItem(), MobConfig.great_finger_trade_items)) {
                    //Tartarus Part
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.MANUS_FINGER), 1);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 80, 0, false, false));
                } else if (ModUtils.canItemBeTraded(stack.getItem(), MobConfig.grafted_core_trade_items)) {
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.MANUS_CORE), 1);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 80, 0, false, false));
                } else if (ModUtils.canItemBeTraded(stack.getItem(), MobConfig.champion_locator_trade_items)) {
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.CHAMPION_LOCATOR), 1);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 80, 0, false, false));
                }
            }

            //diamond Tier Trades
            if(this.diamondTier) {
                if(ModUtils.canItemBeTraded(stack.getItem(), MobConfig.hemoglephin_trade_items)) {
                    //Glepholin
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.HEMOGLEPHIN), ModRand.range(1, 3));
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 60, 1, false, false));
                } else if (ModUtils.canItemBeTraded(stack.getItem(), MobConfig.depths_brain_trade_items)) {
                    //Manus Brains
                    doTradeAction(player, new ItemStack(stack.getItem()), new ItemStack(ModItems.MANUS_BRAIN), 1);
                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotions.GREED, 60, 1, false, false));
                }
            }
        }


        if(this.isSleepMode() && !world.isRemote) {
            player.sendStatusMessage(new TextComponentTranslation("helios.no_valuable_blocks", new Object[0]), true);
        }
        return super.processInteract(player, hand);
    }


    private void doTradeAction(EntityPlayer player, ItemStack input, ItemStack output, int outputAmount) {
        this.setTradeAction(true);
        animator.setStatusUpdate(ANIM_TRADE_ACTION);
        addEvent(()-> this.equipItemInHand(TRADER_HAND.HAND, input), 10);
        addEvent(()-> {
            this.playSound(SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, 0.75f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }, 8);

        addEvent(()-> {
            this.playSound(ModSoundHandler.HAND_TRADE, 1.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }, 25);
        addEvent(()-> {
            this.equipItemInHand(TRADER_HAND.HAND, new ItemStack(ModItems.INVISIBLE));
        }, 32);

        addEvent(()-> {
                this.equipItemInHand(TRADER_HAND.HAND, output);
        }, 55);

        addEvent(()-> {
            this.equipItemInHand(TRADER_HAND.HAND, new ItemStack(ModItems.INVISIBLE));
            EntityItem itemToThrow = new EntityItem(world, this.posX, this.posY + this.getEyeHeight() - 0.1, this.posZ, new ItemStack(output.getItem(), outputAmount));
            Vec3d lookScale = player.getPositionVector();
            Vec3d relPos = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 1.6, 0)));
            double d0 = lookScale.y + (double)player.getEyeHeight() - 1.100000023841858D;
            double d1 = lookScale.x - relPos.x;
            double d2 = d0 - this.posY;
            double d3 = lookScale.z - relPos.z;
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
            this.shoot(itemToThrow, d1, d2 + (double)(f * 0.3F), d3, 0.3F, 1.0F);
            itemToThrow.velocityChanged = true;
            world.spawnEntity(itemToThrow);
        }, 64);

        addEvent(()-> {
            this.playSound(ModSoundHandler.HAND_TRADE_DONE, 1.0f, 0.8f / (rand.nextFloat() * 0.4F + 0.4f));
        }, 60);

        addEvent(()-> {
            this.setTradeAction(false);
            if(this.getHealth() - 1 == 0) {
                this.setDeathPlay = true;
            }
            this.setHealth(this.getHealth() - 1);
        }, 85);

    }

    public void shoot(Entity entityIn, double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        entityIn.motionX = x;
        entityIn.motionY = y;
        entityIn.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        entityIn.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        entityIn.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        entityIn.prevRotationYaw = entityIn.rotationYaw;
        entityIn.prevRotationPitch = entityIn.rotationPitch;
    }

    public void updateAnimations() {
        if(!this.isTradeAction() && !this.isSummonApostle()) {
            animator.setIdleMode(this.isSleepMode());
        }

        if(!this.isSummonApostle()) {
            animator.setRootsIdle();
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ModUtils.performNTimes(2, (i) -> {
                Main.proxy.spawnParticle(1, this.posX + ModRand.getFloat(6), this.posY + ModRand.getFloat(4), this.posZ + ModRand.getFloat(6), 0, 0.06, 0, 9320921);
            });
        }
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!this.isSleepMode() && world.rand.nextInt(9) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
    }

    public enum TRADER_HAND {
        HAND("TradeJoint");

        public String getBoneName() {
            return this.boneName;
        }

        private String boneName;

        TRADER_HAND(String bone) {
            this.boneName = bone;
        }

        @Nullable
        public static EntityManusAvaritia.TRADER_HAND getFromBoneName(String boneName) {
            if ("TradeJoint".equals(boneName)) {
                return HAND;
            }
            return null;
        }

        public int getIndex() {
            if (this == TRADER_HAND.HAND) {
                return 1;
            }
            return 0;
        }
    }

    public void equipItemInHand(EntityManusAvaritia.TRADER_HAND head, ItemStack state) {
        if(world.isRemote) {
            return;
        }
        if (head == TRADER_HAND.HAND) {
            this.dataManager.set(ITEM_HAND, state);
        }
    }

    public ItemStack getItemFromRoyalHand(EntityManusAvaritia.TRADER_HAND head) {
        if (head == TRADER_HAND.HAND) {
            return this.dataManager.get(ITEM_HAND);
        }
        return null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void onDeath(DamageSource cause) {
      super.onDeath(cause);
    }


}
