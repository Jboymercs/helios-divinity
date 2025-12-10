package com.unseen.helios.init;

import com.unseen.helios.Main;
import com.unseen.helios.common.entity.entities.EntityApostleHand;
import com.unseen.helios.common.entity.entities.EntityManusAvaritia;
import com.unseen.helios.common.entity.entities.EntityManusMinion;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.common.entity.entities.attacks.EntityGodBeam;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.common.entity.entities.effects.EntityGreedHand;
import com.unseen.helios.common.entity.projectile.EntityChampionLocator;
import com.unseen.helios.common.entity.projectile.ProjectileChampionOrb;
import com.unseen.helios.common.entity.projectile.ProjectileCyclerSoul;
import com.unseen.helios.common.entity.projectile.ProjectileTrackingOrb;
import com.unseen.helios.common.entity.tileEntities.TileEntityChampionHead;
import com.unseen.helios.common.entity.tileEntities.TileEntityRemnantCycler;
import com.unseen.helios.util.ModReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Where Entities are registered
 */
public class ModEntities {

    private static final Map<Class<? extends Entity>, String> ID_MAP = new HashMap<>();

    private static int ENTITY_START_ID = 123;
    private static int PROJECTILE_START_ID = 230;
    private static int PARTICLE_START_ID = 500;

    public static Vec3i nameless_champion = new Vec3i(0xABABAB,0x9700BA,0);
    public static void registerEntities() {
        //blah blah register deez
        registerEntity("manus_avaritia", EntityManusAvaritia.class, ENTITY_START_ID++, 50);
        registerEntity("apostle_hand", EntityApostleHand.class, ENTITY_START_ID++, 50);
        registerEntity("god_beam", EntityGodBeam.class, ENTITY_START_ID++, 50);
        registerEntity("manus_minion", EntityManusMinion.class, ENTITY_START_ID++, 50);
        registerEntity("cycler_projectile", ProjectileCyclerSoul.class, ENTITY_START_ID++, 40);
        registerEntity("greed_hand", EntityGreedHand.class, ENTITY_START_ID++, 50);
        registerEntityWithID("nameless_champion", EntityNamelessChampion.class, ENTITY_START_ID++, 50, nameless_champion);
        registerEntity("champion_aoe", EntityChampionAOE.class, ENTITY_START_ID++, 40);
        registerEntity("champion_tracking_orb", ProjectileTrackingOrb.class, ENTITY_START_ID++, 40);
        registerEntity("champion_orb", ProjectileChampionOrb.class, ENTITY_START_ID++, 40);
        registerEntity("champion_locator_proj", EntityChampionLocator.class, ENTITY_START_ID++, 30);
        //
        registerTileEntity(TileEntityRemnantCycler.class, "remant_cycler");
      //  registerTileEntity(TileEntityChampionHead.class, "champion_helmet");
    }

    public static void registerEntitySpawns() {
        //registers spawn rates and locations
    }

    /**
     * Below functions are for registering specific entities, Tile entities
     * @param entity
     * @return
     */
    public static String getID(Class<? extends Entity> entity) {
        if (ID_MAP.containsKey(entity)) {
            return ModReference.MOD_ID + ":" + ID_MAP.get(entity);
        }
        throw new IllegalArgumentException("Mapping of an entity has not be registered for the end expansion mob spawner system.");
    }

    private static void registerEntityWithID(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor) {
        EntityRegistry.registerModEntity(new ResourceLocation(ModReference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
        ID_MAP.put(entity, name);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor) {
        EntityRegistry.registerModEntity(new ResourceLocation(ModReference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(ModReference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
    }

    private static void registerFastProjectile(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(ModReference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, false);
    }

    private static void registerTileEntity(Class<? extends TileEntity> entity, String name) {
        GameRegistry.registerTileEntity(entity, new ResourceLocation(ModReference.MOD_ID + ":" + name));
    }

    /**
     * Below are methods for registering spawnrates, and spawnrates for specific biomes
     * @param entityClass
     * @param creatureType
     * @param weight
     * @param min
     * @param max
     * @param biomesAllowed
     */
    private static void spawnRate(Class<? extends EntityLiving> entityClass, EnumCreatureType creatureType, int weight, int min, int max, BiomeDictionary.Type biomesAllowed) {
        for(Biome biome: BiomeDictionary.getBiomes(biomesAllowed)) {
            if(biome != null && weight > 0) {
                EntityRegistry.addSpawn(entityClass, weight, min, max, creatureType, biome);

            }
        }
    }

    private static void spawnRateBiomeSpecific(Class<? extends EntityLiving> entityClass, EnumCreatureType creatureType, int weight, int min, int max, Biome biome) {
        if(biome != null && weight > 0) {
            EntityRegistry.addSpawn(entityClass, weight, min, max, creatureType, biome);

        }

    }
}
