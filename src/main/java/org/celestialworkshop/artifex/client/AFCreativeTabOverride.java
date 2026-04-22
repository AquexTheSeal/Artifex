package org.celestialworkshop.artifex.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.config.AFClientConfig;

import java.util.*;
import java.util.function.Supplier;

public class AFCreativeTabOverride {

    private static final Map<CreativeModeTab, TabAnimState> TAB_OVERRIDES = new LinkedHashMap<>();
    private static final float ANIM_DURATION = 20F;

    public static void registerTabOverride(Supplier<CreativeModeTab> tabSupplier) {
        TAB_OVERRIDES.put(tabSupplier.get(), new TabAnimState());
    }

    public static Set<Map.Entry<CreativeModeTab, TabAnimState>> allEntries() {
        return Collections.unmodifiableSet(TAB_OVERRIDES.entrySet());
    }

    public static boolean renderAnimatedIcon(GuiGraphics pGuiGraphics, CreativeModeTab pCreativeModeTab, Font font, int x, int y) {

        if (!AFClientConfig.CUSTOM_CREATIVE_TAB_RENDER.get()) return false;

        TabAnimState state = TAB_OVERRIDES.get(pCreativeModeTab);
        if (state == null) return false;
        if (state.currentStack.isEmpty()) return false;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;

        float partial = mc.getPartialTick();
        float delta   = Math.min((state.animTick + partial) / ANIM_DURATION, 1f);

        if (delta >= 0.5f && !state.swapped) {
            state.currentStack = state.nextStack;
            state.swapped = true;
        }

        float angle = easeInOutExpo(delta, 180);

        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(x + 8, y + 8, 0);
        pose.mulPose(Axis.ZP.rotationDegrees(angle));
        pGuiGraphics.renderItem(state.currentStack, -8, -8);
        pose.popPose();

        return true;
    }

    public static void tickAll() {
        for (TabAnimState state : TAB_OVERRIDES.values()) {
            if (!state.currentStack.isEmpty() && state.animTick < ANIM_DURATION) {
                state.animTick++;
            }
        }
    }

    private static float easeInOutExpo(float delta, float animAngle) {
        if (delta < 0.5f) {
            return easeInExpo(delta * 2f) * animAngle;
        } else {
            return animAngle + easeOutExpo((delta - 0.5f) * 2f) * animAngle;
        }
    }

    private static float easeInExpo(float t) {
        return t == 0f ? 0f : (float) Math.pow(2, 10 * t - 10);
    }

    private static float easeOutExpo(float t) {
        return t == 1f ? 1f : 1f - (float) Math.pow(2, -10 * t);
    }

    public static final class TabAnimState {
        public ItemStack currentStack = ItemStack.EMPTY;
        public ItemStack nextStack = ItemStack.EMPTY;
        float animTick = 0f;
        boolean swapped = false;

        public void startTransition(ItemStack next) {
            this.nextStack = next;
            this.animTick = 0f;
            this.swapped = false;
        }
    }
}