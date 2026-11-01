package org.celestialworkshop.artifex;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AFTags {
    public static final TagKey<Item> BOLTS = ItemTags.create(Artifex.prefix("bolts"));
    public static final TagKey<Item> THROWABLE_ENCHANTABLE = ItemTags.create(Artifex.prefix("enchantable/throwable"));
}
