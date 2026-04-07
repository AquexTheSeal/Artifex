package org.celestialworkshop.artifex;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.entity.AFThrowableProjectile;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.item.base.ArtifexItemProperties;

import java.util.Map;
 
@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AFCommonEvents {
 
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        Entity direct = event.getSource().getDirectEntity();
        if (attacker instanceof LivingEntity leAttacker && direct instanceof AbstractArrow arrow) {

            ItemStack heldStack = AFEntityDataCapability.get(direct).map(AFEntityData::getBoundItemStack).orElse(ItemStack.EMPTY);
            if (direct instanceof AFThrowableProjectile throwable) {
                heldStack = throwable.getHeldStack();
            }

            if (!heldStack.isEmpty() && heldStack.getItem() instanceof ArtifexItemProperties materialItem) {
                for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                    float result = entry.getKey().onDamageRanged(leAttacker, event.getEntity(), heldStack, arrow, event.getAmount(), arrow.isCritArrow(), entry.getValue());
                    event.setAmount(result);
                }
            }
        }
    }
 
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
