package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ProjectileWeaponItem {

    public CrossbowItemMixin(Properties p_43009_) {
        super(p_43009_);
    }

    @Inject(
            method = "getChargeDuration",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void modifyChargeDuration(ItemStack pCrossbowStack, CallbackInfoReturnable<Integer> cir) {
        if (pCrossbowStack.getItem() instanceof AFCrossbowItem ext) {
            int modified = (int) (cir.getReturnValue() * ext.getChargingSpeedReductionScale(pCrossbowStack));
            cir.setReturnValue(Math.max(1, modified));
        }
    }

    @WrapOperation(
            method = "shootProjectile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private static boolean modifyArrow(Level level, Entity projectile, Operation<Boolean> original, @Local(ordinal = 0, argsOnly = true) ItemStack crossbowStack) {
        boolean flag = original.call(level, projectile);
        if (flag) {
            if (projectile instanceof AbstractArrow arrow) {

                AFEntityDataCapability.get(arrow).ifPresent(cap -> {
                    cap.setBoundItemStack(crossbowStack.copy());
                });

                if (crossbowStack.getItem() instanceof AFCrossbowItem ext) {
                    ext.modifyArrowProperties(crossbowStack, arrow);
                }
            }
        }
        return flag;
    }

    @ModifyExpressionValue(
            method = "onUseTick",
            at = @At(value = "CONSTANT", args = "floatValue=0.2F")
    )
    private float changeLoadingThreshold(float original, @Local(argsOnly = true) ItemStack crossbowStack) {
        if (crossbowStack.getItem() instanceof AFCrossbowItem ext && ext.playChargeSoundEarlier()) {
            return original / 3.0F;
        }
        return original;
    }

    @WrapOperation(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private void overrideLoadingEndSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Operation<Void> original, ItemStack stack) {
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            sound = Optional.ofNullable(ext.getLoadingEndSoundOverride()).orElse(sound);
        }
        original.call(instance, player, x, y, z, sound, source, volume, pitch);
    }

    @WrapOperation(
            method = "shootProjectile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private static void overrideShootSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            sound = Optional.ofNullable(ext.getShootSoundOverride()).orElse(sound);
        }
        original.call(instance, player, x, y, z, sound, source, volume, pitch);
    }

    @WrapOperation(
            method = "onUseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
                    ordinal = 0
            )
    )
    private void overrideLoadingStartSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Operation<Void> original, @Local(argsOnly = true) ItemStack stack, @Local(ordinal = 0, argsOnly = true) int enchantmentLevel) {
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            sound = Optional.ofNullable(ext.getLoadingStartSoundOverride(enchantmentLevel)).orElse(sound);
        }
        original.call(instance, player, x, y, z, sound, source, volume, pitch);
    }

    @WrapOperation(
            method = "onUseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
                    ordinal = 1
            )
    )
    private void overrideLoadingMiddleSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            sound = Optional.ofNullable(ext.getLoadingMiddleSoundOverride()).orElse(sound);
        }
        original.call(instance, player, x, y, z, sound, source, volume, pitch);
    }
}
