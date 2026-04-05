package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.celestialworkshop.artifex.Artifex;

public class AFLanguageProvider extends LanguageProvider {

    public AFLanguageProvider(PackOutput output, String locale) {
        super(output, Artifex.MODID, locale);
    }

    @Override
    protected void addTranslations() {
    }

    private void addAdvancement(String name, String title, String desc) {
        this.add("advancement.behemoths." + name + ".title", title);
        this.add("advancement.behemoths." + name + ".description", desc);
    }

}