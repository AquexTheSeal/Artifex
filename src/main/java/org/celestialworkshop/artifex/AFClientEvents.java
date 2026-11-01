package org.celestialworkshop.artifex;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.client.tooltip.SpecialtyTooltip;
import org.celestialworkshop.artifex.util.ItemStackUtil;

import java.util.*;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = Artifex.MODID, value = Dist.CLIENT)
public class AFClientEvents {

    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        Map<AFSpecialty, Integer> specialties = ItemStackUtil.getSpecialties(stack.getItem());

        if (!specialties.isEmpty()) {
            List<AFSpecialty.Category> categoryOrder = List.of(
                    AFSpecialty.Category.BENEFICIAL,
                    AFSpecialty.Category.NEUTRAL,
                    AFSpecialty.Category.HARMFUL
            );

            Map<AFSpecialty, Integer> sortedSpecialties = specialties.entrySet().stream()
                    .sorted(Comparator.comparingInt(e ->
                            categoryOrder.indexOf(e.getKey().getCategory())))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

            event.getTooltipElements().add(Either.right(new SpecialtyTooltip(sortedSpecialties)));
        }
    }

}
