package org.celestialworkshop.artifex.item.base;

import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;

import java.util.Map;
import java.util.UUID;

public interface AFPropertyItem {
    UUID BASE_MOVEMENT_SPEED_UUID = UUID.fromString("8e1d0e45-0fcd-4822-9d0e-450fcd78224c");
    UUID BASE_ENTITY_REACH_UUID = UUID.fromString("4f55b5af-5348-4b4f-95b5-af53489b4f8a");

    AFMaterial getMaterial();

    Map<AFSpecialty, Integer> getSpecialties();

    default int getComboTime() {
        return 15;
    }
}
