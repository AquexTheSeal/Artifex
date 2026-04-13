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
        this.handheldItem(AFItems.CHARRED_STICK.get());
        this.handheldItem(AFItems.BASIC_HILT.get());
        this.handheldItem(AFItems.STANDARD_HILT.get());
        this.basicParentedItem(AFItems.STANDARD_POLE.get(), "artifex:item/handheld_long_middle");
    }
}
