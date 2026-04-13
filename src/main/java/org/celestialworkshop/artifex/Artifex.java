package org.celestialworkshop.artifex;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.config.AFCommonConfig;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.registry.*;
import org.slf4j.Logger;

@Mod(Artifex.MODID)
public class Artifex {

    public static final String MODID = "artifex";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Artifex() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        AFSpecialties.SPECIALTIES.register(modEventBus);
        AFCreativeTabs.CREATIVE_TABS.register(modEventBus);
        AFItems.ITEMS.register(modEventBus);
        AFEntities.ENTITIES.register(modEventBus);
        AFSoundEvents.SOUND_EVENTS.register(modEventBus);
        AFParticleTypes.PARTICLE_TYPES.register(modEventBus);
        AFEnchantments.ENCHANTMENTS.register(modEventBus);

        AFNetwork.register();
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AFCommonConfig.SPEC, "artifex/artifex-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AFClientConfig.SPEC, "artifex/artifex-client.toml");
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
