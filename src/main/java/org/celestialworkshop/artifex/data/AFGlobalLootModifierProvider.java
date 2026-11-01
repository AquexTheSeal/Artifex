package org.celestialworkshop.artifex.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.loot.MergeLootTableModifier;

import java.util.concurrent.CompletableFuture;

public class AFGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public AFGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Artifex.MODID);
    }

    @Override
    protected void start() {
        add("blacksmith_pool", new MergeLootTableModifier(new LootItemCondition[]{
                AnyOfCondition.anyOf(
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_ARMORER.location())
                ).build()
        }, Artifex.prefix("chests/blacksmith_pool")));

        add("end_city_pool", new MergeLootTableModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()
        }, Artifex.prefix("chests/end_city_pool")));
    }
}
