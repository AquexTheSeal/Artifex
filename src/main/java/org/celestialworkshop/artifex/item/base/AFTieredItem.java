package org.celestialworkshop.artifex.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class AFTieredItem extends TieredItem implements ArtifexItemProperties {

    private final AFMaterial material;
    private final Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final boolean canSweep;

    // TODO: CHANGE HOW SPECIALTIES ARE INITIALIZED
    public AFTieredItem(AFMaterial material, float attackDamage, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material.getItemTier(), material.getItemProperties());
        this.material = material;
        this.canSweep = canSweep;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        float completeDamage = attackDamage + this.getTier().getAttackDamageBonus();
        if (completeDamage != 0.0F) {
            attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Attack Damage modifier", completeDamage, AttributeModifier.Operation.ADDITION));
        }
        float completeAttackSpeed = attackSpeed - 4.0F;
        if (attackSpeed != 0.0F) {
            attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Attack Speed modifier", completeAttackSpeed, AttributeModifier.Operation.ADDITION));
        }
        if (movementSpeedPercent != 0.0F) {
            attributeBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED_UUID, "Movement Speed modifier", movementSpeedPercent, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        if (reach != 0.0F) {
            attributeBuilder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Entity Reach modifier", reach, AttributeModifier.Operation.ADDITION));
        }
        
        this.attributeModifiers = attributeBuilder.build();
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

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return this.canSweep && ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SWEEPING_EDGE && !this.canSweep) return false;
        return enchantment.category == EnchantmentCategory.WEAPON;
    }
}
