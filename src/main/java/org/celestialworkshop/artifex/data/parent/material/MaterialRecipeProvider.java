package org.celestialworkshop.artifex.data.parent.material;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.advancement.IngredientPredicate;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MaterialRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final String modid;
    private final List<AFMaterial> materials;

    public MaterialRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, List<AFMaterial> materials) {
        super(output, registries);
        this.modid = modid;
        this.materials = materials;
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        for (AFMaterial material : materials) {
            Ingredient craftingHilt = material.getCraftingHiltItem();
            Ingredient craftingReinforcedHilt = material.getCraftingReinforcedHiltItem();
            Ingredient craftingPole = material.getCraftingPoleItem();
            Ingredient craftingMaterial = material.getCraftingMaterialItem();
            AFMaterial.SmithingConfig smithingConfig = material.getSmithingConfig();

            for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
                Item weapon = material.getWeapon(weaponType);

                if (weapon != null) {
                    String name = BuiltInRegistries.ITEM.getKey(weapon).getPath();
                    boolean handledBySmithing = false;

                    if (smithingConfig != null) {
                        AFMaterial baseMaterial = smithingConfig.baseMaterial();
                        Item baseWeapon = baseMaterial.getWeapon(weaponType);
                        if (baseWeapon != null) {
                            SmithingTransformRecipeBuilder.smithing(
                                            Ingredient.of(smithingConfig.template().get()),
                                            Ingredient.of(baseWeapon),
                                            smithingConfig.ingot().get(),
                                            RecipeCategory.COMBAT,
                                            weapon)
                                    .unlocks("has_base_weapon", has(baseWeapon))
                                    .save(consumer, ResourceLocation.fromNamespaceAndPath(modid, "smithing/" + name));
                            handledBySmithing = true;
                        }
                    }

                    if (!handledBySmithing && !craftingMaterial.isEmpty()) {
                        try {
                            ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weapon);
                            String[] pattern = weaponType.getRecipePattern();
                            for (String row : pattern) {
                                builder.pattern(row);
                            }

                            this.tryDefineSymbol(pattern, builder, 'S', craftingHilt);
                            this.tryDefineSymbol(pattern, builder, 'R', craftingReinforcedHilt);
                            this.tryDefineSymbol(pattern, builder, 'P', craftingPole);
                            this.tryDefineSymbol(pattern, builder, 'X', craftingMaterial);

                            this.tryDefineSymbol(pattern, builder, 'T', Ingredient.of(Tags.Items.STRINGS));
                            this.tryDefineSymbol(pattern, builder, 'B', Ingredient.of(Items.BOW));
                            this.tryDefineSymbol(pattern, builder, 'C', Ingredient.of(Items.CROSSBOW));
                            this.tryDefineSymbol(pattern, builder, 'I', Ingredient.of(Tags.Items.INGOTS_IRON));
                            this.tryDefineSymbol(pattern, builder, 'H', Ingredient.of(Items.IRON_TRAPDOOR));

                            builder.unlockedBy("has_material", has(craftingHilt, craftingReinforcedHilt, craftingPole))
                                    .save(consumer, ResourceLocation.fromNamespaceAndPath(modid, name));

                        } catch (Exception e) {
                            Artifex.LOGGER.debug("Failed to generate recipe for weapon: {}", name);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void tryDefineSymbol(String[] pattern, ShapedRecipeBuilder builder, char character, Ingredient item) {
        String search = String.valueOf(character);
        if (Arrays.stream(pattern).anyMatch(s -> s.contains(search))) {
            builder.define(character, item);
        }
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(Ingredient... ingredient) {
        return inventoryTrigger(ItemPredicate.Builder.item().withSubPredicate(IngredientPredicate.TYPE, new IngredientPredicate(ingredient)));
    }
}
