package com.unseen.helios.client.item.render;

import com.unseen.helios.client.item.model.ModelManusShield;
import com.unseen.helios.common.items.shield.ManusShield;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderManusShield  extends GeoItemRenderer<ManusShield> {

    public RenderManusShield() {
        super(new ModelManusShield());
    }
}
