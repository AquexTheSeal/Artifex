package org.celestialworkshop.artifex.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.celestialworkshop.artifex.advancement.AFWeaponTypePredicate;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AFAdvancementProvider extends ForgeAdvancementProvider {

    public AFAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Advancements()));
    }

    public static class Advancements implements AdvancementGenerator {

        public static final ResourceLocation rootTexture = ResourceLocation.parse("textures/block/crying_obsidian.png");

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {

            Advancement root = Advancement.Builder.advancement()
                    .display(
                            AFItems.NETHERITE_MATERIAL.getWeapon(AFWeaponType.WAR_DOOR),
                            asTitle("artifex_startup"),
                            asDescription("artifex_startup"),
                            rootTexture, FrameType.TASK, false, false, false
                    )
                    .addCriterion("on_join", PlayerTrigger.TriggerInstance.located(LocationPredicate.ANY))
                    .save(saver, "artifex:artifex_startup");

            // Per-Weapon Type advancements.
            for (AFWeaponType type : AFWeaponType.values()) {
                String typeName = type.getName();
                Advancement.Builder.advancement().parent(root)
                        .display(
                                AFItems.IRON_MATERIAL.getWeapon(type),
                                asTitle("obtain_weapon_type_" + typeName),
                                asDescription("obtain_weapon_type_" + typeName),
                                null, FrameType.TASK, true, true, false
                        )
                        .addCriterion("obtained_weapon_type_" + typeName, InventoryChangeTrigger.TriggerInstance.hasItems(new AFWeaponTypePredicate(type)))
                        .save(saver, "artifex:obtain_weapon_type_" + typeName);
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
