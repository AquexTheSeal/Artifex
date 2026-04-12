package org.celestialworkshop.artifex.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFWeaponType;

public class AFCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Artifex.MODID);

    public static final RegistryObject<CreativeModeTab> ARTIFEX = CREATIVE_TABS.register("artifex", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + Artifex.MODID + ".artifex"))
            .icon(() -> new ItemStack(AFItems.NETHERITE_MATERIAL.getWeapon(AFWeaponType.WAR_DOOR)))
            .displayItems((params, output) -> {
               AFItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept);
            })
            .build()
    );

}
