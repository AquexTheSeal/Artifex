package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;
import org.celestialworkshop.artifex.registry.AFParticleTypes;
import org.celestialworkshop.artifex.registry.AFSoundEvents;

import java.util.concurrent.atomic.AtomicReference;

public class IaijutsuSpecialty extends AFSpecialty {

    public IaijutsuSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        AtomicReference<Float> result = new AtomicReference<>(originalDamage);
        if (attacker instanceof ServerPlayer player) {
            AFEntityDataCapability.get(attacker).ifPresent(cap -> {
                if (cap.iaijutsuTimer <= 0) {
                    result.updateAndGet(original -> original * this.getDamageMultiplier(specialityLevel));

                    player.serverLevel().sendParticles(AFParticleTypes.IAIJUTSU.get(), target.getX(), target.getY(0.5), target.getZ(), 0, 0, 0, 0, 0);
                    player.serverLevel().playSound(null, target.blockPosition(), AFSoundEvents.IAIJUTSU.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                }
                cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
                AFNetwork.sendToPlayer(player, new S2CSyncIaijutsuPacket(cap.iaijutsuItemStack, cap.iaijutsuTimer, cap.iaijutsuSpeedUp));
            });
        }
        return result.get();
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                calculateTime(level) / 20,
                asPercentFormat(getDamageMultiplier(level))
        };
    }

    public int calculateTime(int level) {
        return 300 - ((level - 1) * 100);
    }

    public float getDamageMultiplier(int level) {
        return 3.0F + ((level - 1) * 0.5F);
    }
}
