package org.celestialworkshop.artifex.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFItemStackDataCapability;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;

import java.util.Map;

public class ItemStackUtil {

    // SPECIALTY MANAGEMENT
    public static Map<AFSpecialty, Integer> getSpecialties(Item item) {
        return AFSpecialtiesReloadListener.SPECIALTY_CACHE.computeIfAbsent(item, i -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(i);
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

    // ITEM MATCHING
    public static boolean sameItemMatchesEnchantments(ItemStack stack1, ItemStack stack2) {
        if (!stack1.is(stack2.getItem())) return false;

        return EnchantmentHelper.getEnchantments(stack1).equals(EnchantmentHelper.getEnchantments(stack2));
    }

    // THROWABLE AMMO MANAGEMENT
    public static void fillThrowableAmmoCapacity(ItemStack stack) {
        AFItemStackDataCapability.get(stack).ifPresent(cap -> {
            cap.setAmmo(cap.getMaxAmmo(stack));
        });
    }
}
