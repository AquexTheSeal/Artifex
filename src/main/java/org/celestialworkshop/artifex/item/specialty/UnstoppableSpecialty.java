package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.registry.AFAttributes;

import java.util.UUID;

public class UnstoppableSpecialty extends ComboBasedSpecialty {
    private static final UUID DAMAGE_REDUCTION_MODIFIER_UUID = UUID.fromString("6d986b24-7427-463d-82c1-d3a907106096");

    public UnstoppableSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        this.manageResistanceStacking(attacker, specialityLevel);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        this.manageResistanceStacking(attacker, specialityLevel);
    }

    public void manageResistanceStacking(LivingEntity attacker, int specialityLevel) {
        AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();
        AttributeInstance damageReduction = attacker.getAttribute(AFAttributes.DAMAGE_REDUCTION.get());
        if (damageReduction != null) {
            damageReduction.removeModifier(DAMAGE_REDUCTION_MODIFIER_UUID);
            double reductionAmount = entityData.comboCount * getReductionIncrement(specialityLevel);
            AttributeModifier modifier = new AttributeModifier(DAMAGE_REDUCTION_MODIFIER_UUID, "unstoppable_damage_reduction", reductionAmount, AttributeModifier.Operation.ADDITION);
            damageReduction.addTransientModifier(modifier);
        }
    }

    @Override
    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {
        AttributeInstance damageReduction = attacker.getAttribute(AFAttributes.DAMAGE_REDUCTION.get());
        if (damageReduction != null) {
            damageReduction.removeModifier(DAMAGE_REDUCTION_MODIFIER_UUID);
        }
    }

    private float getReductionIncrement(int specialityLevel) {
        return specialityLevel * 0.1F;
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(getReductionIncrement(level))
        };
    }
}
