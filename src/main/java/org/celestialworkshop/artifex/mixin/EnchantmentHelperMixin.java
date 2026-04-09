package org.celestialworkshop.artifex.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(
            method = "setEnchantments",
            at = @At("TAIL")
    )
    private static void setEnchantments(Map<Enchantment, Integer> pEnchantmentsMap, ItemStack pStack, CallbackInfo ci) {
        if (pEnchantmentsMap.containsKey(AFEnchantments.STOCKPILE.get())) {
            ItemStackUtil.fillThrowableAmmoCapacity(pStack);
        }
    }
}
