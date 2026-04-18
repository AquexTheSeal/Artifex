package org.celestialworkshop.artifex.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;

public class AFParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Artifex.MODID);

    public static final RegistryObject<SimpleParticleType> EXECUTE = PARTICLE_TYPES.register("execute", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHOCKWAVE = PARTICLE_TYPES.register("shockwave", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IAIJUTSU = PARTICLE_TYPES.register("iaijutsu", () -> new SimpleParticleType(false));
}
