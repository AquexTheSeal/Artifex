package org.celestialworkshop.artifex.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.registry.AFItems;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AFMaterial {

    private static final ObjectArrayList<AFMaterial> ALL_MATERIALS = new ObjectArrayList<>();
    public static final Reference2ObjectOpenHashMap<Item, AFWeaponType> ITEM_TO_WEAPON_TYPE = new Reference2ObjectOpenHashMap<>();

    private final Object2ObjectOpenHashMap<AFWeaponType, RegistryObject<Item>> registeredWeaponsMap = new Object2ObjectOpenHashMap<>();

    private final Supplier<Map<AFSpecialty, Integer>> weaponTypeSpecialties;
    private final Supplier<Ingredient> craftingHiltItem;
    private final Supplier<Ingredient> craftingReinforcedHiltItem;
    private final Supplier<Ingredient> craftingPoleItem;
    private final Supplier<Ingredient> craftingMaterialItem;
    private final SmithingConfig smithingConfig;

    public final Tier itemTier;
    public final Supplier<Item.Properties> itemProperties;

    public AFMaterial(Supplier<Builder> builder, Tier itemTier, Supplier<Item.Properties> itemProperties) {
        this.itemTier = itemTier;
        this.itemProperties = itemProperties;
        this.registerWeapons(builder);

        this.weaponTypeSpecialties = builder.get().weaponTypeSpecialties;
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

            RegistryObject<Item> registered = builderGet.itemRegister.register(itemId, () -> {
                Item item = weaponType.getMaker().create(this);
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

    public Supplier<Map<AFSpecialty, Integer>> getWeaponTypeSpecialties() {
        return weaponTypeSpecialties;
    }

    public Ingredient getCraftingHiltItem() {
        return craftingHiltItem.get();
    }

    public Ingredient getCraftingReinforcedHiltItem() {
        return craftingReinforcedHiltItem.get();
    }

    public Ingredient getCraftingPoleItem() {
        return craftingPoleItem.get();
    }

    public Ingredient getCraftingMaterialItem() {
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

        protected Supplier<Map<AFSpecialty, Integer>> weaponTypeSpecialties = Map::of;
        protected Supplier<Ingredient> craftingHiltItem = () -> Ingredient.of(AFItems.BASIC_HILT.get());
        protected Supplier<Ingredient> craftingReinforcedHiltItem = () -> Ingredient.of(AFItems.STANDARD_HILT.get());
        protected Supplier<Ingredient> craftingPoleItem = () -> Ingredient.of(AFItems.STANDARD_POLE.get());
        protected Supplier<Ingredient> craftingMaterialItem = () -> itemTier.getRepairIngredient();

        protected SmithingConfig smithingConfig;

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

        public Builder weaponTypeSpecialties(Supplier<Map<AFSpecialty, Integer>> value) {
            this.weaponTypeSpecialties = value;
            return this;
        }

        public Builder craftingHiltItem(Supplier<Ingredient> value) {
            this.craftingHiltItem = value;
            return this;
        }

        public Builder craftingReinforcedHiltItem(Supplier<Ingredient> value) {
            this.craftingReinforcedHiltItem = value;
            return this;
        }

        public Builder craftingPoleItem(Supplier<Ingredient> value) {
            this.craftingPoleItem = value;
            return this;
        }

        public Builder craftingMaterialItem(Supplier<Ingredient> value) {
            this.craftingMaterialItem = value;
            return this;
        }

        public Builder smithingConfig(Supplier<Item> template, Supplier<Ingredient> ingot, AFMaterial baseMaterial) {
            this.smithingConfig = new SmithingConfig(template, ingot, baseMaterial);
            return this;
        }

        public Builder blacklist(AFWeaponType... types) {
            this.weaponTypeBlacklist.addAll(Arrays.asList(types));
            return this;
        }

        public AFMaterial build() {
            return new AFMaterial(() -> this, itemTier, itemProperties);
        }
    }

    public record SmithingConfig(Supplier<Item> template, Supplier<Ingredient> ingot, AFMaterial baseMaterial) {}
}