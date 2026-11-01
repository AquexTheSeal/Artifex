package org.celestialworkshop.artifex.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.item.BoltItem;

import java.util.List;

public class AFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Artifex.MODID);

    public static final List<AFMaterial> MATERIALS = new ObjectArrayList<>();

    public static final DeferredHolder<Item, Item> CHARRED_STICK = ITEMS.register("charred_stick", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BASIC_HILT = ITEMS.register("basic_hilt", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STANDARD_HILT = ITEMS.register("standard_hilt", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STANDARD_POLE = ITEMS.register("standard_pole", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> BASIC_BOLT = ITEMS.register("basic_bolt", () -> new BoltItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STANDARD_BOLT = ITEMS.register("standard_bolt", () -> new BoltItem(new Item.Properties()));

    public static final AFMaterial WOODEN_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "wooden").tier(Tiers.WOOD)
            .blacklist(AFWeaponType.CROSSBOW, AFWeaponType.BOW, AFWeaponType.LONGBOW, AFWeaponType.ARBALEST, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .build()
    );

    public static final AFMaterial STONE_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "stone").tier(Tiers.STONE)
            .blacklist(AFWeaponType.CROSSBOW, AFWeaponType.BOW, AFWeaponType.LONGBOW, AFWeaponType.ARBALEST, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .build()
    );

    public static final AFMaterial IRON_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "iron").tier(Tiers.IRON)
            .blacklist()
            .build()
    );

    public static final AFMaterial GOLD_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "golden").tier(Tiers.GOLD)
            .blacklist()
            .build()
    );

    public static final AFMaterial DIAMOND_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "diamond").tier(Tiers.DIAMOND)
            .blacklist()
            .build()
    );

    public static final AFMaterial NETHERITE_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "netherite").tier(Tiers.NETHERITE)
            .properties(() -> new Item.Properties().fireResistant())
            .smithingConfig(() -> Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, () -> Ingredient.of(Items.NETHERITE_INGOT), DIAMOND_MATERIAL)
            .blacklist()
            .build()
    );

    public static AFMaterial registerGeneralMaterial(AFMaterial material) {
        MATERIALS.add(material);
        return material;
    }
}
