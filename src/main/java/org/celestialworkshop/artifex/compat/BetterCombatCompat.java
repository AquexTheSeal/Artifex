package org.celestialworkshop.artifex.compat;

import net.minecraftforge.fml.ModList;

public class BetterCombatCompat {

    public static String BETTER_COMBAT_ID = "bettercombat";

    public static boolean isBTCPresent() {
        return ModList.get().isLoaded(BETTER_COMBAT_ID);
    }
}
