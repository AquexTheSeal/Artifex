package org.celestialworkshop.artifex.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(
            method = "enchant",
            at = @At("TAIL")
    )
    public void enchant(Holder<Enchantment> pEnchantment, int pLevel, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (pEnchantment.is(AFEnchantments.STOCKPILE)) {
            ItemStackUtil.fillThrowableAmmoCapacity(stack);
        }
    }
}
