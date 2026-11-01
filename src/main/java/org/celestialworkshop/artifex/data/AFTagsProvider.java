package org.celestialworkshop.artifex.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.celestialworkshop.artifex.AFTags;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.item.base.*;
import org.celestialworkshop.artifex.registry.AFItems;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AFTagsProvider {

    public static class ItemsProvider extends ItemTagsProvider {
        public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags.contentsGetter(), Artifex.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            this.tag(AFTags.BOLTS).add(AFItems.BASIC_BOLT.get(), AFItems.STANDARD_BOLT.get());

            for (DeferredHolder<Item, ? extends Item> holder : AFItems.ITEMS.getEntries()) {
                Item item = holder.get();
                if (item instanceof AFThrowableTieredItem) {
                    this.tag(AFTags.THROWABLE_ENCHANTABLE).add(item);
                }
                if (item instanceof AFTieredItem tiered) {
                    this.tag(ItemTags.WEAPON_ENCHANTABLE).add(item);
                    this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(item);
                    this.tag(ItemTags.SWORD_ENCHANTABLE).add(item);
                    this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).add(item);
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                    this.tag(ItemTags.VANISHING_ENCHANTABLE).add(item);
                } else if (item instanceof AFBowItem) {
                    this.tag(ItemTags.BOW_ENCHANTABLE).add(item);
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                    this.tag(ItemTags.VANISHING_ENCHANTABLE).add(item);
                } else if (item instanceof AFCrossbowItem) {
                    this.tag(ItemTags.CROSSBOW_ENCHANTABLE).add(item);
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                    this.tag(ItemTags.VANISHING_ENCHANTABLE).add(item);
                } else if (item instanceof AFShieldItem) {
                    this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
                    this.tag(ItemTags.VANISHING_ENCHANTABLE).add(item);
                }
            }
        }
    }

    public static class BlocksProvider extends BlockTagsProvider {
        public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, Artifex.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
        }
    }
}
