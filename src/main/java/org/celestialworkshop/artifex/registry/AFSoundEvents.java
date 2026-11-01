package org.celestialworkshop.artifex.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.celestialworkshop.artifex.Artifex;

import java.util.List;

public class AFSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Artifex.MODID);
    public static final List<AutoGenSound> AUTO_GEN_SOUNDS = new ObjectArrayList<>();

    public static final DeferredHolder<SoundEvent, SoundEvent> EXECUTE = registerSoundEvent("execute");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOCKWAVE = registerSoundEvent("shockwave");
    public static final DeferredHolder<SoundEvent, SoundEvent> IAIJUTSU = registerSoundEvent("iaijutsu");

    public static final DeferredHolder<SoundEvent, SoundEvent> LONGBOW_SHOOT = registerSoundEvent("longbow_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ARBALEST_START = registerSoundEvent("arbalest_start");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARBALEST_LOADED = registerSoundEvent("arbalest_loaded");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARBALEST_SHOOT = registerSoundEvent("arbalest_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> THROWABLE_IMPACT = registerSoundEvent("throwable_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> THROWABLE_THROWN = registerSoundEvent("throwable_thrown");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return registerSoundEvent(name, 1);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name, int variants) {
        DeferredHolder<SoundEvent, SoundEvent> object = SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifex.prefix(name)));
        AUTO_GEN_SOUNDS.add(new AutoGenSound(object, variants, false));
        return object;
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerMusic(String name) {
        DeferredHolder<SoundEvent, SoundEvent> object = SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifex.prefix(name)));
        AUTO_GEN_SOUNDS.add(new AutoGenSound(object, 1, true));
        return object;
    }

    public record AutoGenSound(DeferredHolder<SoundEvent, SoundEvent> sound, int variants, boolean stream) {
    }
}
