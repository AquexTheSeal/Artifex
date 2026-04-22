package org.celestialworkshop.artifex.api;

import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.compat.BetterCombatCompat;
import org.celestialworkshop.artifex.item.base.*;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public enum AFWeaponType {

    KNUCKLES("knuckles", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 1.25F, 2.4F, 0.1F, 0.0F, false),
            () -> Map.of(AFSpecialties.IMPACT_COMBO.get(), 2),
            " X ", "X X"),

    SHORTSWORD("shortsword", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 2.0F, 2.0F, 0.0F, 0.0F, true),
            () -> Map.of(AFSpecialties.FINESSE.get(), 1, AFSpecialties.EXECUTE.get(), 1),
            "X", "S"),

    SICKLE("sickle", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 2.0F, 2.2F, 0.1F, 0.0F, true),
            () -> Map.of(BetterCombatCompat.getSweepingOrSubstitute(), 1, AFSpecialties.BOUNTIFUL_HARVEST.get(), 2),
            "XX", " R"),

    KATANA("katana", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 2.0F, 1.5F, 0.05F, 0.0F, true),
            () -> Map.of(AFSpecialties.IAIJUTSU.get(), 2, AFSpecialties.TWO_HANDED.get(), 1),
            "  X", " X ", " R "),

    BATTLEAXE("battleaxe", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 7.0F, 0.75F, -0.1F, 0.0F, false),
            () -> Map.of(AFSpecialties.EXECUTE.get(), 2),
            "XXX", "XRX", " R "),

    FLANGED_MACE("flanged_mace", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 5.0F, 1.0F, -0.05F, 0.0F, false),
            () -> Map.of(AFSpecialties.ARMOR_PIERCER.get(), 1, AFSpecialties.SHOCKWAVE.get(), 1),
            "XXX", " R ", " R "),

    SCIMITAR("scimitar", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 3.0F, 1.2F, 0.0F, 0.0F, true),
            () -> Map.of(AFSpecialties.IMPACT_COMBO.get(), 1, AFSpecialties.FINESSE.get(), 1),
            " XX", " X ", "R  "),

    RAPIER("rapier", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 1.0F, 2.2F, 0.05F, 0.0F, false),
            () -> Map.of(AFSpecialties.FINESSE.get(), 2, AFSpecialties.ARMOR_PIERCER.get(), 1),
            "  X", " X ", "R  "),

    DAGGER("dagger", Category.RANGED_MELEE,
            (mat) -> new AFThrowableTieredItem(mat, 1.0F, 2.2F, 0.05F, 0.0F, false, 10, 3, 4.0F),
            () -> Map.of(AFSpecialties.ROGUE.get(), 1),
            " X", "S "),

    JAVELIN("javelin", Category.RANGED_MELEE,
            (mat) -> new AFThrowableTieredItem(mat, 3.0F, 1.2F, 0.0F, 1.25F, false, 20, 6.0F, 5.0F),
            () -> Map.of(AFSpecialties.ARMOR_PIERCER.get(), 1),
            "X", "P", "X"),

    SPEAR("spear", Category.RANGED_MELEE,
            (mat) -> new AFThrowableTieredItem(mat, 1.5F, 1.0F, 0.0F, 1.5F, false, 15, 4.5F, 2.5F),
            () -> Map.of(AFSpecialties.CRIPPLING.get(), 1),
            "X", "P"),

    BOW("bow", Category.RANGED,
            AFBowItem::new,
            () -> Map.of(),
            "RX", "XB"),

    LONGBOW("longbow", Category.RANGED,
            AFBowItem::new,
            () -> Map.of(),
            "XXT", "XB ", "T  "),

    CROSSBOW("crossbow", Category.RANGED,
            AFCrossbowItem::new,
            () -> Map.of(),
            " X ", "XCX", " S "),

    ARBALEST("arbalest", Category.RANGED,
            AFCrossbowItem::new,
            () -> Map.of(AFSpecialties.ARMOR_PIERCER.get(), 2, AFSpecialties.TWO_HANDED.get(), 1),
            "XCX", "XTX", " R "),

    GLAIVE("glaive", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 3.0F, 0.9F, 0.0F, 0.5F, true),
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1, AFSpecialties.FINESSE.get(), 3),
            " X ", "XP "),

    HALBERD("halberd", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 4.0F, 1.0F, 0.0F, 1.25F, true),
            () -> Map.of(AFSpecialties.CRIPPLING.get(), 1, AFSpecialties.TWO_HANDED.get(), 1),
            "XX", "XP"),

    SCYTHE("scythe", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 3.0F, 1.4F, 0.0F, 1.0F, true),
            () -> Map.of(BetterCombatCompat.getSweepingOrSubstitute(), 2, AFSpecialties.BOUNTIFUL_HARVEST.get(), 1),
            "XX", " P"),

    GREATSWORD("greatsword", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 6.0F, 1.0F, -0.1F, 1.0F, true),
            () -> Map.of(AFSpecialties.TWO_HANDED.get(), 1, AFSpecialties.UNSTOPPABLE.get(), 1, AFSpecialties.HINDERING.get(), 1),
            " X ", " X ", " R "),

    ODACHI("odachi", Category.MELEE,
            (mat) -> new AFTieredItem(mat, 4.5F, 1.2F, 0.0F, 1.0F, true),
            () -> Map.of(AFSpecialties.IAIJUTSU.get(), 1, AFSpecialties.TWO_HANDED.get(), 1),
            "XX", "X ", "R "),

    BUCKLER("buckler", Category.SHIELD,
            (mat) -> new AFShieldItem(mat, 0.0F, 1.25F),
            () -> Map.of(),
            " X ", "XRX", " X "),

    SHIELD("shield", Category.SHIELD,
            (mat) -> new AFShieldItem(mat, 0.0F, 1.75F),
            () -> Map.of(),
            "XXX", "XIX", " X "),

    WAR_DOOR("war_door", Category.SHIELD,
            (mat) -> new AFShieldItem(mat, -0.15F, 2.5F),
            () -> Map.of(),
            "XXX", "XHX", "XXX");


    private final String name;
    private final Category category;
    private final WeaponMaker maker;
    private final Supplier<Map<AFSpecialty, Integer>> weaponTypeSpecialties;

    private String[] recipePattern = new String[]{"X", "S", "S"};

    AFWeaponType(String name, Category category, WeaponMaker maker, Supplier<Map<AFSpecialty, Integer>> weaponTypeSpecialties) {
        this.name = name;
        this.category = category;
        this.maker = maker;
        this.weaponTypeSpecialties = weaponTypeSpecialties;
    }

    AFWeaponType(String name, Category category, WeaponMaker maker, Supplier<Map<AFSpecialty, Integer>> weaponTypeSpecialties, String... recipePattern) {
        this(name, category, maker, weaponTypeSpecialties);
        this.recipePattern = recipePattern;
    }

    public String[] getRecipePattern() {
        return recipePattern;
    }

    public static boolean isWeaponType(Item item, AFWeaponType weaponType) {
        return getWeaponType(item) == weaponType;
    }

    public static @Nullable AFWeaponType getWeaponType(Item item) {
        return item != null ? AFMaterial.ITEM_TO_WEAPON_TYPE.get(item) : null;
    }

    public static @Nullable Category getWeaponCategory(Item item) {
        return getWeaponType(item) != null ? getWeaponType(item).getCategory() : null;
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

    public Supplier<Map<AFSpecialty, Integer>> getWeaponTypeSpecialties() {
        return weaponTypeSpecialties;
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
        Item create(AFMaterial material);
    }

    public enum Category {
        MELEE,
        RANGED,
        RANGED_MELEE,
        SHIELD
    }
}