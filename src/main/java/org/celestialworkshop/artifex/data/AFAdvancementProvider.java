package org.celestialworkshop.artifex.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.advancement.AFWeaponTypePredicate;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AFAdvancementProvider extends AdvancementProvider {

    public AFAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Advancements()));
    }

    public static class Advancements implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            AFItems.NETHERITE_MATERIAL.getWeapon(AFWeaponType.WAR_DOOR),
                            asTitle("artifex_startup"),
                            asDescription("artifex_startup"),
                            ResourceLocation.parse("textures/block/crying_obsidian.png"), AdvancementType.TASK, false, false, false
                    )
                    .addCriterion("on_join", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location()))
                    .save(saver, Artifex.prefix("artifex_startup"), existingFileHelper);

            for (AFWeaponType type : AFWeaponType.values()) {
                String typeName = type.getName();
                Advancement.Builder.advancement().parent(root)
                        .display(
                                AFItems.IRON_MATERIAL.getWeapon(type),
                                asTitle("obtain_weapon_type_" + typeName),
                                asDescription("obtain_weapon_type_" + typeName),
                                null, AdvancementType.TASK, true, true, false
                        )
                        .addCriterion("obtained_weapon_type_" + typeName, InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().withSubPredicate(AFWeaponTypePredicate.TYPE, new AFWeaponTypePredicate(type))
                        ))
                        .save(saver, Artifex.prefix("obtain_weapon_type_" + typeName), existingFileHelper);
            }
        }
    }

    public static Component asTitle(String name) {
        return Component.translatable("advancement.artifex." + name + ".title");
    }

    public static Component asDescription(String name) {
        return Component.translatable("advancement.artifex." + name + ".description");
    }
}
