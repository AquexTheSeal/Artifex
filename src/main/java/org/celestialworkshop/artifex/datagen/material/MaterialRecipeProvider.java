package org.celestialworkshop.artifex.datagen.material;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MaterialRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final String modid;
    private final List<AFMaterial> materials;

    public MaterialRecipeProvider(PackOutput output, String modid, List<AFMaterial> materials) {
        super(output);
        this.modid = modid;
        this.materials = materials;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (AFMaterial material : materials) {
            Item craftingHilt = material.getCraftingHiltItem();
            Item craftingReinforcedHilt = material.getCraftingReinforcedHiltItem();
            Item craftingPole = material.getCraftingPoleItem();
            Item craftingMaterial = material.getCraftingMaterialItem();
            AFMaterial.SmithingConfig smithingConfig = material.getSmithingConfig();

            for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
                Item weapon = material.getWeapon(weaponType);

                if (weapon == null) continue;

                String name = ForgeRegistries.ITEMS.getKey(weapon).getPath();

                if (smithingConfig != null) {
                    AFMaterial baseMaterial = smithingConfig.baseMaterial();
                    Item baseWeapon = baseMaterial.getWeapon(weaponType);
                    if (baseWeapon != null) {
                        SmithingTransformRecipeBuilder.smithing(
                                        Ingredient.of(smithingConfig.template().get()),
                                        Ingredient.of(baseWeapon),
                                        Ingredient.of(smithingConfig.ingot().get()),
                                        RecipeCategory.COMBAT,
                                        weapon)
                                .unlocks("has_base_weapon", has(baseWeapon))
                                .save(consumer, ResourceLocation.fromNamespaceAndPath(modid, "smithing/" + name));
                        continue;
                    }
                }

                if (craftingMaterial == null) continue;

                String[] pattern = weaponType.getRecipePattern();
                ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weapon);
                for (String row : pattern) {
                    builder.pattern(row);
                }

                this.tryDefineSymbol(pattern, builder, 'S', craftingHilt);
                this.tryDefineSymbol(pattern, builder, 'R', craftingReinforcedHilt);
                this.tryDefineSymbol(pattern, builder, 'P', craftingPole);
                this.tryDefineSymbol(pattern, builder, 'X', craftingMaterial);

                this.tryDefineSymbol(pattern, builder, 'T', Tags.Items.STRING);
                this.tryDefineSymbol(pattern, builder, 'B', Items.BOW);
                this.tryDefineSymbol(pattern, builder, 'C', Items.CROSSBOW);
                this.tryDefineSymbol(pattern, builder, 'I', Tags.Items.INGOTS_IRON);
                this.tryDefineSymbol(pattern, builder, 'H', Items.IRON_TRAPDOOR);

                builder.unlockedBy("has_material", has(craftingMaterial))
                        .save(consumer, ResourceLocation.fromNamespaceAndPath(modid, name));
            }
        }
    }

    public void tryDefineSymbol(String[] pattern, ShapedRecipeBuilder builder, char character, ItemLike item) {
        String search = String.valueOf(character);
        if (Arrays.stream(pattern).anyMatch(s -> s.contains(search))) {
            builder.define(character, item);
        }
    }

    public void tryDefineSymbol(String[] pattern, ShapedRecipeBuilder builder, char character, TagKey<Item> tagKey) {
        String search = String.valueOf(character);
        if (Arrays.stream(pattern).anyMatch(s -> s.contains(search))) {
            builder.define(character, tagKey);
        }
    }
}
