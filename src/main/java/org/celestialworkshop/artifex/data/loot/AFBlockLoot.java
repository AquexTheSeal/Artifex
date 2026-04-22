package org.celestialworkshop.artifex.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.data.AFLootTableProvider;

import java.util.Set;

public class AFBlockLoot extends BlockLootSubProvider {

    public AFBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return AFLootTableProvider.knownSet(ForgeRegistries.BLOCKS);
    }
}
