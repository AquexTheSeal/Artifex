package org.celestialworkshop.artifex.data;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.registry.AFSoundEvents;

public class AFSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public AFSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Artifex.MODID, helper);
    }

    @Override
    public void registerSounds() {
        for (AFSoundEvents.AutoGenSound sound : AFSoundEvents.AUTO_GEN_SOUNDS) {
            if (sound.variants() > 1) {
                this.variantSound(sound.sound(), sound.variants());
            } else {
                if (sound.stream()) {
                    this.music(sound.sound());
                } else {
                    this.basicSound(sound.sound());
                }
            }
        }
    }

    private void music(DeferredHolder<SoundEvent, SoundEvent> sound) {
        this.add(sound.get(), subtitledSound(sound.getId().getPath())
                .with(sound(Artifex.prefix(sound.getId().getPath())).stream())
        );
    }

    private void basicSound(DeferredHolder<SoundEvent, SoundEvent> sound) {
        this.add(sound.get(), subtitledSound(sound.getId().getPath())
                .with(sound(Artifex.prefix(sound.getId().getPath())))
        );
    }

    private void variantSound(DeferredHolder<SoundEvent, SoundEvent> sound, int variants) {
        SoundDefinition soundDefinition = subtitledSound(sound.getId().getPath());

        for (int i = 0; i < variants; i++) {
            soundDefinition.with(sound(Artifex.prefix(sound.getId().getPath() + "_" + i)));
        }

        this.add(sound.get(), soundDefinition);
    }

    public static SoundDefinition subtitledSound(String path) {
        return SoundDefinition.definition().subtitle("%s.subtitle.%s".formatted(Artifex.MODID, path));
    }
}
