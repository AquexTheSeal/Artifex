package org.celestialworkshop.artifex;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.config.AFCommonConfig;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.registry.*;
import org.slf4j.Logger;

@Mod(Artifex.MODID)
public class Artifex {

    public static final String MODID = "artifex";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Artifex(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(AFNetwork::register);

        AFSpecialties.SPECIALTIES.register(modEventBus);
        AFAttributes.ATTRIBUTES.register(modEventBus);
        AFDataComponents.DATA_COMPONENTS.register(modEventBus);
        AFAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        AFCreativeTabs.CREATIVE_TABS.register(modEventBus);
        AFItems.ITEMS.register(modEventBus);
        AFEntities.ENTITIES.register(modEventBus);
        AFSoundEvents.SOUND_EVENTS.register(modEventBus);
        AFParticleTypes.PARTICLE_TYPES.register(modEventBus);
        AFLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, AFCommonConfig.SPEC, "artifex/artifex-common.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, AFClientConfig.SPEC, "artifex/artifex-client.toml");
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
