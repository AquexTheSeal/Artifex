package org.celestialworkshop.artifex;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AFTags {
    public static final TagKey<Item> BOLTS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "bolts"));

}