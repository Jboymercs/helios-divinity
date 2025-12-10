package com.unseen.helios.common.world.structures.finger_structure;

import com.unseen.helios.common.world.structures.base.ModStructureTemplate;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class FingerStructureTemplate extends ModStructureTemplate {

    public FingerStructureTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rot, int distance, boolean overWriteIn) {
        super(manager, type, pos,distance, rot, overWriteIn);
    }

    public FingerStructureTemplate() {

    }

    @Override
    public String templateLocation() {
        return "finger";
    }
}
