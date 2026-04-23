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
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFSoundEvents;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AFCrossbowItem extends CrossbowItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;

    public AFCrossbowItem(AFMaterial material) {
        super(material.getItemPropertiesSupplier().get().durability(material.getItemTier().getUses()));
        this.material = material;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
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

        if (AFWeaponType.isWeaponType(this, AFWeaponType.ARBALEST)) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(2.0F));
            arrow.hasImpulse = true;
        }
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
