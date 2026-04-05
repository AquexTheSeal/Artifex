package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;

public class AFSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public AFSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Artifex.MODID, helper);
    }

    @Override
    public void registerSounds() {
    }

    private void basicSound(RegistryObject<SoundEvent> sound) {
        this.add(sound.get(), subtitledSound(sound.getId().getPath())
                .with(sound(Artifex.prefix(sound.getId().getPath())))
        );
    }

    private void variantSound(RegistryObject<SoundEvent> sound, int variants) {
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
