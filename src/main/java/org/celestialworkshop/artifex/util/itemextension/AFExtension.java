package org.celestialworkshop.artifex.util.itemextension;

public interface AFExtension {

    /**
     * Total movement speed will be 0.2 * (this value), so if you want to revert to basic movement, return 5.0F.
     */
    default float getItemUsingSlowdownMultiplier() {
        return 1.0F;
    }
}
