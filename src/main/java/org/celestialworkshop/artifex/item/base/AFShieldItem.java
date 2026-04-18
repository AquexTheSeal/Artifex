package org.celestialworkshop.artifex.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AFShieldItem extends ShieldItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AFShieldItem(AFMaterial material, float speedMod, float durabilityMult, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemPropertiesSupplier().get().durability((int) (material.getItemTier().getUses() * durabilityMult)));
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

    @Override
    public boolean allowUseSprinting(Player player) {
        return AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER);
    }

    public void onShieldBlock(LivingEntity entity, ItemStack stack, DamageSource damageSource, float incomingDamage) {
        this.getSpecialties().forEach((specialty, level) -> {
            specialty.onPostShieldBlock(entity, stack, damageSource, incomingDamage, level);
        });

        if (AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER)) {
            if (entity instanceof Player player) {
                int timer = (int) (20 + incomingDamage * 4);
                player.getCooldowns().addCooldown(stack.getItem(), Mth.clamp(timer, 40, 150));
                player.stopUsingItem();
            }
        }
    }

    public float getShieldDisableMultiplier(Player player) {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER)) {
            return 1.5F;
        }
        if (AFWeaponType.isWeaponType(this, AFWeaponType.WAR_DOOR)) {
            return 0.3F;
        }
        return 1.0F;
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER)) {
            return 5.0F;
        }
        if (AFWeaponType.isWeaponType(this, AFWeaponType.WAR_DOOR)) {
            return 0.25F;
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
        if (type == AFWeaponType.BUCKLER) {
            pTooltip.add(Component.translatable("tooltip.artifex.buckler_description").withStyle(ChatFormatting.GRAY));
        } else if (type == AFWeaponType.WAR_DOOR) {
            pTooltip.add(Component.translatable("tooltip.artifex.war_door_description").withStyle(ChatFormatting.GRAY));
        }
    }
}
