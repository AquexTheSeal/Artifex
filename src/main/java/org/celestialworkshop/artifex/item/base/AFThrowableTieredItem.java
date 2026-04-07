package org.celestialworkshop.artifex.item.base;

import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;

import java.util.Map;
import java.util.function.Supplier;

public class AFThrowableTieredItem extends AFTieredItem {

    public AFThrowableTieredItem(AFMaterial material, float attackDamage, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material, attackDamage, attackSpeed, movementSpeedPercent, reach, canSweep, specialtyMapSupplier);
    }


}
