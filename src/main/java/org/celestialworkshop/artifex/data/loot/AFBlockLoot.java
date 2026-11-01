package org.celestialworkshop.artifex.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.celestialworkshop.artifex.data.AFLootTableProvider;

import java.util.Set;

public class AFBlockLoot extends BlockLootSubProvider {

    public AFBlockLoot(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    public void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return AFLootTableProvider.knownSet(BuiltInRegistries.BLOCK);
    }
}
