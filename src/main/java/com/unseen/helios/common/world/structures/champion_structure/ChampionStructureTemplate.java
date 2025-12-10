package com.unseen.helios.common.world.structures.champion_structure;

import com.unseen.helios.common.entity.entities.boss.EntityNamelessChampion;
import com.unseen.helios.common.world.structures.base.ModStructureTemplate;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class ChampionStructureTemplate extends ModStructureTemplate {

    public ChampionStructureTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rot, int distance, boolean overWriteIn) {
        super(manager, type, pos,distance, rot, overWriteIn);
    }

    public ChampionStructureTemplate() {

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox sbb) {
        //adds the boss to the structure
        if(function.startsWith("boss")) {
            EntityNamelessChampion champion = new EntityNamelessChampion(world);
            champion.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.spawnEntity(champion);
            world.setBlockToAir(pos);
        }
    }


    @Override
    public String templateLocation() {
        return "champion";
    }
}
