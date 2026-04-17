package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;

public class CripplingSpecialty extends AFSpecialty {
    public CripplingSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        return this.applyCrippling(attacker, target, originalDamage, specialityLevel);
    }

    @Override
    public float onDamageRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, float originalDamage, boolean wasCrit, int specialityLevel) {
        return this.applyCrippling(ammo, target, originalDamage, specialityLevel);
    }

    private float applyCrippling(Entity direct, LivingEntity target, float damage, int level) {
        if (target.getRandom().nextFloat() < getCripplingChance(level)) {
            target.knockback(1.5F, direct.getX() - target.getX(), direct.getZ() - target.getZ());
            target.hasImpulse = true;
            target.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0f, 1.5f);
            return damage * getDamageMultiplier(level);
        }
        return damage;
    }

    private float getCripplingChance(int level) {
        return Math.min(1.0f, 0.2F + 0.05f * level);
    }

    private static float getDamageMultiplier(int level) {
        return 1.25f + (0.25f * level);
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(getCripplingChance(level)),
                asPercentFormat(getDamageMultiplier(level))
        };
    }
}
