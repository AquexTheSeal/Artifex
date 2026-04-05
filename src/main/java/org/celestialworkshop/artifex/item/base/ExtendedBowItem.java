package org.celestialworkshop.artifex.item.base;

import net.minecraft.world.item.BowItem;
import org.celestialworkshop.artifex.api.AFMaterial;

public class ExtendedBowItem extends BowItem implements MaterialBasedItem {

    private final AFMaterial material;

    public ExtendedBowItem(AFMaterial material) {
        super(material.getItemProperties());
        this.material = material;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

}
