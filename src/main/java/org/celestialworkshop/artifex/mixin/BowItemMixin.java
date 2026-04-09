package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.item.base.AFBowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(BowItem.class)
public abstract class BowItemMixin extends ProjectileWeaponItem {

    public BowItemMixin(Properties p_43009_) {
        super(p_43009_);
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/event/ForgeEventFactory;onArrowLoose(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;IZ)I"
            ),
            index = 3
    )
    private int modifyUsingTicks(int i, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof AFBowItem ext) {
            return Mth.floor(i * ext.getDrawSpeedMultiplier());
        }
        return i;
    }

    @WrapOperation(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean modifyArrow(Level level, Entity projectile, Operation<Boolean> original, @Local(argsOnly = true) ItemStack bowStack) {
        boolean flag = original.call(level, projectile);
        if (flag && bowStack.getItem() instanceof AFBowItem ext) {
            if (projectile instanceof AbstractArrow arrow) {
                ext.modifyArrowProperties(bowStack, arrow);
            }
        }
        return flag;
    }

    @WrapOperation(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private void modifyReleaseShootingSound(Level instance, Player pPlayer, double pX, double pY, double pZ, SoundEvent pSound, SoundSource pCategory, float pVolume, float pPitch, Operation<Void> original, @Local(argsOnly = true) ItemStack bowStack) {
        if (bowStack.getItem() instanceof AFBowItem ext) {
            pSound = Optional.ofNullable(ext.getShootSoundOverride()).orElse(pSound);
        }
        original.call(instance, pPlayer, pX, pY, pZ, pSound, pCategory, pVolume, pPitch);
    }
}
