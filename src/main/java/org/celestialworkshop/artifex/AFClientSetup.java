package org.celestialworkshop.artifex;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.celestialworkshop.artifex.item.base.ExtendedBowItem;
import org.celestialworkshop.artifex.item.base.ExtendedCrossbowItem;
import org.celestialworkshop.artifex.registry.AFItems;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AFClientSetup {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        AFItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof ExtendedBowItem) {
                ItemProperties.register(item.get(), ResourceLocation.parse("pull"), (stack, level, entity, seed) -> entity == null || entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F);
                ItemProperties.register(item.get(), ResourceLocation.parse("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            }
            if (item.get() instanceof ExtendedCrossbowItem) {
                ItemProperties.register(item.get(), ResourceLocation.parse("pull"), (stack, level, entity, seed) -> entity == null || CrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack));
                ItemProperties.register(item.get(), ResourceLocation.parse("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
                ItemProperties.register(item.get(), ResourceLocation.parse("charged"), (stack, level, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
                ItemProperties.register(item.get(), ResourceLocation.parse("firework"), (stack, level, entity, seed) -> CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            }
        });
    }
}
