package org.celestialworkshop.artifex.advancement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.celestialworkshop.artifex.Artifex;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IngredientPredicate extends ItemPredicate {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "ingredient");

    private final Ingredient[] ingredients;

    public IngredientPredicate(Ingredient... ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return Arrays.stream(ingredients).anyMatch(ingredient -> ingredient.test(stack));
    }

    public static IngredientPredicate fromJson(JsonObject json) {
        JsonArray array = json.getAsJsonArray("ingredients");
        Ingredient[] ingredients = new Ingredient[array.size()];
        for (int i = 0; i < array.size(); i++) {
            ingredients[i] = Ingredient.fromJson(array.get(i));
        }
        return new IngredientPredicate(ingredients);
    }

    @Override
    public @NotNull JsonElement serializeToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", ID.toString());
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : ingredients) {
            array.add(ingredient.toJson());
        }
        obj.add("ingredients", array);
        return obj;
    }

    public static void register() {
        ItemPredicate.register(ID, IngredientPredicate::fromJson);
    }
}