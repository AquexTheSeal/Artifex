package org.celestialworkshop.artifex;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.client.AFCreativeTabOverride;
import org.celestialworkshop.artifex.client.tooltip.SpecialtyTooltip;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.registry.AFItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AFClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && mc.player != null && mc.level != null) {

            AFCreativeTabOverride.tick();

            if (mc.player.tickCount % 20 == 0) {
                List<ItemStack> list = new ObjectArrayList<>(AFItems.ITEMS.getEntries().stream().map(obj -> new ItemStack(obj.get())).filter(stack -> !(stack.getItem() instanceof SmithingTemplateItem) && !(stack.getItem() instanceof BlockItem)).toList());
                if (list.isEmpty()) return;
                ItemStack next = list.get(mc.level.random.nextInt(list.size()));

                if (AFCreativeTabOverride.currentStack.isEmpty()) {
                    AFCreativeTabOverride.currentStack = next;
                } else {
                    AFCreativeTabOverride.startTransition(next);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof AFPropertyItem af && !af.getSpecialties().isEmpty()) {
            event.getTooltipElements().add(Either.right(new SpecialtyTooltip(af.getSpecialties())));
        }
    }

}
