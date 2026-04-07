package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;

public class SweepingSpecialty extends AFSpecialty {
    public SweepingSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageSweep(LivingEntity attacker, LivingEntity sweepTarget, ItemStack itemStack, float sweepDamage, float originalDamage, int specialityLevel) {
        return sweepDamage + (originalDamage * this.calculateSweepDamagePercent(specialityLevel));
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(calculateSweepDamagePercent(level))
        };
    }

    private float calculateSweepDamagePercent(int level) {
        return 0.3f + (level * 0.1f);
    }
}
