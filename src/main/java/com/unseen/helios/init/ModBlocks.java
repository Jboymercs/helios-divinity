package com.unseen.helios.init;

import com.unseen.helios.common.blocks.*;
import com.unseen.helios.common.blocks.championHead.BlockChampionHead;
import com.unseen.helios.common.blocks.slab.BlockDoubleSlab;
import com.unseen.helios.common.blocks.slab.BlockHalfSlab;
import com.unseen.helios.handlers.ModSoundTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import java.util.ArrayList;
import java.util.List;

/**
 * Where we will define our blocks for the Mod
 */
public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final float PLANTS_HARDNESS = 0.2f;
    public static final float PLANTS_RESISTANCE = 2.0f;
    public static final float GLOOM_STONE_HARDNESS = 3.0F;
    public static final float GLOOM_STONE_RESITANCE = 6.0F;

    public static final Block FERMENT_STONE = new BlockBase("ferment_stone_brick", Material.ROCK, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block FERMENT_STONE_WALL = new BlockWallBase("ferment_stone_wall", Material.ROCK, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block FERMENT_STONE_STAIRS = new BlockStairBase("ferment_stone_stairs", FERMENT_STONE.getDefaultState(), GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE).setCreativeTab(ModCreativeTab.ITEMS);
    public static final BlockSlab FERMENT_SLAB_DOUBLE = new BlockDoubleSlab("ferment_slab_double", Material.ROCK, CreativeTabs.SEARCH, ModBlocks.FERMENT_SLAB_HALF, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE);
    public static final BlockSlab FERMENT_SLAB_HALF = new BlockHalfSlab("ferment_slab_half", Material.ROCK, ModCreativeTab.ITEMS, ModBlocks.FERMENT_SLAB_HALF, ModBlocks.FERMENT_SLAB_DOUBLE, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE);
    public static final Block FERMENT_PILLAR_STONE = new BlockPillarBase("ferment_pillar", Material.ROCK, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block FERMENT_SMOOTH = new BlockBase("ferment_smooth", Material.ROCK, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE).setCreativeTab(ModCreativeTab.ITEMS);

    public static final Block MANUS_ALTAR = new BlockManusAltar("manus_altar", Material.ROCK).setLightLevel(0.75F).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block MANUS_EXTENSION_ALTAR = new BlockDescription("manus_altar_extension", Material.ROCK, GLOOM_STONE_HARDNESS, GLOOM_STONE_RESITANCE, SoundType.STONE, "manus_altar_extension_desc").setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block MANUS_BLOCk = new BlockManusGoop("manus_block", Material.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, ModSoundTypes.MANUS_GOOP).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block REMNANT_RECYCLER = new BlockRemnantCycler("remnant_cycler", "remnant_cycler_desc").setLightLevel(0.75F).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block MANUS_GRASS = new BlockManusGrass("manus_grass").setLightLevel(0.5F).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block MANUS_VINES = new BlockVineBaseUP("manus_vines", Material.VINE, ModSoundTypes.MANUS_GRASS).setCreativeTab(ModCreativeTab.ITEMS);
    public static final Block FARM_MANUS_VINES = new BlockVineBaseUP("farm_manus_vines", Material.VINE, ModSoundTypes.MANUS_GRASS).setCreativeTab(ModCreativeTab.ITEMS).setLightLevel(1.0F);
  //  public static final Block MANUS_DEBRIS[] = {new BlockManusDebris(Material.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, 0), new BlockManusDebris( Material.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, 1),
  //          new BlockManusDebris (Material.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, 2),new BlockManusDebris(Material.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, 3)};


    public static final Block CHAMPION_HEAD = new BlockChampionHead("champion_head", "champion_head_desc");

    /** If blocks don't specify the 'addToTab' boolean, assume true.*/
    public static Block blockReadyForRegister(Block block, String name)
    { return blockReadyForRegister(block, name, true);}

    /** Slaps the names to Blocks, and adds them to the blockList to be registered in 'registerBlocks'.*/
    public static Block blockReadyForRegister(Block block, String name, Boolean addNormalModel)
    {
        block.setTranslationKey(name);
        block.setRegistryName(name);

        // if (addToTab) block.setCreativeTab(Main.OCEANEXPTAB);
        // else block.setCreativeTab(null);

        BLOCKS.add(block);

        return block;
    }

}
