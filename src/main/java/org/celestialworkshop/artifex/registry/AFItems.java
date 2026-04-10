package org.celestialworkshop.artifex.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.List;

public class AFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Artifex.MODID);
    public static final List<AFMaterial> MATERIALS = new ObjectArrayList<>();

    public static final AFMaterial WOODEN_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "wooden").tier(Tiers.WOOD)
            .blacklist(AFWeaponType.CROSSBOW, AFWeaponType.BOW, AFWeaponType.LONGBOW, AFWeaponType.ARBALEST, AFWeaponType.BUCKLER, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .build());
    public static final AFMaterial STONE_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "stone").tier(Tiers.STONE)
            .blacklist(AFWeaponType.CROSSBOW, AFWeaponType.BOW, AFWeaponType.LONGBOW, AFWeaponType.ARBALEST, AFWeaponType.BUCKLER, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .build());
    public static final AFMaterial IRON_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "iron").tier(Tiers.IRON)
            .blacklist()
            .build());
    public static final AFMaterial GOLD_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "golden").tier(Tiers.GOLD)
            .blacklist()
            .build());
    public static final AFMaterial DIAMOND_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "diamond").tier(Tiers.DIAMOND)
            .blacklist(AFWeaponType.BUCKLER, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .build());
    public static final AFMaterial NETHERITE_MATERIAL = registerGeneralMaterial(AFMaterial.builder(ITEMS, "netherite").tier(Tiers.NETHERITE)
            .blacklist(AFWeaponType.BUCKLER, AFWeaponType.SHIELD, AFWeaponType.WAR_DOOR)
            .properties(() -> new Item.Properties().fireResistant())
            .build());

    public static AFMaterial registerGeneralMaterial(AFMaterial material) {
        MATERIALS.add(material);
        return material;
    }
}
