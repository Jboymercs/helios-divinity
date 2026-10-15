package com.unseen.helios.client.entity.render;

import com.unseen.helios.common.entity.entities.manus_avatiria.AnimatorManusAvaritia;
import com.unseen.helios.common.entity.entities.manus_avatiria.EntityManusAvaritia;
import com.unseen.helios.util.ModReference;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.layer.AzBlockAndItemLayer;
import mod.azure.azurelib.util.math.Vector3f;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RenderManusAvaritia extends AzEntityRenderer<EntityManusAvaritia> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_avaritia.png");
    private static final ResourceLocation TEXTURE_SLEEP = new ResourceLocation(ModReference.MOD_ID, "textures/entity/manus_avaritia_sleep.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/manus/geo.manus_avaritia.json");

    public RenderManusAvaritia(RenderManager renderManager) {
        super(AzEntityRendererConfig.<EntityManusAvaritia>builder($ -> MODEL_RESLOC, entity ->
                                entity.isSleepMode() ? TEXTURE_SLEEP : TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .addRenderLayer(new AzBlockAndItemLayer<UUID, EntityManusAvaritia>() {

                            @Override
                            public ItemStack itemStackForBone(AzBone bone, EntityManusAvaritia animatable) {
                                if (bone.getName().equals("TradeJoint")) {
                                    return animatable.getItemFromRoyalHand(EntityManusAvaritia.TRADER_HAND.HAND);
                                }

                                return null;
                            }

                            @Override
                            protected ItemCameraTransforms.TransformType getTransformTypeForStack(
                                    AzBone bone,
                                    ItemStack stack,
                                    EntityManusAvaritia animatable
                            ) {
                                return ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                            }

                            @Override
                            protected void renderItemForBone(
                                    AzRendererPipelineContext<UUID, EntityManusAvaritia> context,
                                    AzBone bone,
                                    ItemStack itemStack,
                                    EntityManusAvaritia animatable
                            ) {
                                context.poseStack().mulPose(Vector3f.XP.rotationDegrees(90));
                                context.poseStack().mulPose(Vector3f.YP.rotationDegrees(0));
                                context.poseStack().mulPose(Vector3f.ZP.rotationDegrees(0f));
                                context.poseStack().translate(0D, -0.1D, 0D);
                                context.poseStack().scale(0.75f, 0.75f, 0.75f);
                                super.renderItemForBone(context, bone, itemStack, animatable);
                            }
                        })
                        .setRenderEntry(contextPipeline -> {

                            contextPipeline.animatable().updateAnimations();
                            return contextPipeline;
                        })
                        .setAnimatorProvider(AnimatorManusAvaritia::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setShadowRadius(0F)
                        .build()
                , renderManager);
    }
}
