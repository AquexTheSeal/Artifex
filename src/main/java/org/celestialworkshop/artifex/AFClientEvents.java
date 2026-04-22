package org.celestialworkshop.artifex;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.client.AFCreativeTabOverride;
import org.celestialworkshop.artifex.client.tooltip.SpecialtyTooltip;
import org.celestialworkshop.artifex.registry.AFItems;
import org.celestialworkshop.artifex.util.ItemStackUtil;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AFClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.player == null || mc.level == null) return;

        AFCreativeTabOverride.tickAll();

        if (mc.player.tickCount % 20 == 0) {
            List<ItemStack> pool = AFItems.ITEMS.getEntries().stream().map(obj -> new ItemStack(obj.get())).toList();

            if (pool.isEmpty()) return;

            for (Map.Entry<CreativeModeTab, AFCreativeTabOverride.TabAnimState> entry : AFCreativeTabOverride.allEntries()) {
                AFCreativeTabOverride.TabAnimState state = entry.getValue();
                ItemStack next = pool.get(mc.level.random.nextInt(pool.size()));
                if (state.currentStack.isEmpty()) {
                    state.currentStack = next;
                } else {
                    state.startTransition(next);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        AFWeaponType type = AFWeaponType.getWeaponType(stack.getItem());

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
