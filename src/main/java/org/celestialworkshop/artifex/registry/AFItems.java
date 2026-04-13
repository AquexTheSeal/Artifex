package org.celestialworkshop.artifex.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.List;

public class AFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Artifex.MODID);

    // Datagen iteration purposes.
    public static final List<AFMaterial> MATERIALS = new ObjectArrayList<>();

    public static final RegistryObject<Item> BASIC_HILT = ITEMS.register("basic_hilt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REINFORCED_HILT = ITEMS.register("reinforced_hilt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POLE = ITEMS.register("pole", () -> new Item(new Item.Properties()));

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
            .smithingConfig(() -> Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_INGOT, DIAMOND_MATERIAL)
            .blacklist()
            .build()
    );

    public static AFMaterial registerGeneralMaterial(AFMaterial material) {
        MATERIALS.add(material);
        return material;
    }
}
