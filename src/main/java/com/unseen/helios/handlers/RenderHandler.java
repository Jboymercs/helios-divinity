package com.unseen.helios.handlers;

import com.unseen.helios.client.entity.render.*;
import com.unseen.helios.common.blocks.championHead.RenderChampionHead;
import com.unseen.helios.common.entity.entities.apostle_hand.EntityApostleHand;
import com.unseen.helios.common.entity.entities.manus_avatiria.EntityManusAvaritia;
import com.unseen.helios.common.entity.entities.attacks.EntityChampionAOE;
import com.unseen.helios.common.entity.entities.attacks.god_beam.EntityGodBeam;
import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.common.entity.entities.effects.greed_hand.EntityGreedHand;
import com.unseen.helios.common.entity.projectile.EntityChampionLocator;
import com.unseen.helios.common.entity.projectile.ProjectileChampionOrb;
import com.unseen.helios.common.entity.projectile.ProjectileCyclerSoul;
import com.unseen.helios.common.entity.projectile.ProjectileTrackingOrb;
import com.unseen.helios.common.entity.tileEntities.TileEntityChampionHead;
import com.unseen.helios.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Function;

/**
 * Where we will handle all Entity Renders, need to add projectiles to the functions
 */

public class RenderHandler {





    public static void registerAllEntityRenders() {

    }



    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, U model, String... textures) {
        registerModEntityRenderer(entityClass, (manager) -> new RenderModEntity(manager, model, textures));
    }

    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, Function<RenderManager, Render<? super T>> renderClass) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>() {
            @Override
            public Render<? super T> createRenderFor(RenderManager manager) {
                return renderClass.apply(manager);
            }
        });
    }

    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass) {
        registerProjectileRenderer(projectileClass, null);
    }

    /**
     * Makes a projectile render with the given item
     *
     * @param projectileClass
     */
    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass, Item item) {
        RenderingRegistry.registerEntityRenderingHandler(projectileClass, new IRenderFactory<T>() {
            @Override
            public Render<? super T> createRenderFor(RenderManager manager) {
                return new RenderProjectile<T>(manager, Minecraft.getMinecraft().getRenderItem(), item);
            }
        });
    }

    public static void registerProjectileRenderers() {
        registerProjectileRenderer(ProjectileCyclerSoul.class, ModItems.INVISIBLE);
        registerProjectileRenderer(ProjectileTrackingOrb.class, ModItems.TRACKING_PROJECTILE);
        registerProjectileRenderer(ProjectileChampionOrb.class, ModItems.TRACKING_PROJECTILE);
        registerProjectileRenderer(EntityChampionLocator.class, ModItems.TRACKING_PROJECTILE);
    }

    public static void registerGeoEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityManusAvaritia.class, RenderManusAvaritia::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityApostleHand.class, RenderApostleHand::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGodBeam.class, RenderGodBeam::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGreedHand.class, RenderGreedHand::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNamelessChampion.class, RenderNamelessChampion::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChampionAOE.class, RenderChampionAOE::new);
        //Champion Head
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChampionHead.class, new RenderChampionHead());
    }

}
