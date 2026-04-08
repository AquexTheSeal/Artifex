package org.celestialworkshop.artifex;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
 
@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AFCommonEvents {
 
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        AFEntityDataCapability.Provider provider = new AFEntityDataCapability.Provider();
        event.addCapability(Artifex.prefix("artifex_entity_data"), provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof AFThrowableTieredItem af) {
            AFAmmoDataCapability.Provider provider = new AFAmmoDataCapability.Provider(event.getObject(), af.getMaximumAmmo());
            event.addCapability(Artifex.prefix("ammo"), provider);
            event.addListener(provider::invalidate);
        }
    }
}
