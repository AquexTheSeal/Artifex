package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.registry.AFItems;
import org.celestialworkshop.artifex.registry.AFSpecialties;

public class AFLanguageProvider extends LanguageProvider {

    public AFLanguageProvider(PackOutput output, String locale) {
        super(output, Artifex.MODID, locale);
    }

    @Override
    protected void addTranslations() {

        this.add("tooltip.artifex.specialty", "Weapon Class Specialties");

        this.addSpecialty(AFSpecialties.EXECUTE.get(), "Execute", "Targets with %s health have a %s chance to be instantly killed (Only %s damage is dealt if the target’s maximum health exceeds %s).");
        this.addSpecialty(AFSpecialties.SWEEPING.get(), "Sweeping", "Sweeping attacks deal extra damage equal to %s of the focused damage.");
        this.addSpecialty(AFSpecialties.IMPACT_COMBO.get(), "Impact Combo", "Successive attacks build momentum, increasing damage with each hit by %s up to a maximum of %s.");
        this.addSpecialty(AFSpecialties.FINESSE.get(), "Finesse", "Successive attacks build momentum, increasing your attack speed with each hit by %s up to %s.");
        this.addSpecialty(AFSpecialties.ARMOR_PIERCER.get(), "Armor Piercer", "Deals bonus damage based on the target's protection, scaled at a %s:%s ratio of armor to power.");
        this.addSpecialty(AFSpecialties.SHOCKWAVE.get(), "Shockwave", "Critical hits create a shockwave that knocks nearby targets up that deals %s damage.");

        this.addSpecialty(AFSpecialties.THROWABLE.get(), "Throwable", "This weapon can be thrown as a projectile.");

        this.addSpecialty(AFSpecialties.TWO_HANDED.get(), "Two-Handed", "Hides your off-hand when held; cannot be equipped in the off-hand slot.");

        for (RegistryObject<Item> item : AFItems.ITEMS.getEntries()) {
            if (!(item.get() instanceof BlockItem) && !(item.get() instanceof SmithingTemplateItem)) {
                this.addItem(item, WordUtils.capitalize(item.getId().getPath().replace("_", " ")));
            }
        }
    }

    public void addSpecialty(AFSpecialty specialty, String title, String desc) {
        this.add(specialty.getDisplayNameKey(), title);
        this.add(specialty.getDisplayDescriptionKey(), desc);
    }

    private void addAdvancement(String name, String title, String desc) {
        this.add("advancement.behemoths." + name + ".title", title);
        this.add("advancement.behemoths." + name + ".description", desc);
    }

}