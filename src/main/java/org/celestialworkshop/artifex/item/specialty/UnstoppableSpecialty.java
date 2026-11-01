package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
import org.celestialworkshop.artifex.registry.AFAttributes;

public class UnstoppableSpecialty extends ComboBasedSpecialty {

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
        AFEntityData entityData = attacker.getData(AFAttachmentTypes.ENTITY_DATA);
        AttributeInstance damageReduction = attacker.getAttribute(AFAttributes.DAMAGE_REDUCTION);
        if (damageReduction != null) {
            damageReduction.removeModifier(Artifex.prefix("unstoppable_damage_reduction"));
            double reductionAmount = entityData.comboCount * getReductionIncrement(specialityLevel);
            AttributeModifier modifier = new AttributeModifier(Artifex.prefix("unstoppable_damage_reduction"), reductionAmount, AttributeModifier.Operation.ADD_VALUE);
            damageReduction.addTransientModifier(modifier);
        }
    }

    @Override
    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {
        AttributeInstance damageReduction = attacker.getAttribute(AFAttributes.DAMAGE_REDUCTION);
        if (damageReduction != null) {
            damageReduction.removeModifier(Artifex.prefix("unstoppable_damage_reduction"));
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
