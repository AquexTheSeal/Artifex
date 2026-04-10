package org.celestialworkshop.artifex.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.datagen.compat.AFBetterCombatPropertiesProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AFDataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper efh = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new AFSoundDefinitionsProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new AFLanguageProvider(packOutput, "en_us"));
        generator.addProvider(event.includeClient(), new AFItemModelProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new AFBlockStateProvider(packOutput, efh));

        AFTagsProvider.BlocksProvider blockTagsProvider = new AFTagsProvider.BlocksProvider(packOutput, lookupProvider, efh);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new AFTagsProvider.ItemsProvider(packOutput, lookupProvider, blockTagsProvider, efh));
        generator.addProvider(event.includeServer(), new AFLootTableProvider(packOutput));
        generator.addProvider(event.includeServer(), new AFAdvancementProvider(packOutput, lookupProvider, efh));
        generator.addProvider(event.includeServer(), new AFGlobalLootModifierProvider(packOutput));

        generator.addProvider(event.includeServer(), new AFBetterCombatPropertiesProvider(packOutput));
    }
}
