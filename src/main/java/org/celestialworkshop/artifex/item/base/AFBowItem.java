package org.celestialworkshop.artifex.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
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
        float multiplier = 1.0F;
        if (AFWeaponType.isWeaponType(this, AFWeaponType.LONGBOW)) {
            multiplier *= 0.7F;
        }
        return multiplier;
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type == AFWeaponType.LONGBOW) {
            pTooltip.add(Component.translatable("tooltip.artifex.longbow_description").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
