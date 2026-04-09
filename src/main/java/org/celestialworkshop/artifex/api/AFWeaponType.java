package org.celestialworkshop.artifex.api;

import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.item.base.AFBowItem;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.item.base.AFTieredItem;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import java.util.Map;

public enum AFWeaponType {
    KNUCKLES("knuckles", mat -> new AFTieredItem(mat, 1.5F, 2.4F, 0.1F, 0.0F, false, () -> Map.of(
            AFSpecialties.IMPACT_COMBO.get(), 2
    ))),

    SHORTSWORD("shortsword", mat -> new AFTieredItem(mat, 2.0F, 2.0F, 0.0F, -0.20F, true, () -> Map.of(
            AFSpecialties.FINESSE.get(), 1,
            AFSpecialties.IMPACT_COMBO.get(), 1,
            AFSpecialties.EXECUTE.get(), 1
    ))),

    DAGGER("dagger", mat -> new AFThrowableTieredItem(mat, 1.0F, 2.2F, 0.15F, -0.25F, false, 3, 4.0F, () -> Map.of(
    ))),

    SICKLE("sickle", mat -> new AFTieredItem(mat, 2.0F, 1.8F, 0.1F, 0.0F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 1
    ))),

    BATTLEAXE("battleaxe", mat -> new AFTieredItem(mat, 7.0F, 0.65F, -0.1F, 0.0F, false, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 1,
            AFSpecialties.EXECUTE.get(), 2
    ))),

    FLANGED_MACE("flanged_mace", mat -> new AFTieredItem(mat, 5.0F, 1.0F, -0.05F, 0.0F, false, () -> Map.of(
            AFSpecialties.ARMOR_PIERCER.get(), 1,
            AFSpecialties.SHOCKWAVE.get(), 1
    ))),

    GREATSWORD("greatsword", mat -> new AFTieredItem(mat, 5.0F, 0.85F, -0.1F, 0.5F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 1
    ))),

    ARBALEST("arbalest", mat -> new AFCrossbowItem(mat, () -> Map.of(
            AFSpecialties.ARMOR_PIERCER.get(), 2,
            AFSpecialties.TWO_HANDED.get(), 1
    ))),

    LONGBOW("longbow", mat -> new AFBowItem(mat, () -> Map.of(
    ))),

    BOW("bow", mat -> new AFBowItem(mat, () -> Map.of(
    ))),

    CROSSBOW("crossbow", mat -> new AFCrossbowItem(mat, () -> Map.of(
    ))),

    JAVELIN("javelin", mat -> new AFThrowableTieredItem(mat, 3.0F, 1.2F, 0.0F, 0.75F, false, 4.5F, 3.0F, () -> Map.of(
    ))),

    SPEAR("spear", mat -> new AFThrowableTieredItem(mat, 3.0F, 1.1F, 0.0F, 1.0F, false, 5.0F, 2.5F, () -> Map.of(
    ))),

    GLAIVE("glaive", mat -> new AFTieredItem(mat, 3.5F, 1.6F, 0.0F, 1.0F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 1
    ))),

    HALBERD("halberd", mat -> new AFTieredItem(mat, 4.0F, 1.2F, 0.0F, 1.2F, true, () -> Map.of(
            AFSpecialties.TWO_HANDED.get(), 1
    ))),

    SCYTHE("scythe", mat -> new AFTieredItem(mat, 3.0F, 1.4F, 0.0F, 0.5F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 2
    ))),

    KATANA("katana", mat -> new AFTieredItem(mat, 2.5F, 1.75F, 0.05F, 0.25F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 1
    ))),

    ODACHI("odachi", mat -> new AFTieredItem(mat, 3.0F, 1.5F, 0.0F, 0.50F, true, () -> Map.of(
            AFSpecialties.SWEEPING.get(), 2,
            AFSpecialties.TWO_HANDED.get(), 1
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