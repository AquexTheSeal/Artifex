package org.celestialworkshop.artifex.api;

import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.item.base.AFBowItem;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.celestialworkshop.artifex.item.base.AFTieredItem;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import java.util.Map;

public enum AFWeaponType {
    KNUCKLES("knuckles", mat -> new AFTieredItem(mat, 1.5F, 2.4F, 0.1F, 0.0F, false, () -> Map.of(
            AFSpecialties.IMPACT_COMBO.get(), 0
    ))),

    SHORTSWORD("shortsword", mat -> new AFTieredItem(mat, 2.0F, 2.0F, 0.0F, -0.20F, true, () -> Map.of(
            AFSpecialties.FINESSE.get(), 0,
            AFSpecialties.EXECUTE.get(), 0
    ))),

    DAGGER("dagger", mat -> new AFTieredItem(mat, 1.0F, 2.2F, 0.15F, -0.25F, false, () -> Map.of(
            AFSpecialties.THROWABLE.get(), 0
    ))),

    SICKLE("sickle", mat -> new AFTieredItem(mat, 2.0F, 1.8F, 0.1F, 0.0F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 0
    ))),

    BATTLEAXE("battleaxe", mat -> new AFTieredItem(mat, 7.0F, 0.65F, -0.1F, 0.0F, false, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 0,
            AFSpecialties.EXECUTE.get(), 1
    ))),

    FLANGED_MACE("flanged_mace", mat -> new AFTieredItem(mat, 5.0F, 1.0F, -0.05F, 0.0F, false, () -> Map.of(
            AFSpecialties.ARMOR_PIERCER.get(), 0,
            AFSpecialties.SHOCKWAVE.get(), 0
    ))),

    GREATSWORD("greatsword", mat -> new AFTieredItem(mat, 5.0F, 0.85F, -0.1F, 1.5F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 0
    ))),

    ARBALEST("arbalest", mat -> new AFCrossbowItem(mat, () -> Map.of(
            AFSpecialties.ARMOR_PIERCER.get(), 1,
            AFSpecialties.TWO_HANDED.get(), 1
    ))),

    LONGBOW("longbow", mat -> new AFBowItem(mat, () -> Map.of(
            AFSpecialties.EXECUTE.get(), 15
    ))),

    BOW("bow", mat -> new AFBowItem(mat, () -> Map.of(
            AFSpecialties.EXECUTE.get(), 15
    ))),

    CROSSBOW("crossbow", mat -> new AFCrossbowItem(mat, () -> Map.of(

    ))),

    JAVELIN("javelin", mat -> new AFTieredItem(mat, 3.0F, 1.2F, 0.0F, 0.75F, false, () -> Map.of(
            AFSpecialties.THROWABLE.get(), 0
    ))),

    SPEAR("spear", mat -> new AFTieredItem(mat, 3.0F, 1.1F, 0.0F, 1.0F, false, () -> Map.of(
            AFSpecialties.THROWABLE.get(), 0
    ))),

    GLAIVE("glaive", mat -> new AFTieredItem(mat, 3.5F, 1.6F, 0.0F, 1.0F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 0
    ))),

    HALBERD("halberd", mat -> new AFTieredItem(mat, 4.0F, 1.2F, 0.0F, 1.2F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 0
    ))),

    SCYTHE("scythe", mat -> new AFTieredItem(mat, 3.0F, 1.4F, 0.0F, 0.5F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 1
    ))),

    KATANA("katana", mat -> new AFTieredItem(mat, 2.5F, 1.75F, 0.05F, 0.25F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 0
    ))),

    ODACHI("odachi", mat -> new AFTieredItem(mat, 3.0F, 1.5F, 0.0F, 0.50F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 0,
            AFSpecialties.SWEEPING.get(), 1
    )));

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