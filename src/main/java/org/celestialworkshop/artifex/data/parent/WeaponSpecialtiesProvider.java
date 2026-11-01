package org.celestialworkshop.artifex.data.parent;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFSpecialtyWrapper;

import java.util.Map;

public abstract class WeaponSpecialtiesProvider extends CodecBasedProvider<AFSpecialtyWrapper> {

    protected WeaponSpecialtiesProvider(PackOutput output, String modid) {
        super(output, modid, "weapon_specialties", PackOutput.Target.DATA_PACK, AFSpecialtyWrapper.CODEC);
    }

    @Override
    public String getName() {
        return "Item Specialties: " + modid;
    }

    public void add(Item item, Map<AFSpecialty, Integer> specialties) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (id != null) {
            this.add(id, specialties);
        } else {
            throw new IllegalStateException("Unregistered item: " + item);
        }
    }

    public void add(ResourceLocation itemId, Map<AFSpecialty, Integer> specialties) {
        if (data.put(itemId, new AFSpecialtyWrapper(specialties)) != null) {
            throw new IllegalStateException("Duplicate specialty data for " + itemId);
        }
    }
}
