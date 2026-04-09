package org.celestialworkshop.artifex;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.client.tooltip.SpecialtyTooltip;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AFClientEvents {

    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof AFPropertyItem af && !af.getSpecialties().isEmpty()) {
            event.getTooltipElements().add(Either.right(new SpecialtyTooltip(af.getSpecialties())));
        }
    }

}
