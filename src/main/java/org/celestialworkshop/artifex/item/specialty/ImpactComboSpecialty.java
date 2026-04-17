package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;

public class ImpactComboSpecialty extends ComboBasedSpecialty {
    public ImpactComboSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        if (attacker instanceof Player pl) {
            if (pl.getAttackStrengthScale(0) > 0.9) {
                target.invulnerableTime = 0;
            }
        }
        return this.manageDamage(attacker, target, originalDamage, specialityLevel);
    }

    @Override
    public float onDamageRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, float originalDamage, boolean wasCrit, int specialityLevel) {
        return this.manageDamage(attacker, target, originalDamage, specialityLevel);
    }

    public float manageDamage(LivingEntity attacker, LivingEntity target, float originalDamage, int specialityLevel) {
        AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();
        return originalDamage + (entityData.comboCount * originalDamage * getDamageIncrement(specialityLevel));
    }

    private float getDamageIncrement(int specialityLevel) {
        return specialityLevel * 0.05F;
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(getDamageIncrement(level))
        };
    }
}
