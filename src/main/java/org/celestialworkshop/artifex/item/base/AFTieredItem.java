package org.celestialworkshop.artifex.item.base;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;

public class AFTieredItem extends TieredItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;
    private final boolean canSweep;

    public AFTieredItem(AFMaterial material, float attackDamage, float attackDamageTierScale, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep) {
        this(material, material.getItemPropertiesSupplier().get(), attackDamage, attackDamageTierScale, attackSpeed, movementSpeedPercent, reach, canSweep);
    }

    protected AFTieredItem(AFMaterial material, Item.Properties properties, float attackDamage, float attackDamageTierScale, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep) {
        super(material.getItemTier(), properties.attributes(createAttributes(material, attackDamage, attackDamageTierScale, attackSpeed, movementSpeedPercent, reach)));
        this.material = material;
        this.canSweep = canSweep;
    }

    private static ItemAttributeModifiers createAttributes(AFMaterial material, float attackDamage, float attackDamageTierScale, float attackSpeed, float movementSpeedPercent, float reach) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        float completeDamage = attackDamage + (material.getItemTier().getAttackDamageBonus() * attackDamageTierScale);
        if (completeDamage != 0.0F) {
            builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, completeDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }

        float completeAttackSpeed = attackSpeed - 4.0F;
        if (attackSpeed != 0.0F) {
            builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, completeAttackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }

        if (movementSpeedPercent != 0.0F) {
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(Artifex.prefix("base_movement_speed"), movementSpeedPercent, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND);
        }

        if (reach != 0.0F) {
            builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Artifex.prefix("base_entity_reach"), reach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }

        return builder.build();
    }

    public boolean canSweep() {
        return canSweep;
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return this.canSweep && ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.SWEEPING_EDGE) && !this.canSweep) {
            return false;
        } else {
            return super.supportsEnchantment(stack, enchantment) || enchantment.value().isSupportedItem(new ItemStack(Items.DIAMOND_SWORD));
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type != null) {
            return switch (type) {
                case BATTLEAXE, FLANGED_MACE, GREATSWORD -> true;
                default -> false;
            };
        } else {
            return false;
        }
    }
}
