package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;

import javax.annotation.Nullable;

public class ExecuteSpecialty extends AFSpecialty {

    public ExecuteSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        this.triggerExecute(attacker, target, null, specialityLevel);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        this.triggerExecute(attacker, target, ammo, specialityLevel);
    }

    public void triggerExecute(LivingEntity attacker, LivingEntity target, @Nullable Entity directEntity, int specialityLevel) {
        float targetHpPercent = target.getHealth() / target.getMaxHealth();
        if (targetHpPercent <= this.calculateHpThreshold(specialityLevel)) {
            if (attacker.getRandom().nextFloat() <= this.calculateChance(specialityLevel)) {
                DamageSource damageSource = attacker.damageSources().indirectMagic(attacker, directEntity);
                if (target.getMaxHealth() > this.calculateLimitedHp(specialityLevel)) {
                    target.hurt(damageSource, this.calculateDamageLimit(specialityLevel));
                } else {
                    target.hurt(damageSource, Float.MAX_VALUE);
                }
            }
        }
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(calculateHpThreshold(level)),
                asPercentFormat(calculateChance(level)),
                (int) calculateDamageLimit(level),
                (int) calculateLimitedHp(level)
        };
    }

    private float calculateHpThreshold(int level) {
        return Math.min(1.0f, 0.15f + (level * 0.05f));
    }

    private float calculateChance(int level) {
        return Math.min(1.0f, 0.4f + (level * 0.1f));
    }

    private float calculateDamageLimit(int level) {
        return 5f + (level * 5f);
    }

    private float calculateLimitedHp(int level) {
        return 90f + (level * 10f);
    }
}
