package org.celestialworkshop.artifex.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.registry.AFItems;
import org.celestialworkshop.artifex.registry.AFSpecialties;

public class AFLanguageProvider extends LanguageProvider {

    public AFLanguageProvider(PackOutput output, String locale) {
        super(output, Artifex.MODID, locale);
    }

    @Override
    protected void addTranslations() {

        // CREATIVE TABS
        this.add("item_group.artifex.artifex", "Artifex");

        // TOOLTIPS

        this.add("tooltip.artifex.longbow_description", "Has a longer charge time than a regular bow, but arrows shoot faster and deal more damage.");
        this.add("tooltip.artifex.arbalest_description", "Has a longer charge time than a regular crossbow, but arrows shoot faster and deal more damage.");

        this.add("tooltip.artifex.buckler_description", "Using this shield won't slow the player down. Disables for a certain amount of time when taking damage (scales with damage received).");
        this.add("tooltip.artifex.war_door_description", "Using this shield can heavily slow the player down. Disabling cooldown is significantly reduced compared to other shields.");

        this.add("tooltip.artifex.throwable_description", "This weapon can be thrown as a projectile.");
        this.add("tooltip.artifex.throwable_max_throw_time", "Maximum Throwing Time: %ss");
        this.add("tooltip.artifex.throwable_ammo_stack", "Throwable Ammo: %s/%s");

        this.add("tooltip.artifex.specialty", "Weapon Class Specialties");

        // ATTRIBUTES
        this.add("attribute.name.artifex.damage_reduction", "Damage Reduction");

        // SPECIALTIES
        this.addSpecialty(AFSpecialties.EXECUTE.get(), "Execute", "Targets with %s health have a %s chance to be instantly killed (Only %s damage is dealt if the target’s maximum health exceeds %s).");
        this.addSpecialty(AFSpecialties.SWEEPING.get(), "Sweeping", "Sweeping attacks deal extra damage equal to %s of the focused damage.");
        this.addSpecialty(AFSpecialties.IMPACT_COMBO.get(), "Impact Combo", "Successive attacks build momentum, increasing damage with each hit by %s up to a maximum of 5 stacks.");
        this.addSpecialty(AFSpecialties.FINESSE.get(), "Finesse", "Successive attacks build momentum, increasing your attack speed with each hit by %s up to 5 stacks.");
        this.addSpecialty(AFSpecialties.ARMOR_PIERCER.get(), "Armor Piercer", "Deals %s extra damage for every point of armor the target has.");
        this.addSpecialty(AFSpecialties.SHOCKWAVE.get(), "Shockwave", "Critical hits create a shockwave that knocks nearby targets up that deals %s damage.");
        this.addSpecialty(AFSpecialties.CRIPPLING.get(), "Crippling", "Attacks have a %s chance to knock the target back and deal %s damage.");
        this.addSpecialty(AFSpecialties.UNSTOPPABLE.get(), "Unstoppable", "Successive attacks build momentum, increasing your damage reduction with each hit by %s up to 5 stacks.");
        this.addSpecialty(AFSpecialties.BOUNTIFUL_HARVEST.get(), "Bountiful Harvest", "Increases your mob looting level by %s.");
        this.addSpecialty(AFSpecialties.ROGUE.get(), "Rogue", "On impact; If your off-hand holds a matching throwable with the same enchantments, automatically fire another projectile that inherits %s damage.");
        this.addSpecialty(AFSpecialties.IAIJUTSU.get(), "Iaijutsu", "Stay out of combat and hold this item for %s seconds to empower your next strike for %s damage. Standing still or moving slowly triples the timer speed. Cannot crit.");

        this.addSpecialty(AFSpecialties.TWO_HANDED.get(), "Two-Handed", "Hides your off-hand when held; cannot be equipped in the off-hand slot.");
        this.addSpecialty(AFSpecialties.HINDERING.get(), "Hindering", "Successive attacks weakens, reducing your attack speed with each hit by %s up to 5 stacks.");

        // ITEMS
        for (RegistryObject<Item> item : AFItems.ITEMS.getEntries()) {
            if (!(item.get() instanceof BlockItem) && !(item.get() instanceof SmithingTemplateItem)) {
                this.addItem(item, WordUtils.capitalize(item.getId().getPath().replace("_", " ")));
            }
        }

        // ENCHANTMENTS
        for (RegistryObject<Enchantment> enchantment : AFEnchantments.ENCHANTMENTS.getEntries()) {
            this.addEnchantment(enchantment, WordUtils.capitalize(enchantment.getId().getPath().replace("_", " ")));
        }

        this.addEnchantmentDescription(AFEnchantments.PUNCTURE, "Increases damage for thrown projectiles.");
        this.addEnchantmentDescription(AFEnchantments.STOCKPILE, "Increases the throwable weapon's ammo capacity.");

        // ADVANCEMENTS
        this.addAdvancement("artifex_startup", "Artifex", "Explore the wide range of armaments!");

        this.addAdvancement("obtain_weapon_type_knuckles", "Iron Fist", "Obtain a Knuckle.");
        this.addAdvancement("obtain_weapon_type_shortsword", "Short but Sweet", "Obtain a Shortsword.");
        this.addAdvancement("obtain_weapon_type_dagger", "Hidden Blade", "Obtain a Dagger.");
        this.addAdvancement("obtain_weapon_type_sickle", "Harvest Season", "Obtain a Sickle.");
        this.addAdvancement("obtain_weapon_type_battleaxe", "Axe to Grind", "Obtain a Battleaxe.");
        this.addAdvancement("obtain_weapon_type_flanged_mace", "Blunt Force", "Obtain a Flanged Mace.");
        this.addAdvancement("obtain_weapon_type_greatsword", "Big Sword Energy", "Obtain a Greatsword.");
        this.addAdvancement("obtain_weapon_type_katana", "Way of the Blade", "Obtain a Katana.");
        this.addAdvancement("obtain_weapon_type_odachi", "Great Wave", "Obtain an Odachi.");
        this.addAdvancement("obtain_weapon_type_glaive", "Slithery", "Obtain a Glaive.");
        this.addAdvancement("obtain_weapon_type_halberd", "Pole Position", "Obtain a Halberd.");
        this.addAdvancement("obtain_weapon_type_scythe", "Grim Reaper", "Obtain a Scythe.");
        this.addAdvancement("obtain_weapon_type_bow", "Classic Archer", "Obtain a Bow.");
        this.addAdvancement("obtain_weapon_type_longbow", "Long Shot", "Obtain a Longbow.");
        this.addAdvancement("obtain_weapon_type_crossbow", "Cocked and Ready", "Obtain a Crossbow.");
        this.addAdvancement("obtain_weapon_type_arbalest", "Heavy Artillery", "Obtain an Arbalest.");
        this.addAdvancement("obtain_weapon_type_javelin", "Olympian", "Obtain a Javelin.");
        this.addAdvancement("obtain_weapon_type_spear", "Pointy Stick", "Obtain a Spear.");
        this.addAdvancement("obtain_weapon_type_rapier", "En Guarde", "Obtain a Rapier.");
        this.addAdvancement("obtain_weapon_type_scimitar", "Deserted", "Obtain a Scimitar.");
        this.addAdvancement("obtain_weapon_type_buckler", "Light Cover", "Obtain a Buckler.");
        this.addAdvancement("obtain_weapon_type_shield", "Stellar Defense", "Obtain a Shield.");
        this.addAdvancement("obtain_weapon_type_war_door", "Heavy-Duty Fortress", "Obtain a War Door.");
    }

    public void addSpecialty(AFSpecialty specialty, String title, String desc) {
        this.add(specialty.getDisplayNameKey(), title);
        this.add(specialty.getDisplayDescriptionKey(), desc);
    }

    private void addAdvancement(String name, String title, String desc) {
        this.add("advancement.artifex." + name + ".title", title);
        this.add("advancement.artifex." + name + ".description", desc);
    }

    public void addEnchantmentDescription(RegistryObject<Enchantment> enchantment, String desc) {
        String key = String.format("enchantment.%s.%s.desc", enchantment.getId().getNamespace(), enchantment.getId().getPath());
        this.add(key, desc);
    }

}