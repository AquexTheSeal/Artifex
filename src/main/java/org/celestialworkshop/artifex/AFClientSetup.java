package org.celestialworkshop.artifex;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.client.itemdecoration.AFAmmoDecoration;
import org.celestialworkshop.artifex.client.overlay.ComboOverlay;
import org.celestialworkshop.artifex.client.overlay.ThrowableIndicatorOverlay;
import org.celestialworkshop.artifex.item.base.AFShieldItem;
import org.celestialworkshop.artifex.particle.ExecuteParticle;
import org.celestialworkshop.artifex.particle.ShockwaveParticle;
import org.celestialworkshop.artifex.client.renderer.ThrownWeaponProjectileRenderer;
import org.celestialworkshop.artifex.client.tooltip.SpecialtyTooltip;
import org.celestialworkshop.artifex.item.base.AFBowItem;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.registry.AFEntities;
import org.celestialworkshop.artifex.registry.AFParticleTypes;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AFClientSetup {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        for (Map.Entry<ResourceKey<Item>, Item> item : ForgeRegistries.ITEMS.getEntries()) {
            if (item.getValue() instanceof AFBowItem afBow) {
                ItemProperties.register(item.getValue(), ResourceLocation.parse("pull"), (stack, level, entity, seed) -> {
                    if (entity == null || entity.getUseItem() != stack) {
                        return 0.0F;
                    } else {
                        float lapsed = (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks());
                        return (lapsed * afBow.getDrawSpeedMultiplier()) / 20.0F;
                    }
                });
                ItemProperties.register(item.getValue(), ResourceLocation.parse("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            }

            if (item.getValue() instanceof AFCrossbowItem) {
                ItemProperties.register(item.getValue(), ResourceLocation.parse("pull"), (stack, level, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return CrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack);
                    }
                });
                ItemProperties.register(item.getValue(), ResourceLocation.parse("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
                ItemProperties.register(item.getValue(), ResourceLocation.parse("charged"), (stack, level, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
                ItemProperties.register(item.getValue(), ResourceLocation.parse("firework"), (stack, level, entity, seed) -> CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            }

            if (item.getValue() instanceof AFShieldItem) {
                ItemProperties.register(item.getValue(), ResourceLocation.parse("blocking"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            }
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent ev) {
        ev.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "combo_bar_overlay", ComboOverlay::render);
        ev.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "throwable_indicator_overlay", ThrowableIndicatorOverlay::render);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AFEntities.THROWABLE_PROJECTILE.get(), ThrownWeaponProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SpecialtyTooltip.class, component -> component);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AFParticleTypes.EXECUTE.get(), ExecuteParticle.Provider::new);
        event.registerSpriteSet(AFParticleTypes.SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        for (Item obj : ForgeRegistries.ITEMS.getValues()) {
            if (obj instanceof AFThrowableTieredItem) {
                event.register(obj, new AFAmmoDecoration());
            }
        }
    }
}
