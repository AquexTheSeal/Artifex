package org.celestialworkshop.artifex.data.parent.material;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.data.parent.WeaponSpecialtiesProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialWeaponSpecialtiesProvider extends WeaponSpecialtiesProvider {

    private final List<AFMaterial> materials;

    public MaterialWeaponSpecialtiesProvider(PackOutput output, String modid, List<AFMaterial> materials) {
        super(output, modid);
        this.materials = materials;
    }

    @Override
    protected void addEntries() {
        for (AFMaterial material : materials) {
            for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
                Item weapon = material.getWeapon(weaponType);
                Map<AFSpecialty, Integer> merged = new HashMap<>(weaponType.getWeaponTypeSpecialties().get());
                merged.putAll(material.getWeaponTypeSpecialties().get());
                this.add(weapon, merged);
            }
        }
    }
}
