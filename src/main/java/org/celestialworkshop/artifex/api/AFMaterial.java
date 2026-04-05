package org.celestialworkshop.artifex.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AFMaterial {

    private static final ObjectArrayList<AFMaterial> ALL_MATERIALS = new ObjectArrayList<>();
    private final Object2ObjectOpenHashMap<AFWeaponType, RegistryObject<Item>> registeredWeaponsMap = new Object2ObjectOpenHashMap<>();

    public final Tier itemTier;
    public final Item.Properties itemProperties;

    public AFMaterial(Builder builder, Tier itemTier, Item.Properties itemProperties) {
        this.itemTier = itemTier;
        this.itemProperties = itemProperties;
        this.registerWeapons(builder);
    }

    public void registerWeapons(Builder builder) {
        for (AFWeaponType weaponType : AFWeaponType.values()) {

            String itemId = builder.materialId + "_" + weaponType.getName();

            if (builder.weaponTypeBlacklist.contains(weaponType)) {
                Artifex.LOGGER.debug("Skipping registration of excluded general weapon: {}", itemId);
                continue;
            }

            RegistryObject<Item> result = builder.itemRegister.register(itemId, () -> weaponType.getMaker().create(this));
            registeredWeaponsMap.put(weaponType, result);

        }
        ALL_MATERIALS.add(this);
    }

    public @Nullable Item getWeapon(AFWeaponType weaponType) {
        RegistryObject<Item> item = registeredWeaponsMap.get(weaponType);
        return item != null ? item.get() : null;
    }

    public Collection<AFWeaponType> getAvailableWeaponTypes() {
        return registeredWeaponsMap.keySet();
    }

    public static boolean isWeaponType(Item item, AFWeaponType weaponType) {
        if (item == null || weaponType == null) {
            return false;
        }
        for (AFMaterial material : ALL_MATERIALS) {
            Item weaponItem = material.getWeapon(weaponType);
            if (weaponItem != null && weaponItem == item) {
                return true;
            }
        }
        return false;
    }

    public Tier getItemTier() {
        return itemTier;
    }

    public Item.Properties getItemProperties() {
        return itemProperties;
    }

    public static Builder builder(DeferredRegister<Item> itemRegister, String materialId) {
        return new Builder(itemRegister, materialId);
    }

    public static class Builder {
        protected final DeferredRegister<Item> itemRegister;
        protected final String materialId;

        private Tier itemTier = Tiers.WOOD;
        private Item.Properties itemProperties = new Item.Properties();
        private final List<AFWeaponType> weaponTypeBlacklist = new ObjectArrayList<>();

        public Builder(DeferredRegister<Item> itemRegister, String materialId) {
            this.itemRegister = itemRegister;
            this.materialId = materialId;
        }

        public Builder tier(Tier tier) {
            this.itemTier = tier;
            return this;
        }

        public Builder properties(Item.Properties properties) {
            this.itemProperties = properties;
            return this;
        }

        public Builder blacklist(AFWeaponType type) {
            this.weaponTypeBlacklist.add(type);
            return this;
        }

        public Builder blacklist(AFWeaponType... types) {
            this.weaponTypeBlacklist.addAll(Arrays.asList(types));
            return this;
        }
        public AFMaterial build() {
            return new AFMaterial(this, itemTier, itemProperties);
        }
    }
}