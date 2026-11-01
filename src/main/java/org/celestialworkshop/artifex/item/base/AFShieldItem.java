package org.celestialworkshop.artifex.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;

import java.util.List;

public class AFShieldItem extends ShieldItem implements AFPropertyItem, AFExtension {

    private final AFMaterial material;

    public AFShieldItem(AFMaterial material, float speedMod, float durabilityMult) {
        super(material.getItemPropertiesSupplier().get()
                .durability((int) (material.getItemTier().getUses() * durabilityMult))
                .attributes(createAttributes(speedMod))
        );
        this.material = material;
    }

    private static ItemAttributeModifiers createAttributes(float speedMod) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        if (speedMod != 0.0F) {
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(Artifex.prefix("base_movement_speed"), speedMod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HAND);
        }
        return builder.build();
    }

    @Override
    public AFMaterial getMaterial() {
        return material;
    }

    @Override
    public boolean allowUseSprinting(Player player) {
        return AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER);
    }

    public void onShieldBlock(LivingEntity entity, ItemStack stack, DamageSource damageSource, float incomingDamage) {
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
        } else if (AFWeaponType.isWeaponType(this, AFWeaponType.WAR_DOOR)) {
            return 0.3F;
        } else {
            return 1.0F;
        }
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.BUCKLER)) {
            return 5.0F;
        } else if (AFWeaponType.isWeaponType(this, AFWeaponType.WAR_DOOR)) {
            return 0.25F;
        } else {
            return 1.0F;
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return this.getMaterial().getItemTier().getRepairIngredient().test(pRepair);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type == AFWeaponType.BUCKLER) {
            pTooltip.add(Component.translatable("tooltip.artifex.buckler_description").withStyle(ChatFormatting.GRAY));
        } else if (type == AFWeaponType.WAR_DOOR) {
            pTooltip.add(Component.translatable("tooltip.artifex.war_door_description").withStyle(ChatFormatting.GRAY));
        }
    }
}
