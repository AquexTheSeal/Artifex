package org.celestialworkshop.artifex.item.base;

import net.minecraft.world.item.CrossbowItem;
import org.celestialworkshop.artifex.api.AFMaterial;

public class ExtendedCrossbowItem extends CrossbowItem implements MaterialBasedItem {

    private final AFMaterial material;

    public ExtendedCrossbowItem(AFMaterial material) {
        super(material.getItemProperties());
        this.material = material;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

}
