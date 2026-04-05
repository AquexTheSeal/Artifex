package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.IForgeRegistry;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.datagen.loot.AFBlockLoot;
import org.celestialworkshop.artifex.datagen.loot.AFChestLoot;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AFLootTableProvider extends LootTableProvider {

    public AFLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(AFBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(AFChestLoot::new, LootContextParamSets.CHEST)
        ));
    }

    public static <T> Set<T> knownSet(final IForgeRegistry<T> registry) {
        return StreamSupport
                .stream(registry.spliterator(), false)
                .filter(entry -> Optional.ofNullable(registry.getKey(entry))
                        .filter(key -> key.getNamespace().equals(Artifex.MODID))
                        .isPresent()).collect(Collectors.toSet());
    }
}
