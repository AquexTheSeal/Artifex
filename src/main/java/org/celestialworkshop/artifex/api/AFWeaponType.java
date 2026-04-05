package org.celestialworkshop.artifex.api;

import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.item.base.ExtendedBowItem;
import org.celestialworkshop.artifex.item.base.ExtendedCrossbowItem;
import org.celestialworkshop.artifex.item.base.ExtendedTieredItem;

public enum AFWeaponType {
    KNUCKLES("knuckles", mat -> new ExtendedTieredItem(mat, 1.5F, 2.4F, 0.1F, 0.0F, false)),
    SHORTSWORD("shortsword", mat -> new ExtendedTieredItem(mat, 2.0F, 2.0F, 0.0F, -0.20F, true)),
    DAGGER("dagger", mat -> new ExtendedTieredItem(mat, 1.0F, 2.2F, 0.15F, -0.25F, false)),
    SICKLE("sickle", mat -> new ExtendedTieredItem(mat, 2.0F, 1.8F, 0.1F, 0.0F, true)),

    BATTLEAXE("battleaxe", mat -> new ExtendedTieredItem(mat, 7.0F, 0.65F, -0.1F, 0.0F, false)),
    FLANGED_MACE("flanged_mace", mat -> new ExtendedTieredItem(mat, 5.0F, 1.0F, -0.05F, 0.0F, false)),
    GREATSWORD("greatsword", mat -> new ExtendedTieredItem(mat, 5.0F, 0.85F, -0.1F, 1.5F, true)),
    ARBALEST("arbalest", mat -> new ExtendedCrossbowItem(mat)),
    LONGBOW("longbow", mat -> new ExtendedBowItem(mat)),

    BOW("bow", mat -> new ExtendedBowItem(mat)),
    CROSSBOW("crossbow", mat -> new ExtendedCrossbowItem(mat)),
    JAVELIN("javelin", mat -> new ExtendedTieredItem(mat, 3.0F, 1.2F, 0.0F, 0.75F, false)),
    SPEAR("spear", mat -> new ExtendedTieredItem(mat, 3.0F, 1.1F, 0.0F, 1.0F, false)),

    GLAIVE("glaive", mat -> new ExtendedTieredItem(mat, 3.5F, 1.6F, 0.0F, 1.0F, true)),
    HALBERD("halberd", mat -> new ExtendedTieredItem(mat, 4.0F, 1.2F, 0.0F, 1.0F, true)),
    SCYTHE("scythe", mat -> new ExtendedTieredItem(mat, 3.0F, 1.4F, 0.0F, 0.5F, true)),
    KATANA("katana", mat -> new ExtendedTieredItem(mat, 2.5F, 1.75F, 0.05F, 0.25F, true)),
    ODACHI("odachi", mat -> new ExtendedTieredItem(mat, 3.0F, 1.5F, 0.0F, 0.50F, true));

    private final String name;
    private final WeaponMaker maker;

    AFWeaponType(String name, WeaponMaker maker) {
        this.name = name;
        this.maker = maker;
    }

    public String getName() {
        return name;
    }

    public WeaponMaker getMaker() {
        return maker;
    }

    @FunctionalInterface
    public interface WeaponMaker {
        Item create(AFMaterial material);
    }
}
