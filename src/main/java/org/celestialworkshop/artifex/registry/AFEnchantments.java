package org.celestialworkshop.artifex.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.enchantment.AFEnchantment;

public class AFEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Artifex.MODID);

    public static final RegistryObject<Enchantment> PUNCTURE = ENCHANTMENTS.register("puncture",
            () -> new AFEnchantment(Enchantment.Rarity.COMMON, AFEnchantment.THROWABLE, 5,
                    (level) -> 1 + (level * 10),
                    (level, minCost) -> minCost + 20
            )
    );

    public static final RegistryObject<Enchantment> STOCKPILE = ENCHANTMENTS.register("stockpile",
            () -> new AFEnchantment(Enchantment.Rarity.COMMON, AFEnchantment.THROWABLE, 2,
                    (level) -> 2 + (level * 15),
                    (level, minCost) -> minCost + 30
            )
    );
}
