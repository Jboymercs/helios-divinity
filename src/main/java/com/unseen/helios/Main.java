package com.unseen.helios;

import com.unseen.helios.capability.CapabilityPlayerGreed;
import com.unseen.helios.common.entity.tileEntities.TileEntityChampionHead;
import com.unseen.helios.common.world.structures.ModWorldGenerator;
import com.unseen.helios.handlers.ModSoundHandler;
import com.unseen.helios.init.ModEntities;
import com.unseen.helios.init.ModStructures;
import com.unseen.helios.proxy.CommonProxy;
import com.unseen.helios.util.HDLogger;
import com.unseen.helios.util.ModReference;
import com.unseen.helios.util.command.CommandLocateHelios;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = ModReference.MOD_ID, name = ModReference.NAME, version = ModReference.VERSION)
public class Main {

    @SidedProxy(clientSide = ModReference.CLIENT_PROXY_CLASS, serverSide = ModReference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static Logger LOGGER = LogManager.getLogger(ModReference.MOD_ID);
    public static SimpleNetworkWrapper network;
    @Mod.Instance
    public static Main instance;

    public Main() {
        HDLogger.clearLog();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GeckoLib.initialize();
        //Handles Entities
        GameRegistry.registerTileEntity(TileEntityChampionHead.class, new ResourceLocation(ModReference.MOD_ID, "champion_helmet"));
        ModEntities.registerEntities();
        ModEntities.registerEntitySpawns();
        //Proxy Init
        proxy.init();
        //Registers Sounds
        ModSoundHandler.registerSounds();
        //Registers Structures
        GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 1);
        //
        CapabilityManager.INSTANCE.register(CapabilityPlayerGreed.IPlayerGreedCapability.class, new CapabilityPlayerGreed.Storage(), CapabilityPlayerGreed.HeliosPlayerGreedMethods::new);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        //Handles Structure Registration
        ModStructures.handleStructureRegistries();
        //Handles Sounds Registration
        proxy.registerEventHandlers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // register server commands
        event.registerServerCommand(new CommandLocateHelios());
    }
}
