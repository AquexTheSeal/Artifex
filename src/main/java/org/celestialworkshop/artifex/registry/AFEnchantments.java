package org.celestialworkshop.artifex.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.celestialworkshop.artifex.AFTags;
import org.celestialworkshop.artifex.Artifex;

public class AFEnchantments {

    public static final ResourceKey<Enchantment> PUNCTURE = ResourceKey.create(Registries.ENCHANTMENT, Artifex.prefix("puncture"));
    public static final ResourceKey<Enchantment> STOCKPILE = ResourceKey.create(Registries.ENCHANTMENT, Artifex.prefix("stockpile"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        context.register(PUNCTURE, Enchantment.enchantment(
                Enchantment.definition(
                        items.getOrThrow(AFTags.THROWABLE_ENCHANTABLE),
                        10,
                        5,
                        Enchantment.dynamicCost(11, 10),
                        Enchantment.dynamicCost(31, 10),
                        1,
                        EquipmentSlotGroup.MAINHAND,
                        EquipmentSlotGroup.OFFHAND
                )
        ).build(PUNCTURE.location()));

        context.register(STOCKPILE, Enchantment.enchantment(
                Enchantment.definition(
                        items.getOrThrow(AFTags.THROWABLE_ENCHANTABLE),
                        10,
                        2,
                        Enchantment.dynamicCost(17, 15),
                        Enchantment.dynamicCost(47, 15),
                        1,
                        EquipmentSlotGroup.MAINHAND,
                        EquipmentSlotGroup.OFFHAND
                )
        ).build(STOCKPILE.location()));
    }
}
