package org.celestialworkshop.artifex.util.itemextension;

import net.minecraft.world.entity.player.Player;

public interface AFExtension {

    default float getItemUsingSlowdownMultiplier() {
        return 1.0F;
    }

    default boolean allowUseSprinting(Player player) {
        return false;
    }
}
