package org.celestialworkshop.artifex.item.base;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
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

public class AFBowItem extends BowItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    public AFBowItem(AFMaterial material, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemPropertiesSupplier().get().durability(material.getItemTier().getUses()));
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

    public @Nullable SoundEvent getShootSoundOverride() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.LONGBOW)) {
            return AFSoundEvents.LONGBOW_SHOOT.get();
        }
        return null;
    }

    public float getDrawSpeedMultiplier() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.LONGBOW)) {
            return 0.7F;
        }
        return 1.0F;
    }

    public void modifyArrowProperties(ItemStack bowStack, AbstractArrow original) {
        AFEntityDataCapability.get(original).ifPresent(cap -> {
            cap.setBoundItemStack(bowStack.copy());
        });

        if (AFWeaponType.isWeaponType(this, AFWeaponType.LONGBOW)) {
            original.setDeltaMovement(original.getDeltaMovement().scale(1.8F));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return this.getMaterial().getItemTier().getRepairIngredient().test(pRepair);
    }

    @Override
    public int getComboTime() {
        return AFWeaponType.isWeaponType(this, AFWeaponType.LONGBOW) ? 60 : 30;
    }
}
