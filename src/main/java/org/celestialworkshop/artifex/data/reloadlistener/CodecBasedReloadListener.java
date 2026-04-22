package org.celestialworkshop.artifex.data.reloadlistener;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.celestialworkshop.artifex.Artifex;

import java.util.Map;

public abstract class CodecBasedReloadListener<T> extends SimpleJsonResourceReloadListener {

    private final Codec<T> codec;
    private final String dataName;

    private static final Gson GSON = new Gson();

    public CodecBasedReloadListener(String folder, Codec<T> codec, String dataName) {
        super(GSON, folder);
        this.codec = codec;
        this.dataName = dataName;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        getMainData().clear();

        objects.forEach((id, json) -> {
            try {
                codec.parse(JsonOps.INSTANCE, json).resultOrPartial(
                        err -> Artifex.LOGGER.error("Failed to parse {} data for {}: {}", dataName, id, err)
                ).ifPresent(parsed -> {
                    getMainData().put(id, parsed);
                    Artifex.LOGGER.info("Loaded {} data for: {}", dataName, id);
                });
            } catch (Exception e) {
                Artifex.LOGGER.error("Failed to load {} data for {}: {}", dataName, id, e.getMessage());
            }
        });

        Artifex.LOGGER.info("Loaded {} {} entries", getMainData().size(), dataName);
    }

    public abstract Map<ResourceLocation, T> getMainData();

    public Map<ResourceLocation, T> getData() {
        return Map.copyOf(getMainData());
    }

    public T get(ResourceLocation id) {
        return getMainData().get(id);
    }

    public boolean contains(ResourceLocation id) {
        return getMainData().containsKey(id);
    }
}
