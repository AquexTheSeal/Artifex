package org.celestialworkshop.artifex.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AFAdvancementProvider extends ForgeAdvancementProvider {

    public AFAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Advancements()));
    }

    public static class Advancements implements AdvancementGenerator {

        public Advancement root;

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        }
    }

    public static Advancement obtainItemBasic(RegistryObject<Item> item, Advancement parent, FrameType frameType, Consumer<Advancement> saver) {
        String name = item.getId().getPath();
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(
                        item.get(),
                        asTitle("obtain_" + name), asDescription("obtain_" + name),
                        null, frameType, true, true, false
                )
                .addCriterion("obtained_" + name, InventoryChangeTrigger.TriggerInstance.hasItems(item.get()))
                .save(saver, "behemoths:obtain_" + name);
    }

    public static Component asTitle(String name) {
        return Component.translatable("advancement.behemoths." + name + ".title");
    }

    public static Component asDescription(String name) {
        return Component.translatable("advancement.behemoths." + name + ".description");
    }
}
