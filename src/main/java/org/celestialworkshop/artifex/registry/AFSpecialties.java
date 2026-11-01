package org.celestialworkshop.artifex.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.item.specialty.*;

public class AFSpecialties {
    public static final ResourceKey<Registry<AFSpecialty>> SPECIALTIES_KEY = ResourceKey.createRegistryKey(Artifex.prefix("specialties"));
    public static final DeferredRegister<AFSpecialty> SPECIALTIES = DeferredRegister.create(SPECIALTIES_KEY, Artifex.MODID);
    public static final Registry<AFSpecialty> REGISTRY = SPECIALTIES.makeRegistry(builder -> {});

    public static final DeferredHolder<AFSpecialty, AFSpecialty> EXECUTE = SPECIALTIES.register("execute", () -> new ExecuteSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> SWEEPING = SPECIALTIES.register("sweeping", () -> new SweepingSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> IMPACT_COMBO = SPECIALTIES.register("impact_combo", () -> new ImpactComboSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> FINESSE = SPECIALTIES.register("finesse", () -> new FinesseSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> ARMOR_PIERCER = SPECIALTIES.register("armor_piercer", () -> new ArmorPiercerSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> SHOCKWAVE = SPECIALTIES.register("shockwave", () -> new ShockwaveSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> CRIPPLING = SPECIALTIES.register("crippling", () -> new CripplingSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> UNSTOPPABLE = SPECIALTIES.register("unstoppable", () -> new UnstoppableSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> BOUNTIFUL_HARVEST = SPECIALTIES.register("bountiful_harvest", () -> new LevelArgOnly(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> ROGUE = SPECIALTIES.register("rogue", () -> new RogueSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> IAIJUTSU = SPECIALTIES.register("iaijutsu", () -> new IaijutsuSpecialty(AFSpecialty.Category.BENEFICIAL));

    public static final DeferredHolder<AFSpecialty, AFSpecialty> TWO_HANDED = SPECIALTIES.register("two_handed", () -> new AFSpecialty(AFSpecialty.Category.HARMFUL));
    public static final DeferredHolder<AFSpecialty, AFSpecialty> HINDERING = SPECIALTIES.register("hindering", () -> new HinderingSpecialty(AFSpecialty.Category.HARMFUL));
}
