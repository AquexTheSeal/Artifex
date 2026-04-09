package org.celestialworkshop.artifex.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;

import java.util.Map;

public class ItemStackUtil {

    public static boolean sameItemMatchesEnchantments(ItemStack stack1, ItemStack stack2) {
        if (!stack1.is(stack2.getItem())) return false;

        return EnchantmentHelper.getEnchantments(stack1).equals(EnchantmentHelper.getEnchantments(stack2));
    }

    public static boolean hasComboBasedWeapon(ItemStack stack) {
        if (stack.getItem() instanceof AFPropertyItem af) {
            for (Map.Entry<AFSpecialty, Integer> specialty : af.getSpecialties().entrySet()) {
                if (specialty.getKey() instanceof ComboBasedSpecialty) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void fillThrowableAmmoCapacity(ItemStack stack) {
        AFAmmoDataCapability.get(stack).ifPresent(cap -> {
            cap.setAmmo(cap.getMaxAmmo(stack));
        });
    }
}
