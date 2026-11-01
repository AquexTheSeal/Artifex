package org.celestialworkshop.artifex.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AFCommonConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("Artifex Common Settings");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
