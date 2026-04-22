package org.celestialworkshop.artifex.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.data.parent.material.MaterialRecipeProvider;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.function.Consumer;

public class AFRecipeProvider extends MaterialRecipeProvider {
    public AFRecipeProvider(PackOutput output) {
        super(output, Artifex.MODID, AFItems.MATERIALS);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildRecipes(consumer);

        // CHARRED STICK
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.STICK), RecipeCategory.TOOLS, AFItems.CHARRED_STICK.get(), 0.35F, 400)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "charred_stick_campfire_cooking"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.STICK), RecipeCategory.TOOLS, AFItems.CHARRED_STICK.get(), 0.35F, 200)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "charred_stick_smelting"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.STICK), RecipeCategory.TOOLS, AFItems.CHARRED_STICK.get(), 0.35F, 100)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "charred_stick_smoking"));

        // BASIC HILT
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.BASIC_HILT.get()).requires(Items.STICK).requires(Ingredient.of(Tags.Items.STRING), 2)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "basic_hilt_from_string"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.BASIC_HILT.get()).requires(Items.STICK).requires(Ingredient.of(Tags.Items.FEATHERS), 2)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "basic_hilt_from_feathers"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.BASIC_HILT.get()).requires(Items.STICK).requires(Ingredient.of(ItemTags.SAPLINGS), 2)
                .unlockedBy("has_item", has(Items.STICK)).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "basic_hilt_from_sapling"))
        ;

        // STANDARD HILT
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_HILT.get()).requires(AFItems.CHARRED_STICK.get()).requires(Ingredient.of(Tags.Items.STRING), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_hilt_from_string"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_HILT.get()).requires(AFItems.CHARRED_STICK.get()).requires(Ingredient.of(Tags.Items.FEATHERS), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_hilt_from_feathers"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_HILT.get()).requires(AFItems.CHARRED_STICK.get()).requires(Ingredient.of(ItemTags.SAPLINGS), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_hilt_from_sapling"))
        ;

        // STANDARD POLE
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_POLE.get()).requires(AFItems.CHARRED_STICK.get(), 2).requires(Ingredient.of(Tags.Items.STRING), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_pole_from_string"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_POLE.get()).requires(AFItems.CHARRED_STICK.get(), 2).requires(Ingredient.of(Tags.Items.FEATHERS), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_pole_from_feathers"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_POLE.get()).requires(AFItems.CHARRED_STICK.get(), 2).requires(Ingredient.of(ItemTags.SAPLINGS), 2)
                .unlockedBy("has_item", has(AFItems.CHARRED_STICK.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_pole_from_sapling"))
        ;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AFItems.STANDARD_POLE.get()).requires(AFItems.STANDARD_HILT.get(), 2)
                .unlockedBy("has_item", has(AFItems.STANDARD_HILT.get())).save(consumer, ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "standard_pole_from_standard_hilt"))
        ;
    }
}
