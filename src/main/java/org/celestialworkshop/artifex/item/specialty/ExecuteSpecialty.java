package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.registry.AFParticleTypes;
import org.celestialworkshop.artifex.registry.AFSoundEvents;

import javax.annotation.Nullable;

public class ExecuteSpecialty extends AFSpecialty {

    public ExecuteSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        this.triggerExecute(attacker, target, attacker, specialityLevel);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        this.triggerExecute(attacker, target, ammo, specialityLevel);
    }

    public void triggerExecute(LivingEntity attacker, LivingEntity target, @Nullable Entity directEntity, int specialityLevel) {
        float targetHpPercent = target.getHealth() / target.getMaxHealth();
        if (targetHpPercent <= this.calculateHpThreshold(specialityLevel)) {
            if (attacker.getRandom().nextFloat() <= this.calculateChance(specialityLevel)) {
                if (target.getMaxHealth() > this.calculateLimitedHp(specialityLevel)) {
                    DamageSource damageSource = attacker.damageSources().indirectMagic(attacker, directEntity);
                    target.invulnerableTime = 0;
                    target.hurt(damageSource, this.calculateDamageLimit(specialityLevel));
                } else {
                    target.kill();
                }

                if (attacker.level() instanceof ServerLevel server) {
                    server.sendParticles(AFParticleTypes.EXECUTE.get(), target.getX(), target.getY(1.0), target.getZ(), 0, 0, 0, 0, 0);
                    server.playSound(null, target.blockPosition(), AFSoundEvents.EXECUTE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
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
        return 0f + (level * 5f);
    }

    private float calculateLimitedHp(int level) {
        return 100f + ((level - 1) * 20f);
    }
}
