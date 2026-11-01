package org.celestialworkshop.artifex.compat;

import net.neoforged.fml.ModList;

public class BetterCombatCompat {

    public static boolean isBTCPresent() {
        return ModList.get().isLoaded("bettercombat");
    }
}
