package com.unseen.helios.init;

import com.unseen.helios.potionEffect.PotionGreed;
import com.unseen.helios.potionEffect.PotionStagnant;
import com.unseen.helios.util.ModReference;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public class ModPotions {

    private ModPotions(){}

    public static PotionGreed GREED = (PotionGreed) new PotionGreed("manus_greed",true, 0);
    public static PotionStagnant STAGNATION = (PotionStagnant) new PotionStagnant("stagnant",false, 0);

    public static PotionType greedy =  new PotionType("potionGreedy", new PotionEffect[]{new PotionEffect(ModPotions.GREED, 200)}).setRegistryName("greedy");
    public static PotionType stagnation =  new PotionType("potionStagnation", new PotionEffect[]{new PotionEffect(ModPotions.STAGNATION, 200)}).setRegistryName("stagnation");

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
                GREED,
                STAGNATION
        );
    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(
                greedy,
                stagnation
        );

    }
}
