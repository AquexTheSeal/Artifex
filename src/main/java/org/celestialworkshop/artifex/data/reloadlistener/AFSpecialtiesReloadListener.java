package org.celestialworkshop.artifex.data.reloadlistener;

import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFSpecialtyWrapper;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class AFSpecialtiesReloadListener extends CodecBasedReloadListener<AFSpecialtyWrapper> {

    public static final Map<ResourceLocation, AFSpecialtyWrapper> SPECIALTY_DATA = new HashMap<>();

    public static final Map<Item, Map<AFSpecialty, Integer>> SPECIALTY_CACHE = new IdentityHashMap<>();

    private static final Map<AFSpecialty, Integer> EMPTY = Object2IntMaps.emptyMap();

    public AFSpecialtiesReloadListener() {
        super("weapon_specialties", AFSpecialtyWrapper.CODEC, "Artifex Weapon Specialties");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        this.invalidateSpecialtyCache();
        super.apply(objects, resourceManager, profiler);
    }

    @Override
    public Map<ResourceLocation, AFSpecialtyWrapper> getMainData() {
        return SPECIALTY_DATA;
    }

    public static Map<AFSpecialty, Integer> getSpecialtyData(ResourceLocation itemId) {
        if (SPECIALTY_DATA.containsKey(itemId)) {
            return SPECIALTY_DATA.get(itemId).specialties();
        } else {
            return EMPTY;
        }
    }

    public void invalidateSpecialtyCache() {
        SPECIALTY_CACHE.clear();
    }
}
