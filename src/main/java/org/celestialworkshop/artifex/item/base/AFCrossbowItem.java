package org.celestialworkshop.artifex.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.registry.AFSoundEvents;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AFCrossbowItem extends CrossbowItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    public AFCrossbowItem(AFMaterial material, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
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

    public @Nullable SoundEvent getLoadingStartSoundOverride(int enchantmentLevel) {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_START.get();
        }
        return null;
    }

    public @Nullable SoundEvent getLoadingEndSoundOverride() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_LOADED.get();
        }
        return null;
    }

    public @Nullable SoundEvent getLoadingMiddleSoundOverride() {
        return null;
    }

    public @Nullable SoundEvent getShootSoundOverride() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return AFSoundEvents.ARBALEST_SHOOT.get();
        }
        return null;
    }

    public float getChargingSpeedReductionScale(ItemStack crossbowStack) {
        float multiplier;
        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            multiplier = 3.0F - (this.getMaterial().getItemTier().getAttackDamageBonus() * 0.2F);
        } else {
            multiplier = 1.0F - (this.getMaterial().getItemTier().getAttackDamageBonus() * 0.05F);
        }
        return multiplier;
    }

    public boolean playChargeSoundEarlier() {
        return AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST);
    }

    public void modifyArrowProperties(ItemStack crossbowStack, AbstractArrow arrow) {

        AFEntityDataCapability.get(arrow).ifPresent(cap -> {
            cap.setBoundItemStack(crossbowStack.copy());
        });

        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(2.0F));
            arrow.hasImpulse = true;
        }
    }

    @Override
    public int getComboTime() {
        return AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST) ? 80 : 30;
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            return 0.5F;
        }
        return 1.0F;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return this.getMaterial().getItemTier().getRepairIngredient().test(pRepair);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type == AFWeaponType.LONGBOW) {
            pTooltip.add(Component.translatable("tooltip.artifex.arbalest_description").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
