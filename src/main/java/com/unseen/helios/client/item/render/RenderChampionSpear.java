package com.unseen.helios.client.item.render;

import com.unseen.helios.client.item.model.ModelChampionSpear;
import com.unseen.helios.common.items.tools.ItemChampionSpear;
import com.unseen.helios.common.items.tools.ItemGreatFinger;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderChampionSpear   extends GeoItemRenderer<ItemChampionSpear> {
    public RenderChampionSpear() {
        super(new ModelChampionSpear());
    }
}
