package com.unseen.helios.common.world.structures;

import com.unseen.helios.common.world.structures.champion_structure.WorldGenChampionStructure;
import com.unseen.helios.common.world.structures.finger_structure.WorldGenFingerStructure;
import com.unseen.helios.config.WorldConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Our base world Generator, we can use this to generate structures that are common throughout the Dimension
 */
public class ModWorldGenerator implements IWorldGenerator {

    public static final WorldGenFingerStructure finger = new WorldGenFingerStructure();
    public static final WorldGenChampionStructure championStructure = new WorldGenChampionStructure();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        BlockPos pos = new BlockPos(x + 8, 0, z + 8);

        if(isAllowedDimensionTooSpawnIn(world.provider.getDimension())) {
            finger.generate(world, random, pos);
            championStructure.generate(world, random, pos);
        }
    }


    public static boolean isAllowedDimensionTooSpawnIn(int dimensionIn) {
        for(int i : WorldConfig.list_of_dimensions) {
            if(i == dimensionIn)
                return true;
        }

        return false;
    }


}
