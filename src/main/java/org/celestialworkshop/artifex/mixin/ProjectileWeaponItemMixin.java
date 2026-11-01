package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.item.base.AFBowItem;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
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

    @WrapOperation(
            method = "shoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean modifyArrow(ServerLevel level, Entity projectile, Operation<Boolean> original, @Local(argsOnly = true) ItemStack weaponStack) {
        boolean flag = original.call(level, projectile);
        if (flag && projectile instanceof AbstractArrow arrow) {
            AFEntityData cap = arrow.getData(AFAttachmentTypes.ENTITY_DATA);
            cap.setBoundItemStack(weaponStack.copy());

            if (weaponStack.getItem() instanceof AFBowItem bowExt) {
                bowExt.modifyArrowProperties(weaponStack, arrow);
            } else if (weaponStack.getItem() instanceof AFCrossbowItem crossbowExt) {
                crossbowExt.modifyArrowProperties(weaponStack, arrow);
            }
        }
        return flag;
    }
}
