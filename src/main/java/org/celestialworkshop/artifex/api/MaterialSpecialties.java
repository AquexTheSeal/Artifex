package org.celestialworkshop.artifex.api;

import java.util.*;
import java.util.function.Supplier;

public class MaterialSpecialties {

    record Entry(Supplier<? extends AFSpecialty> specialty, int level, Set<AFWeaponType.Category> categories) {}

    private final List<Entry> entries = new ArrayList<>();

    public MaterialSpecialties add(Supplier<? extends AFSpecialty> specialty, int level) {
        entries.add(new Entry(specialty, level, EnumSet.noneOf(AFWeaponType.Category.class)));
        return this;
    }

    public MaterialSpecialties add(Supplier<? extends AFSpecialty> specialty, int level, AFWeaponType.Category first, AFWeaponType.Category... rest) {
        entries.add(new Entry(specialty, level, EnumSet.of(first, rest)));
        return this;
    }

    List<Entry> getEntries() {
        return entries;
    }
}