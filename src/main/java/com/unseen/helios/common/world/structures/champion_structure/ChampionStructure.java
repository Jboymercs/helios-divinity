package com.unseen.helios.common.world.structures.champion_structure;

import com.unseen.helios.common.world.structures.finger_structure.FingerStructureTemplate;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;

public class ChampionStructure {

    private List<StructureComponent> components;
    private World world;
    private TemplateManager manager;

    public ChampionStructure(World worldIn, TemplateManager template, List<StructureComponent> components) {
        this.world = worldIn;
        this.manager = template;
        this.components = components;
    }

    public void startBuilding(BlockPos pos, Rotation rot, String name) {
        ChampionStructureTemplate template = new ChampionStructureTemplate(manager, name, pos.add(-15, -28, -6), rot, 0, true);
        components.add(template);
        System.out.println("Generated Champion's Arena At" + pos);
    }
}
