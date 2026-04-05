package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import org.celestialworkshop.artifex.Artifex;

public class AFGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public AFGlobalLootModifierProvider(PackOutput output) {
        super(output, Artifex.MODID);
    }

    @Override
    protected void start() {
    }

}