package org.celestialworkshop.artifex.item.base;

import net.minecraft.world.item.ShieldItem;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;

import java.util.Map;
import java.util.function.Supplier;

public class AFShieldItem extends ShieldItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    public AFShieldItem(AFMaterial material, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemProperties());
        this.material = material;
        this.specialtyMapSupplier = specialtyMapSupplier;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

    @Override
    public Map<AFSpecialty, Integer> getSpecialties() {
        return this.specialtyMapSupplier.get();
    }
}
