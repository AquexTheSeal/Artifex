package org.celestialworkshop.artifex.data.parent;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.celestialworkshop.artifex.Artifex;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class CodecBasedProvider<TYPE> implements DataProvider {

    protected final Map<ResourceLocation, TYPE> data = new TreeMap<>();
    protected final PackOutput output;
    protected final String modid;
    protected final String folder;
    protected final PackOutput.Target target;
    protected final Codec<TYPE> codec;

    protected CodecBasedProvider(PackOutput output, String modid, String folder, PackOutput.Target target, Codec<TYPE> codec) {
        this.output = output;
        this.modid = modid;
        this.folder = folder;
        this.target = target;
        this.codec = codec;
    }

    protected abstract void addEntries();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        addEntries();

        if (data.isEmpty()) return CompletableFuture.allOf();

        Path basePath = output.getOutputFolder(target).resolve(modid).resolve(folder);

        List<CompletableFuture<?>> futures = new ArrayList<>();

        data.forEach((id, value) -> {
            JsonElement json = codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow(false, Artifex.LOGGER::error);
            Path target = basePath.resolve(id.getPath() + ".json");
            futures.add(DataProvider.saveStable(cache, json, target));
        });

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
