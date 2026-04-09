package org.celestialworkshop.artifex.item.base;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.registry.AFSoundEvents;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class AFCrossbowItem extends CrossbowItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    public AFCrossbowItem(AFMaterial material, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemProperties());
        this.material = material;
        this.specialtyMapSupplier = specialtyMapSupplier;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

    @Override
    public Map<AFSpecialty, Integer> getSpecialties() {
        return this.specialtyMapSupplier.get();
    }

    public @Nullable SoundEvent getLoadingStartSoundOverride(int enchantmentLevel) {
        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_START.get();
        }
        return null;
    }

    public @Nullable SoundEvent getLoadingEndSoundOverride() {
        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_LOADED.get();
        }
        return null;
    }

    public @Nullable SoundEvent getLoadingMiddleSoundOverride() {
        return null;
    }

    public @Nullable SoundEvent getShootSoundOverride() {
        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_SHOOT.get();
        }
        return null;
    }

    public float getChargingSpeedReductionScale(ItemStack crossbowStack) {
        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return 3.0F - (this.getMaterial().getItemTier().getAttackDamageBonus() * 0.2F);
        }
        return 1.0F - (this.getMaterial().getItemTier().getAttackDamageBonus() * 0.05F);
    }

    public boolean playChargeSoundEarlier() {
        return AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST);
    }

    public void modifyArrowProperties(ItemStack crossbowStack, AbstractArrow arrow) {

        AFEntityDataCapability.get(arrow).ifPresent(cap -> {
            cap.setBoundItemStack(crossbowStack.copy());
        });

        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(2.0F));
            arrow.hasImpulse = true;
        }
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFMaterial.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return 0.5F;
        }
        return 1.0F;
    }
}
