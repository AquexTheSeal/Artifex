package org.celestialworkshop.artifex.api;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
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
import java.util.function.Supplier;

public class AFMaterial {

    private static final ObjectArrayList<AFMaterial> ALL_MATERIALS = new ObjectArrayList<>();
    private static final Reference2ObjectOpenHashMap<Item, AFWeaponType> ITEM_TO_WEAPON_TYPE = new Reference2ObjectOpenHashMap<>();

    private final Object2ObjectOpenHashMap<AFWeaponType, RegistryObject<Item>> registeredWeaponsMap = new Object2ObjectOpenHashMap<>();

    public final Tier itemTier;
    public final Supplier<Item.Properties> itemProperties;

    public AFMaterial(Builder builder, Tier itemTier, Supplier<Item.Properties> itemProperties) {
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

            RegistryObject<Item> result = builder.itemRegister.register(itemId, () -> {
                Item item = weaponType.getMaker().create(this);
                ITEM_TO_WEAPON_TYPE.put(item, weaponType);
                return item;
            });
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
        return getWeaponType(item) == weaponType;
    }

    public static @Nullable AFWeaponType getWeaponType(Item item) {
        return item != null ? ITEM_TO_WEAPON_TYPE.get(item) : null;
    }

    public Tier getItemTier() {
        return itemTier;
    }

    public Supplier<Item.Properties> getItemPropertiesSupplier() {
        return itemProperties;
    }

    public static Builder builder(DeferredRegister<Item> itemRegister, String materialId) {
        return new Builder(itemRegister, materialId);
    }

    public static class Builder {
        protected final DeferredRegister<Item> itemRegister;
        protected final String materialId;

        private Tier itemTier = Tiers.WOOD;
        private Supplier<Item.Properties> itemProperties = Item.Properties::new;
        private final List<AFWeaponType> weaponTypeBlacklist = new ObjectArrayList<>();

        public Builder(DeferredRegister<Item> itemRegister, String materialId) {
            this.itemRegister = itemRegister;
            this.materialId = materialId;
        }

        public Builder tier(Tier tier) {
            this.itemTier = tier;
            return this;
        }

        public Builder properties(Supplier<Item.Properties> properties) {
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