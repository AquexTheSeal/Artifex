package org.celestialworkshop.artifex;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.item.base.ArtifexItemProperties;

import java.util.Map;
 
@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AFCommonEvents {
 
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        Entity direct = event.getSource().getDirectEntity();
        if (attacker instanceof LivingEntity leAttacker && direct instanceof AbstractArrow arrow) {
 
            AFEntityDataCapability.get(arrow).ifPresent(cap -> {
                if (cap.getBoundItemStack().getItem() instanceof ArtifexItemProperties materialItem) {
                    for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                        float result = entry.getKey().onDamageRanged(leAttacker, event.getEntity(), cap.getBoundItemStack(), arrow, event.getAmount(), arrow.isCritArrow(), entry.getValue());
                        event.setAmount(result);
                    }
                }
            });
        }
    }
 
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        AFEntityDataCapability.Provider provider = new AFEntityDataCapability.Provider();
        event.addCapability(Artifex.prefix("artifex_entity_data"), provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onSwapItems(LivingSwapItemsEvent.Hands event) {
//        if (event.getEntity() instanceof Player player) {
//            if (AFSpecialty.hasSpecialty(player.getMainHandItem().getItem(), AFSpecialties.TWO_HANDED.get())) {
//                event.setCanceled(true);
//            }
//        }
    }
}
