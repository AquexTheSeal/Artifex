package org.celestialworkshop.artifex.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AFCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Artifex Common Settings");



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
