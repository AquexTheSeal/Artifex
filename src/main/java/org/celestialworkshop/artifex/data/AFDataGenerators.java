package org.celestialworkshop.artifex.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.data.compat.MaterialBetterCombatPropertiesProvider;
import org.celestialworkshop.artifex.data.parent.material.MaterialWeaponSpecialtiesProvider;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Artifex.MODID)
public class AFDataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper efh = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new AFSoundDefinitionsProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new AFLanguageProvider(packOutput, "en_us"));
        generator.addProvider(event.includeClient(), new AFBlockStateProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new AFItemModelProvider(packOutput, efh));

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                new RegistrySetBuilder().add(Registries.ENCHANTMENT, AFEnchantments::bootstrap),
                Set.of(Artifex.MODID)
        );
        generator.addProvider(event.includeServer(), datapackProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = datapackProvider.getRegistryProvider();

        AFTagsProvider.BlocksProvider blockTagsProvider = new AFTagsProvider.BlocksProvider(packOutput, registryProvider, efh);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new AFTagsProvider.ItemsProvider(packOutput, registryProvider, blockTagsProvider, efh));
        generator.addProvider(event.includeServer(), new AFLootTableProvider(packOutput, registryProvider));
        generator.addProvider(event.includeServer(), new AFAdvancementProvider(packOutput, registryProvider, efh));
        generator.addProvider(event.includeServer(), new AFGlobalLootModifierProvider(packOutput, registryProvider));
        generator.addProvider(event.includeServer(), new AFRecipeProvider(packOutput, registryProvider));

        generator.addProvider(event.includeServer(), new MaterialWeaponSpecialtiesProvider(packOutput, Artifex.MODID, AFItems.MATERIALS));
        generator.addProvider(event.includeServer(), new MaterialBetterCombatPropertiesProvider(packOutput, Artifex.MODID, AFItems.MATERIALS));
    }
}
