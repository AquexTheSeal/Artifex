package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.datagen.material.MaterialItemModelProvider;
import org.celestialworkshop.artifex.registry.AFItems;

public class AFItemModelProvider extends MaterialItemModelProvider {
    public AFItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, Artifex.MODID, AFItems.MATERIALS);
    }

    @Override
    protected void registerModels() {
        super.registerModels();
        this.handheldItem(AFItems.BASIC_HILT.get());
        this.handheldItem(AFItems.REINFORCED_HILT.get());
        this.basicParentedItem(AFItems.POLE.get(), "artifex:item/handheld_long_middle");
    }
}
