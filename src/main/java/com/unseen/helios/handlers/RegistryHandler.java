package com.unseen.helios.handlers;

import com.google.common.collect.Maps;
import com.unseen.helios.client.item.render.RenderChampionSpear;
import com.unseen.helios.client.item.render.RenderGreatFinger;
import com.unseen.helios.client.item.render.RenderManusShield;
import com.unseen.helios.common.blocks.BlockManusDebris;
import com.unseen.helios.init.ModBlocks;
import com.unseen.helios.init.ModItems;
import com.unseen.helios.util.IHasModel;
import com.unseen.helios.util.ModReference;
import com.unseen.helios.util.block.AdvancedStateMap;
import mod.azure.azurelib.render.item.AzItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

/**
 * Where we will register all of our blocks and items
 */
@Mod.EventBusSubscriber
public class RegistryHandler {
    private static IForgeRegistry<Item> itemRegistry;
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        itemRegistry = event.getRegistry();
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));

        //for(Block block : ModBlocks.MANUS_DEBRIS) {
        //    ModBlocks.blockReadyForRegister(block, "manus_debris_" + ((BlockManusDebris)block).getOrdinal());
       //     ModItems.ITEMS.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
      //  }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        //Have to call these here due to being geckolib animated models
        ModelLoader.setCustomModelResourceLocation(ModItems.MANUS_SHIELD, 0, new ModelResourceLocation(ModReference.MOD_ID + ":manus_shield", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.GREAT_FINGER, 0, new ModelResourceLocation(ModReference.MOD_ID + ":great_finger", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.CHAMPION_SPEAR, 0, new ModelResourceLocation(ModReference.MOD_ID + ":champion_spear", "inventory"));
       // ModItems.MANUS_SHIELD.setTileEntityItemStackRenderer(new RenderManusShield());
      //  ModItems.GREAT_FINGER.setTileEntityItemStackRenderer(new RenderGreatFinger());
      //  ModItems.CHAMPION_SPEAR.setTileEntityItemStackRenderer(new RenderChampionSpear());
        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IStateMappedBlock) {
                AdvancedStateMap.Builder builder = new AdvancedStateMap.Builder();
                ((IStateMappedBlock) block).setStateMapper(builder);
                ModelLoader.setCustomStateMapper(block, builder.build());
            }

      //      for(Block block2 : ModBlocks.MANUS_DEBRIS) {
         ///       ModelLoader.setCustomStateMapper(block2, sharedManusClumper());
        //    }

            if (block instanceof IHasModel) {
                ModelLoader.setCustomStateMapper(ModBlocks.CHAMPION_HEAD, new StateMap.Builder().ignore(BlockSkull.NODROP).ignore(BlockSkull.FACING).build());
                ((IHasModel) block).registerModels();
            }
        }
     //   ModelLoader.setCustomStateMapper(ModBlocks.BOSS_RESUMMON_BLOCK, new BossStateMapper());
    }



    public interface IStateMappedBlock {
        /**
         * Sets the statemap
         *
         * @param builder
         */
        @SideOnly(Side.CLIENT)
        void setStateMapper(AdvancedStateMap.Builder builder);
    }

    @SideOnly(Side.CLIENT)
    private static StateMapperBase sharedManusClumper()
    {
        return new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                map.remove(BlockManusDebris.META);
                System.out.println("MANUS is at clockwork " + ModReference.MOD_ID + ":manus_debris");
                return new ModelResourceLocation( ModReference.MOD_ID + ":manus_debris", getPropertyString(map));
            }
        };
    }
}
