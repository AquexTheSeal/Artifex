package org.celestialworkshop.artifex.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;

public class ItemStackUtil {

    public static boolean hasSpecialty(Item item, AFSpecialty specialty) {
        return item instanceof AFPropertyItem artifexItem && artifexItem.getSpecialties().containsKey(specialty);
    }

    public static boolean hasSpecialty(ItemStack stack, AFSpecialty specialty) {
        return hasSpecialty(stack.getItem(), specialty);
    }

    public static boolean sameItemMatchesEnchantments(ItemStack stack1, ItemStack stack2) {
        if (!stack1.is(stack2.getItem())) return false;

        return EnchantmentHelper.getEnchantments(stack1).equals(EnchantmentHelper.getEnchantments(stack2));
    }

    public static boolean hasComboBasedWeapon(ItemStack stack) {
        return stack.getItem() instanceof AFPropertyItem af && af.getSpecialties().keySet().stream().anyMatch(ComboBasedSpecialty.class::isInstance);
    }

    public static void fillThrowableAmmoCapacity(ItemStack stack) {
        AFAmmoDataCapability.get(stack).ifPresent(cap -> {
            cap.setAmmo(cap.getMaxAmmo(stack));
        });
    }
}
