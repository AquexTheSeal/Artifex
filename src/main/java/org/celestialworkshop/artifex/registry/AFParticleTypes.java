package org.celestialworkshop.artifex.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.celestialworkshop.artifex.Artifex;

public class AFParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, Artifex.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXECUTE = PARTICLE_TYPES.register("execute", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHOCKWAVE = PARTICLE_TYPES.register("shockwave", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IAIJUTSU = PARTICLE_TYPES.register("iaijutsu", () -> new SimpleParticleType(false));
}
