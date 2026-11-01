package org.celestialworkshop.artifex.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.registry.AFDataComponents;

import java.util.Map;

public class ItemStackUtil {

    public static Map<AFSpecialty, Integer> getSpecialties(Item item) {
        return AFSpecialtiesReloadListener.SPECIALTY_CACHE.computeIfAbsent(item, i -> {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(i);
            return AFSpecialtiesReloadListener.getSpecialtyData(id);
        });
    }

    public static int getSpecialtyLevel(Item item, AFSpecialty specialty) {
        return getSpecialties(item).get(specialty);
    }

    public static boolean hasSpecialty(Item item, AFSpecialty specialty) {
        return getSpecialties(item).containsKey(specialty);
    }

    public static boolean hasSpecialty(ItemStack stack, AFSpecialty specialty) {
        return hasSpecialty(stack.getItem(), specialty);
    }

    public static boolean hasComboBasedWeapon(ItemStack stack) {
        return getSpecialties(stack.getItem()).keySet().stream().anyMatch(ComboBasedSpecialty.class::isInstance);
    }

    public static boolean sameItemMatchesEnchantments(ItemStack stack1, ItemStack stack2) {
        if (stack1.is(stack2.getItem())) {
            return stack1.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                    .equals(stack2.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
        } else {
            return false;
        }
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        int level = 0;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet()) {
            if (entry.getKey().is(key)) {
                level = entry.getIntValue();
            }
        }
        return level;
    }

    public static int getAmmo(ItemStack stack) {
        return stack.getOrDefault(AFDataComponents.AMMO.get(), 16);
    }

    public static int getMaxAmmo(ItemStack stack) {
        if (stack.getItem() instanceof AFThrowableTieredItem throwableItem) {
            return throwableItem.getMaximumAmmo(stack);
        } else {
            return 16;
        }
    }

    public static void setAmmo(ItemStack stack, int count) {
        stack.set(AFDataComponents.AMMO.get(), Math.max(0, count));
    }

    public static void addAmmo(ItemStack stack, int count) {
        setAmmo(stack, Math.min(getAmmo(stack) + count, getMaxAmmo(stack)));
    }

    public static void consumeAmmo(ItemStack stack, int count) {
        setAmmo(stack, Math.max(getAmmo(stack) - count, 0));
    }

    public static boolean isFull(ItemStack stack) {
        return getAmmo(stack) >= getMaxAmmo(stack);
    }

    public static boolean isEmpty(ItemStack stack) {
        return getAmmo(stack) <= 0;
    }

    public static void fillThrowableAmmoCapacity(ItemStack stack) {
        if (stack.getItem() instanceof AFThrowableTieredItem) {
            setAmmo(stack, getMaxAmmo(stack));
        }
    }
}
