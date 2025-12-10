package com.unseen.helios.init;

import com.unseen.helios.common.world.dimension.crypt.DimensionCrypt;
import net.minecraft.world.DimensionType;

/**
 * where registration of dimensions will occur
 */
public class ModDimensions {

    public static final DimensionType CRYPT_DIMENSION = DimensionType.register("the_crypt", "_the_crypt", 80, DimensionCrypt.class, false);

    public static void registerDimensions() {
     //   DimensionManager.registerDimension(80, CRYPT_DIMENSION);
    }
}
