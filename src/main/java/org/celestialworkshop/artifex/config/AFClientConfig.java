package org.celestialworkshop.artifex.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AFClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

     public static final ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_CREATIVE_TAB_RENDER;
    public static final ForgeConfigSpec.ConfigValue<Boolean> COMBO_OVERLAY_RENDER;

    static {
        BUILDER.push("Artifex Client Settings");

         CUSTOM_CREATIVE_TAB_RENDER = BUILDER.comment("Render custom creative tab icon?")
                 .define("customCreativeIcon", true);

        COMBO_OVERLAY_RENDER = BUILDER.comment("Render weapon combo overlay?")
                .define("comboOverlay", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
