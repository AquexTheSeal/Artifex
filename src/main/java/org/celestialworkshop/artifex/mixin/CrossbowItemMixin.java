package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
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
    private static void modifyChargeDuration(ItemStack pCrossbowStack, LivingEntity pEntity, CallbackInfoReturnable<Integer> cir) {
        if (pCrossbowStack.getItem() instanceof AFCrossbowItem ext) {
            int modified = (int) (cir.getReturnValue() * ext.getChargingSpeedReductionScale(pCrossbowStack));
            cir.setReturnValue(Math.max(1, modified));
        }
    }

    @ModifyExpressionValue(
            method = "onUseTick",
            at = @At(value = "CONSTANT", args = "floatValue=0.2F")
    )
    private float changeLoadingThreshold(float original, @Local(argsOnly = true) ItemStack crossbowStack) {
        if (crossbowStack.getItem() instanceof AFCrossbowItem ext && ext.playChargeSoundEarlier()) {
            return original / 3.0F;
        } else {
            return original;
        }
    }

    @Inject(method = "getChargingSounds", at = @At("RETURN"), cancellable = true)
    private void overrideLoadingSounds(ItemStack stack, CallbackInfoReturnable<CrossbowItem.ChargingSounds> cir) {
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            CrossbowItem.ChargingSounds sounds = cir.getReturnValue();
            SoundEvent start = ext.getLoadingStartSoundOverride(0);
            SoundEvent end = ext.getLoadingEndSoundOverride();
            SoundEvent mid = ext.getLoadingMiddleSoundOverride();
            cir.setReturnValue(new CrossbowItem.ChargingSounds(
                    start != null ? Optional.of(Holder.direct(start)) : sounds.start(),
                    mid != null ? Optional.of(Holder.direct(mid)) : sounds.mid(),
                    end != null ? Optional.of(Holder.direct(end)) : sounds.end()
            ));
        }
    }

    @WrapOperation(
            method = "shootProjectile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private static void overrideShootSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) LivingEntity shooter) {
        ItemStack stack = shooter.getMainHandItem();
        if (!(stack.getItem() instanceof AFCrossbowItem)) {
            stack = shooter.getOffhandItem();
        }
        if (stack.getItem() instanceof AFCrossbowItem ext) {
            sound = Optional.ofNullable(ext.getShootSoundOverride()).orElse(sound);
        }
        original.call(instance, player, x, y, z, sound, source, volume, pitch);
    }
}
