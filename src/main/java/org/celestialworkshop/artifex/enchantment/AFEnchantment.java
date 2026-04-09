package org.celestialworkshop.artifex.enchantment;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;

import java.util.function.BiFunction;

public class AFEnchantment extends Enchantment {

    public static final EnchantmentCategory THROWABLE = EnchantmentCategory.create("throwable_weapon", item -> item instanceof AFThrowableTieredItem);

    public final int maxLevel;
    public final Int2IntFunction minCostFunction;
    public final BiFunction<Integer, Integer, Integer> maxCostFunction;

    public AFEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, int maxLevel, Int2IntFunction minCostFunction, BiFunction<Integer, Integer, Integer> maxCostFunction) {
        super(pRarity, pCategory, pApplicableSlots);
        this.maxLevel = maxLevel;
        this.minCostFunction = minCostFunction;
        this.maxCostFunction = maxCostFunction;
    }

    public AFEnchantment(Rarity pRarity, EnchantmentCategory pCategory, int maxLevel, Int2IntFunction minCostFunction, BiFunction<Integer, Integer, Integer> maxCostFunction) {
        this(pRarity, pCategory, new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND}, maxLevel, minCostFunction, maxCostFunction);
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getMinCost(int pLevel) {
        return this.minCostFunction.applyAsInt(pLevel);
    }

    @Override
    public int getMaxCost(int pLevel) {
        return this.maxCostFunction.apply(pLevel, minCostFunction.applyAsInt(pLevel));
    }
}
