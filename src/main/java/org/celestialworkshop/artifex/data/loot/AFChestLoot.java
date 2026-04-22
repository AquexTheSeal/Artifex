package org.celestialworkshop.artifex.data.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.function.BiConsumer;

public class AFChestLoot implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {

        pOutput.accept(Artifex.prefix("chests/blacksmith_pool"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SHORTSWORD)).setWeight(10))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BATTLEAXE)).setWeight(5))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SPEAR)).setWeight(7))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.JAVELIN)).setWeight(7))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.DAGGER)).setWeight(10))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BOW)).setWeight(2))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.KNUCKLES)).setWeight(10))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SHIELD)).setWeight(5))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BUCKLER)).setWeight(10))
                )
        );

        pOutput.accept(Artifex.prefix("chests/end_city_pool"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.DAGGER)).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.DAGGER)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.KNUCKLES)).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.KNUCKLES)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.SHORTSWORD)).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SHORTSWORD)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.SICKLE)).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SICKLE)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.BUCKLER)).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BUCKLER)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))

                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.KATANA)).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.KATANA)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.SPEAR)).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SPEAR)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.JAVELIN)).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.JAVELIN)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.GLAIVE)).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.GLAIVE)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.SHIELD)).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SHIELD)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.BOW)).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BOW)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))

                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.GREATSWORD)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.GREATSWORD)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.BATTLEAXE)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.BATTLEAXE)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.FLANGED_MACE)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.FLANGED_MACE)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.ODACHI)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.ODACHI)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.HALBERD)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.HALBERD)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.SCYTHE)).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.SCYTHE)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.LONGBOW)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.LONGBOW)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.CROSSBOW)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.CROSSBOW)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.ARBALEST)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.ARBALEST)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.DIAMOND_MATERIAL.getWeapon(AFWeaponType.WAR_DOOR)).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(AFItems.IRON_MATERIAL.getWeapon(AFWeaponType.WAR_DOOR)).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
                )
        );
    }
}