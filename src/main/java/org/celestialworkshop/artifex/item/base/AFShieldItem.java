package org.celestialworkshop.artifex.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;

import java.util.Map;
import java.util.function.Supplier;

public class AFShieldItem extends ShieldItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AFShieldItem(AFMaterial material, float speedMod, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemPropertiesSupplier().get().durability(material.getItemTier().getUses()));
        this.material = material;
        this.specialtyMapSupplier = specialtyMapSupplier;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        if (speedMod != 0.0F) {
            attributeBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED_UUID, "Movement Speed modifier", speedMod, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        this.attributeModifiers = attributeBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND ? this.attributeModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

    @Override
    public Map<AFSpecialty, Integer> getSpecialties() {
        return this.specialtyMapSupplier.get();
    }

    public float getShieldDisableMultiplier(Player player) {
        if (AFMaterial.isWeaponType(this, AFWeaponType.BUCKLER)) {
            return 1.5F;
        }
        if (AFMaterial.isWeaponType(this, AFWeaponType.WAR_DOOR)) {
            return 0.15F;
        }
        return 1.0F;
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFMaterial.isWeaponType(this, AFWeaponType.BUCKLER)) {
            return 5.0F;
        }
        return 1.0F;
    }
}
