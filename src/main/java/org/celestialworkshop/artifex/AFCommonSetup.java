package org.celestialworkshop.artifex;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.celestialworkshop.artifex.advancement.AFWeaponTypePredicate;
import org.celestialworkshop.artifex.advancement.IngredientPredicate;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.registry.AFAttributes;

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

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AFAttributes.DAMAGE_REDUCTION.get());
    }
}
