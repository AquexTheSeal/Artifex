package org.celestialworkshop.artifex.advancement;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class IngredientPredicate implements ItemSubPredicate {

    public static final ItemSubPredicate.Type<IngredientPredicate> TYPE = new ItemSubPredicate.Type<>(
            RecordCodecBuilder.create(instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(p -> Arrays.asList(p.ingredients))
            ).apply(instance, IngredientPredicate::new))
    );

    private final Ingredient[] ingredients;

    public IngredientPredicate(Ingredient... ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientPredicate(List<Ingredient> ingredients) {
        this.ingredients = ingredients.toArray(new Ingredient[0]);
    }

    @Override
    public boolean matches(ItemStack stack) {
        return Arrays.stream(ingredients).anyMatch(ingredient -> ingredient.test(stack));
    }
}
