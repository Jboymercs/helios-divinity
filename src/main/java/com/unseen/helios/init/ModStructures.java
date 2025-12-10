package com.unseen.helios.init;

import com.unseen.helios.common.world.structures.champion_structure.ChampionStructure;
import com.unseen.helios.common.world.structures.champion_structure.ChampionStructureTemplate;
import com.unseen.helios.common.world.structures.champion_structure.WorldGenChampionStructure;
import com.unseen.helios.common.world.structures.finger_structure.FingerStructureTemplate;
import com.unseen.helios.common.world.structures.finger_structure.WorldGenFingerStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;

/**
 * Used for Registering Structures that utilize templates
 */
public class ModStructures {

    public static void handleStructureRegistries(){
        //Finger Structure
        MapGenStructureIO.registerStructure(WorldGenFingerStructure.Start.class, "ManusStructure");
        MapGenStructureIO.registerStructureComponent(FingerStructureTemplate.class, "FSP");
        //Champion Structure
        MapGenStructureIO.registerStructure(WorldGenChampionStructure.Start.class, "ChampionStructure");
        MapGenStructureIO.registerStructureComponent(ChampionStructureTemplate.class, "CSP");
    }
}
