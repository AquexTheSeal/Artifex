package org.celestialworkshop.artifex.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;

import java.util.List;

public class AFSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Artifex.MODID);
    public static final List<AutoGenSound> AUTO_GEN_SOUNDS = new ObjectArrayList<>();

    public static final RegistryObject<SoundEvent> EXECUTE = registerSoundEvent("execute");

    public static final RegistryObject<SoundEvent> SHOCKWAVE = registerSoundEvent("shockwave");

    public static final RegistryObject<SoundEvent> LONGBOW_SHOOT = registerSoundEvent("longbow_shoot");

    public static final RegistryObject<SoundEvent> ARBALEST_START = registerSoundEvent("arbalest_start");
    public static final RegistryObject<SoundEvent> ARBALEST_LOADED = registerSoundEvent("arbalest_loaded");
    public static final RegistryObject<SoundEvent> ARBALEST_SHOOT = registerSoundEvent("arbalest_shoot");

    public static final RegistryObject<SoundEvent> THROWABLE_IMPACT = registerSoundEvent("throwable_impact");
    public static final RegistryObject<SoundEvent> THROWABLE_THROWN = registerSoundEvent("throwable_thrown");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return registerSoundEvent(name, 1);
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name, int variants) {
        RegistryObject<SoundEvent> object = SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifex.prefix(name)));
        AUTO_GEN_SOUNDS.add(new AutoGenSound(object, variants, false));
        return object;
    }

    private static RegistryObject<SoundEvent> registerMusic(String name) {
        RegistryObject<SoundEvent> object = SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifex.prefix(name)));
        AUTO_GEN_SOUNDS.add(new AutoGenSound(object, 1, true));
        return object;
    }

    public record AutoGenSound(RegistryObject<SoundEvent> sound, int variants, boolean stream) {
    }
}
