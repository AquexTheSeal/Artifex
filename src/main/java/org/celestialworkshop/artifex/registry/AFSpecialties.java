package org.celestialworkshop.artifex.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.item.specialty.ExecuteSpecialty;
import org.celestialworkshop.artifex.item.specialty.SweepingSpecialty;

import java.util.function.Supplier;

public class AFSpecialties {
    public static final ResourceKey<Registry<AFSpecialty>> SPECIALTIES_KEY = ResourceKey.createRegistryKey(Artifex.prefix("specialties"));
    public static final DeferredRegister<AFSpecialty> SPECIALTIES = DeferredRegister.create(SPECIALTIES_KEY, Artifex.MODID);
    public static final Supplier<IForgeRegistry<AFSpecialty>> REGISTRY = SPECIALTIES.makeRegistry(() -> new RegistryBuilder<AFSpecialty>().disableOverrides());

    public static final RegistryObject<AFSpecialty> EXECUTE = SPECIALTIES.register("execute", () -> new ExecuteSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> SWEEPING = SPECIALTIES.register("sweeping", () -> new SweepingSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> IMPACT_COMBO = SPECIALTIES.register("impact_combo", () -> new AFSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> FINESSE = SPECIALTIES.register("finesse", () -> new AFSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> ARMOR_PIERCER = SPECIALTIES.register("armor_piercer", () -> new AFSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> SHOCKWAVE = SPECIALTIES.register("shockwave", () -> new AFSpecialty(AFSpecialty.Category.BENEFICIAL));

    public static final RegistryObject<AFSpecialty> TWO_HANDED = SPECIALTIES.register("two_handed", () -> new AFSpecialty(AFSpecialty.Category.HARMFUL));

}
