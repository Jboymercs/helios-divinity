package com.unseen.helios.client.item.render;

import com.unseen.helios.client.item.model.ModelGreatFinger;
import com.unseen.helios.common.items.shield.ManusShield;
import com.unseen.helios.common.items.tools.ItemGreatFinger;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderGreatFinger  extends GeoItemRenderer<ItemGreatFinger> {
    public RenderGreatFinger() {
        super(new ModelGreatFinger());
    }
}
