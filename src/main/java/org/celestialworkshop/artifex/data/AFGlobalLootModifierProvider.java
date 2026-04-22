package org.celestialworkshop.artifex.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.loot.MergeLootTableModifier;

public class AFGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public AFGlobalLootModifierProvider(PackOutput output) {
        super(output, Artifex.MODID);
    }

    @Override
    protected void start() {
        add("blacksmith_pool", new MergeLootTableModifier(new LootItemCondition[] {
                AnyOfCondition.anyOf(
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH),
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_ARMORER)
                ).build()
        }, Artifex.prefix("chests/blacksmith_pool")));

        add("end_city_pool", new MergeLootTableModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE).build()
        }, Artifex.prefix("chests/end_city_pool")));
    }
}