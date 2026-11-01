package org.celestialworkshop.artifex.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(
            method = "setEnchantments",
            at = @At("TAIL")
    )
    private static void setEnchantments(ItemStack pStack, ItemEnchantments pEnchantments, CallbackInfo ci) {
        if (ItemStackUtil.getEnchantmentLevel(pStack, AFEnchantments.STOCKPILE) > 0) {
            ItemStackUtil.fillThrowableAmmoCapacity(pStack);
        }
    }
}
