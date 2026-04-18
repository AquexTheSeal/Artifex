package org.celestialworkshop.artifex.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.item.specialty.*;

import java.util.function.Supplier;

public class AFSpecialties {
    public static final ResourceKey<Registry<AFSpecialty>> SPECIALTIES_KEY = ResourceKey.createRegistryKey(Artifex.prefix("specialties"));
    public static final DeferredRegister<AFSpecialty> SPECIALTIES = DeferredRegister.create(SPECIALTIES_KEY, Artifex.MODID);
    public static final Supplier<IForgeRegistry<AFSpecialty>> REGISTRY = SPECIALTIES.makeRegistry(() -> new RegistryBuilder<AFSpecialty>().disableOverrides());

    public static final RegistryObject<AFSpecialty> EXECUTE = SPECIALTIES.register("execute", () -> new ExecuteSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> SWEEPING = SPECIALTIES.register("sweeping", () -> new SweepingSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> IMPACT_COMBO = SPECIALTIES.register("impact_combo", () -> new ImpactComboSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> FINESSE = SPECIALTIES.register("finesse", () -> new FinesseSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> ARMOR_PIERCER = SPECIALTIES.register("armor_piercer", () -> new ArmorPiercerSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> SHOCKWAVE = SPECIALTIES.register("shockwave", () -> new ShockwaveSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> CRIPPLING = SPECIALTIES.register("crippling", () -> new CripplingSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> UNSTOPPABLE = SPECIALTIES.register("unstoppable", () -> new UnstoppableSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> BOUNTIFUL_HARVEST = SPECIALTIES.register("bountiful_harvest", () -> new LevelArgOnly(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> ROGUE = SPECIALTIES.register("rogue", () -> new RogueSpecialty(AFSpecialty.Category.BENEFICIAL));
    public static final RegistryObject<AFSpecialty> IAIJUTSU = SPECIALTIES.register("iaijutsu", () -> new IaijutsuSpecialty(AFSpecialty.Category.BENEFICIAL));

    public static final RegistryObject<AFSpecialty> TWO_HANDED = SPECIALTIES.register("two_handed", () -> new AFSpecialty(AFSpecialty.Category.HARMFUL));
    public static final RegistryObject<AFSpecialty> HINDERING = SPECIALTIES.register("hindering", () -> new HinderingSpecialty(AFSpecialty.Category.HARMFUL));
}
