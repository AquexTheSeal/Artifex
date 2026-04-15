package org.celestialworkshop.artifex.item.specialty;

import org.celestialworkshop.artifex.api.AFSpecialty;

public class LevelArgOnly extends AFSpecialty {

    public LevelArgOnly(Category category) {
        super(category);
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{level};
    }
}
