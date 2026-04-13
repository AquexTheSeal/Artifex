package org.celestialworkshop.artifex;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.celestialworkshop.artifex.advancement.AFWeaponTypePredicate;
import org.celestialworkshop.artifex.advancement.IngredientPredicate;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AFCommonSetup {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        AFWeaponTypePredicate.register();
        IngredientPredicate.register();
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        AFEntityDataCapability.register(event);
        AFAmmoDataCapability.register(event);
    }
}
