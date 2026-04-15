package org.celestialworkshop.artifex.registry;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.loot.MergeLootTableModifier;

public class AFLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Artifex.MODID);

    public static final RegistryObject<Codec<MergeLootTableModifier>> MERGE_LOOT_TABLE = LOOT_MODIFIERS.register("merge_loot_table", MergeLootTableModifier.CODEC);
}
