package org.celestialworkshop.artifex.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.celestialworkshop.artifex.Artifex;

public class AFBlockStateProvider extends BlockStateProvider {
    public AFBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Artifex.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
