package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.registry.AFParticleTypes;
import org.celestialworkshop.artifex.registry.AFSoundEvents;

import java.util.List;

public class ShockwaveSpecialty extends AFSpecialty {
    public ShockwaveSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        if (wasCrit) {
            this.triggerShockwave(attacker, target, originalDamage * calculateShockwaveDamage(specialityLevel));
        }
        return originalDamage;
    }

    @Override
    public float onDamageRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, float originalDamage, boolean wasCrit, int specialityLevel) {
        this.triggerShockwave(attacker, target, originalDamage * calculateShockwaveDamage(specialityLevel));
        return originalDamage;
    }

    private void triggerShockwave(LivingEntity attacker, LivingEntity target, float damage) {
        AABB area = target.getBoundingBox().inflate(2.5);
        List<Entity> entities = target.level().getEntities(attacker, area, entity -> entity instanceof LivingEntity && entity != target && entity != attacker);

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living && (!(target instanceof Player) || !(entity instanceof Player))) {
                DamageSource source = attacker instanceof Player pl ? attacker.damageSources().playerAttack(pl) : attacker.damageSources().mobAttack(attacker);
                living.hurt(source, damage);
                living.push(0, 0.4, 0);
            }
        }

        if (attacker.level() instanceof ServerLevel server) {
            server.sendParticles(AFParticleTypes.SHOCKWAVE.get(), target.getX(), target.getY() + 0.2, target.getZ(), 0, 0, 0, 0, 0);
            server.playSound(null, target.blockPosition(), AFSoundEvents.SHOCKWAVE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(calculateShockwaveDamage(level))
        };
    }

    private float calculateShockwaveDamage(int level) {
        return 0.3F + (level * 0.1F);
    }
}
