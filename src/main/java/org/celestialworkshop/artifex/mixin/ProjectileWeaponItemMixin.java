package org.celestialworkshop.artifex.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {

    @Inject(method = "getHeldProjectile", at = @At(value = "HEAD"), cancellable = true)
    private static void compensateHeldProjectilesForTwoHanded(LivingEntity pShooter, Predicate<ItemStack> pIsAmmo, CallbackInfoReturnable<ItemStack> cir) {
        if (pShooter instanceof Player player) {
            ItemStack offhandStack = player.getInventory().offhand.get(0);
            if (pIsAmmo.test(offhandStack)) {
                cir.setReturnValue(offhandStack);
            }
        }
    }
}
