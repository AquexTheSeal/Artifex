package org.celestialworkshop.artifex.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.registry.AFItems;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AFMaterial {

    private static final ObjectArrayList<AFMaterial> ALL_MATERIALS = new ObjectArrayList<>();
    public static final Reference2ObjectOpenHashMap<Item, AFWeaponType> ITEM_TO_WEAPON_TYPE = new Reference2ObjectOpenHashMap<>();

    private final Object2ObjectOpenHashMap<AFWeaponType, RegistryObject<Item>> registeredWeaponsMap = new Object2ObjectOpenHashMap<>();
    private final Supplier<Item> craftingHiltItem;
    private final Supplier<Item> craftingReinforcedHiltItem;
    private final Supplier<Item> craftingPoleItem;
    private final Supplier<Item> craftingMaterialItem;
    private final SmithingConfig smithingConfig;

    public final Tier itemTier;
    public final Supplier<Item.Properties> itemProperties;

    public AFMaterial(Supplier<Builder> builder, Tier itemTier, Supplier<Item.Properties> itemProperties) {
        this.itemTier = itemTier;
        this.itemProperties = itemProperties;
        this.registerWeapons(builder);
        this.craftingHiltItem = builder.get().craftingHiltItem;
        this.craftingReinforcedHiltItem = builder.get().craftingReinforcedHiltItem;
        this.craftingPoleItem = builder.get().craftingPoleItem;
        this.craftingMaterialItem = builder.get().craftingMaterialItem;
        this.smithingConfig = builder.get().smithingConfig;
    }

    private void registerWeapons(Supplier<Builder> builder) {
        for (AFWeaponType weaponType : AFWeaponType.values()) {
            Builder builderGet = builder.get();
            String itemId = builderGet.materialId + "_" + weaponType.getName();

            if (builderGet.weaponTypeBlacklist.contains(weaponType)) {
                Artifex.LOGGER.debug("Skipping excluded weapon type: {}", itemId);
                continue;
            }

            MaterialSpecialties matSpec = new MaterialSpecialties();
            builderGet.specialtiesConfig.accept(matSpec);
            Supplier<Map<AFSpecialty, Integer>> mergedSpecialties = weaponType.mergeSpecialties(matSpec);

            RegistryObject<Item> registered = builderGet.itemRegister.register(itemId, () -> {
                Item item = weaponType.getMaker().create(this, mergedSpecialties);
                ITEM_TO_WEAPON_TYPE.put(item, weaponType);
                return item;
            });

            registeredWeaponsMap.put(weaponType, registered);
        }
        ALL_MATERIALS.add(this);
    }


    public Item getWeapon(AFWeaponType weaponType) {
        RegistryObject<Item> item = registeredWeaponsMap.get(weaponType);
        return item != null ? item.get() : null;
    }

    public Item getCraftingHiltItem() {
        return craftingHiltItem.get();
    }

    public Item getCraftingReinforcedHiltItem() {
        return craftingHiltItem.get();
    }

    public Item getCraftingPoleItem() {
        return craftingPoleItem.get();
    }

    public Item getCraftingMaterialItem() {
        return craftingMaterialItem.get();
    }

    public @Nullable SmithingConfig getSmithingConfig() {
        return smithingConfig;
    }

    public Collection<AFWeaponType> getAvailableWeaponTypes() {
        return registeredWeaponsMap.keySet();
    }

    public Tier getItemTier() { return itemTier; }

    public Supplier<Item.Properties> getItemPropertiesSupplier() { return itemProperties; }


    public static Builder builder(DeferredRegister<Item> itemRegister, String materialId) {
        return new Builder(itemRegister, materialId);
    }

    public static class Builder {
        protected final DeferredRegister<Item> itemRegister;
        protected final String materialId;

        private Tier itemTier = Tiers.WOOD;
        private Supplier<Item.Properties> itemProperties = Item.Properties::new;
        private final List<AFWeaponType> weaponTypeBlacklist = new ObjectArrayList<>();
        private Consumer<MaterialSpecialties> specialtiesConfig = s -> {};

        protected Supplier<Item> craftingHiltItem = AFItems.BASIC_HILT;
        protected Supplier<Item> craftingReinforcedHiltItem = AFItems.REINFORCED_HILT;
        protected Supplier<Item> craftingPoleItem = AFItems.POLE;
        protected Supplier<Item> craftingMaterialItem = () -> itemTier.getRepairIngredient().getItems()[0].getItem();
        protected SmithingConfig smithingConfig;

        public Builder(DeferredRegister<Item> itemRegister, String materialId) {
            this.itemRegister = itemRegister;
            this.materialId = materialId;
        }

        public Builder craftingHiltItem(Supplier<Item> value) {
            this.craftingHiltItem = value;
            return this;
        }

        public Builder craftingReinforcedHiltItem(Supplier<Item> value) {
            this.craftingReinforcedHiltItem = value;
            return this;
        }

        public Builder craftingPoleItem(Supplier<Item> value) {
            this.craftingPoleItem = value;
            return this;
        }

        public Builder craftingMaterialItem(Supplier<Item> value) {
            this.craftingMaterialItem = value;
            return this;
        }

        public Builder smithingConfig(Supplier<Item> template, Supplier<Item> ingot, AFMaterial baseMaterial) {
            this.smithingConfig = new SmithingConfig(template, ingot, baseMaterial);
            return this;
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

        public Builder specialties(Consumer<MaterialSpecialties> config) {
            this.specialtiesConfig = config;
            return this;
        }

        public AFMaterial build() {
            return new AFMaterial(() -> this, itemTier, itemProperties);
        }
    }

    public record SmithingConfig(Supplier<Item> template, Supplier<Item> ingot, AFMaterial baseMaterial) {}
}