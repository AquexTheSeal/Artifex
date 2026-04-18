package org.celestialworkshop.artifex.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AFClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_CREATIVE_TAB_RENDER;

    public static final ForgeConfigSpec.ConfigValue<Boolean> COMBO_OVERLAY_RENDER;
    public static final ForgeConfigSpec.ConfigValue<Integer> COMBO_OVERLAY_X_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> COMBO_OVERLAY_Y_OFFSET;

    public static final ForgeConfigSpec.ConfigValue<Boolean> THROWABLE_OVERLAY_RENDER;
    public static final ForgeConfigSpec.ConfigValue<Integer> THROWABLE_OVERLAY_X_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> THROWABLE_OVERLAY_Y_OFFSET;

    public static final ForgeConfigSpec.ConfigValue<Boolean> IAIJUTSU_OVERLAY_RENDER;
    public static final ForgeConfigSpec.ConfigValue<Integer> IAIJUTSU_OVERLAY_X_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> IAIJUTSU_OVERLAY_Y_OFFSET;


    static {
        BUILDER.push("Artifex Client Settings");

        CUSTOM_CREATIVE_TAB_RENDER = BUILDER.comment("Render custom creative tab icon?")
                .define("customCreativeIcon", true);

        COMBO_OVERLAY_RENDER = BUILDER.comment("Render weapon combo overlay?")
                .define("comboOverlay", true);
        COMBO_OVERLAY_X_OFFSET = BUILDER.comment("Change X-offset of the combo overlay (In pixels)")
                .define("comboOverlayXOffset", 0);
        COMBO_OVERLAY_Y_OFFSET = BUILDER.comment("Change Y-offset of the combo overlay (In pixels)")
                .define("comboOverlayYOffset", 0);

        THROWABLE_OVERLAY_RENDER = BUILDER.comment("Render throwable weapon overlay?")
                .define("throwableOverlay", true);
        THROWABLE_OVERLAY_X_OFFSET = BUILDER.comment("Change X-offset of the throwable weapon overlay (In pixels)")
                .define("throwableOverlayXOffset", 0);
        THROWABLE_OVERLAY_Y_OFFSET = BUILDER.comment("Change Y-offset of the throwable weapon overlay (In pixels)")
                .define("throwableOverlayYOffset", 0);

        IAIJUTSU_OVERLAY_RENDER = BUILDER.comment("Render Iaijutsu overlay?")
                .define("iaijutsuOverlay", true);
        IAIJUTSU_OVERLAY_X_OFFSET = BUILDER.comment("Change X-offset of the Iaijutsu overlay (In pixels)")
                .define("iaijutsuOverlayXOffset", 0);
        IAIJUTSU_OVERLAY_Y_OFFSET = BUILDER.comment("Change Y-offset of the Iaijutsu overlay (In pixels)")
                .define("iaijutsuOverlayYOffset", 0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
