package org.celestialworkshop.artifex.api;

import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.compat.BetterCombatCompat;
import org.celestialworkshop.artifex.item.base.*;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public enum AFWeaponType {

    KNUCKLES("knuckles", Category.MELEE,
            () -> Map.of(AFSpecialties.IMPACT_COMBO.get(), 2),
            (mat, spec) -> new AFTieredItem(mat, 1.5F, 2.4F, 0.1F, 0.0F, false, spec)),

    SHORTSWORD("shortsword", Category.MELEE,
            () -> Map.of(AFSpecialties.FINESSE.get(), 2, AFSpecialties.EXECUTE.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 2.0F, 2.0F, 0.0F, 0.0F, true, spec)),

    DAGGER("dagger", Category.RANGED_MELEE,
            () -> Map.of(),
            (mat, spec) -> new AFThrowableTieredItem(mat, 1.0F, 2.2F, 0.15F, 0.0F, false, 3, 4.0F, spec)),

    SICKLE("sickle", Category.MELEE,
            () -> Map.of(AFSpecialties.SWEEPING.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 2.0F, 1.8F, 0.1F, 0.0F, true, spec)),

    BATTLEAXE("battleaxe", Category.MELEE,
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1, AFSpecialties.EXECUTE.get(), 2),
            (mat, spec) -> new AFTieredItem(mat, 7.0F, 0.8F, -0.1F, 0.0F, false, spec)),

    FLANGED_MACE("flanged_mace", Category.MELEE,
            () -> Map.of(AFSpecialties.ARMOR_PIERCER.get(), 1, AFSpecialties.SHOCKWAVE.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 5.0F, 1.0F, -0.05F, 0.0F, false, spec)),

    GREATSWORD("greatsword", Category.MELEE,
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 5.0F, 0.85F, -0.1F, 1.0F, true, spec)),

    ARBALEST("arbalest", Category.RANGED,
            () -> Map.of(AFSpecialties.ARMOR_PIERCER.get(), 2, AFSpecialties.TWO_HANDED.get(), 1),
            AFCrossbowItem::new),

    LONGBOW("longbow", Category.RANGED,
            () -> Map.of(),
            AFBowItem::new),

    BOW("bow", Category.RANGED,
            () -> Map.of(),
            AFBowItem::new),

    CROSSBOW("crossbow", Category.RANGED,
            () -> Map.of(),
            AFCrossbowItem::new),

    JAVELIN("javelin", Category.RANGED_MELEE,
            () -> Map.of(),
            (mat, spec) -> new AFThrowableTieredItem(mat, 3.0F, 1.2F, 0.0F, 1.25F, false, 4.5F, 3.0F, spec)),

    SPEAR("spear", Category.RANGED_MELEE,
            () -> Map.of(),
            (mat, spec) -> new AFThrowableTieredItem(mat, 3.0F, 1.1F, 0.0F, 1.25F, false, 5.0F, 2.5F, spec)),

    GLAIVE("glaive", Category.MELEE,
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 3.5F, 1.6F, 0.0F, 1.0F, true, spec)),

    HALBERD("halberd", Category.MELEE,
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 4.0F, 1.2F, 0.0F, 1.25F, true, spec)),

    SCYTHE("scythe", Category.MELEE,
            () -> Map.of(AFSpecialties.SWEEPING.get(), 2),
            (mat, spec) -> new AFTieredItem(mat, 3.0F, 1.4F, 0.0F, 0.5F, true, spec)),

    KATANA("katana", Category.MELEE,
            () -> Map.of(BetterCombatCompat.getKatanaOrOdachiMainSpecialty(), 2),
            (mat, spec) -> new AFTieredItem(mat, 2.5F, 1.75F, 0.05F, 0.0F, true, spec)),

    ODACHI("odachi", Category.MELEE,
            () -> Map.of(BetterCombatCompat.getKatanaOrOdachiMainSpecialty(), 1, AFSpecialties.TWO_HANDED.get(), 1),
            (mat, spec) -> new AFTieredItem(mat, 3.0F, 1.2F, 0.0F, 1.0F, true, spec)),

    BUCKLER("buckler", Category.SHIELD,
            () -> Map.of(),
            (mat, spec) -> new AFShieldItem(mat, 0.0F, 1.25F, spec)),

    SHIELD("shield", Category.SHIELD,
            () -> Map.of(),
            (mat, spec) -> new AFShieldItem(mat, 0.0F, 1.75F, spec)),

    WAR_DOOR("war_door", Category.SHIELD,
            () -> Map.of(),
            (mat, spec) -> new AFShieldItem(mat, -0.15F, 2.5F, spec));


    private final String name;
    private final Category category;
    private final Supplier<Map<AFSpecialty, Integer>> baseSpecialties;
    private final WeaponMaker maker;

    AFWeaponType(String name, Category category, Supplier<Map<AFSpecialty, Integer>> baseSpecialties, WeaponMaker maker) {
        this.name = name;
        this.category = category;
        this.baseSpecialties = baseSpecialties;
        this.maker = maker;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public WeaponMaker getMaker() {
        return maker;
    }

    public Supplier<Map<AFSpecialty, Integer>> mergeSpecialties(MaterialSpecialties materialSpecialties) {
        return () -> {
            Map<AFSpecialty, Integer> merged = new HashMap<>(this.baseSpecialties.get());

            for (MaterialSpecialties.Entry entry : materialSpecialties.getEntries()) {
                boolean categoryMatches = entry.categories().isEmpty() || entry.categories().contains(this.category);
                if (categoryMatches) {
                    merged.merge(entry.specialty().get(), entry.level(), Integer::max);
                }
            }

            return merged;
        };
    }

    public static AFWeaponType byName(String name) {
        for (AFWeaponType type : AFWeaponType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    @FunctionalInterface
    public interface WeaponMaker {
        Item create(AFMaterial material, Supplier<Map<AFSpecialty, Integer>> specialties);
    }

    public enum Category {
        MELEE,
        RANGED,
        RANGED_MELEE,
        SHIELD
    }
}