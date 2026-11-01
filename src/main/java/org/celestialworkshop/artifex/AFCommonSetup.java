package org.celestialworkshop.artifex;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.celestialworkshop.artifex.advancement.AFWeaponTypePredicate;
import org.celestialworkshop.artifex.advancement.IngredientPredicate;
import org.celestialworkshop.artifex.registry.AFAttributes;

@EventBusSubscriber(modid = Artifex.MODID)
public class AFCommonSetup {

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        event.register(Registries.ITEM_SUB_PREDICATE_TYPE, helper -> {
            helper.register(Artifex.prefix("weapon_type"), AFWeaponTypePredicate.TYPE);
            helper.register(Artifex.prefix("ingredient"), IngredientPredicate.TYPE);
        });
    }

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AFAttributes.DAMAGE_REDUCTION);
    }
}
