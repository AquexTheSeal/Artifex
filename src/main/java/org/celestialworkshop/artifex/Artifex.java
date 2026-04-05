package org.celestialworkshop.artifex;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.celestialworkshop.artifex.registry.AFCreativeTabs;
import org.celestialworkshop.artifex.registry.AFItems;
import org.slf4j.Logger;

@Mod(Artifex.MODID)
public class Artifex {

    public static final String MODID = "artifex";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Artifex() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        AFCreativeTabs.CREATIVE_TABS.register(modEventBus);
        AFItems.ITEMS.register(modEventBus);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
