package org.celestialworkshop.artifex.compat;

import net.minecraftforge.fml.ModList;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.registry.AFSpecialties;

public class BetterCombatCompat {

    public static String BETTER_COMBAT_ID = "bettercombat";

    public static boolean isBTCPresent() {
        return ModList.get().isLoaded(BETTER_COMBAT_ID);
    }

    public static AFSpecialty getKatanaOrOdachiMainSpecialty() {
        return isBTCPresent() ? AFSpecialties.FINESSE.get() : AFSpecialties.SWEEPING.get();
    }
}
