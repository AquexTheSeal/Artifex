package org.celestialworkshop.artifex.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AFCreativeTabOverride {

    private static final float ANIM_DURATION = 20f;
    private static final float ANIM_ANGLE = 180f;

    public static ItemStack currentStack = ItemStack.EMPTY;
    public static ItemStack nextStack = ItemStack.EMPTY;

    private static float animTick = 0f;
    private static boolean swapped = false;

    public static boolean renderAnimatedIcon(GuiGraphics pGuiGraphics, CreativeModeTab pCreativeModeTab, Font font, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        if (currentStack.isEmpty() || mc.player == null) return false;

        float partial = mc.getPartialTick();
        float delta = Math.min((animTick + partial) / ANIM_DURATION, 1f);

        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(x + 8, y + 8, 0);

        if (delta >= 0.5f && !swapped) {
            currentStack = nextStack;
            swapped = true;
        }

        float angle;
        if (delta < 0.5f) {
            float e = easeInExpo(delta * 2f);
            angle = e * ANIM_ANGLE;
        } else {
            float e = easeOutExpo((delta - 0.5f) * 2f);
            angle = ANIM_ANGLE + (e * ANIM_ANGLE);
        }

        pose.mulPose(Axis.ZP.rotationDegrees(angle));
        pGuiGraphics.renderItem(currentStack, -8, -8);
        pGuiGraphics.renderItemDecorations(font, currentStack, -8, -8);

        pose.popPose();

        return true;
    }

    public static void tick() {
        if (currentStack.isEmpty()) return;
        if (animTick < ANIM_DURATION) {
            animTick++;
        }
    }

    public static void startTransition(ItemStack next) {
        nextStack = next;
        animTick = 0f;
        swapped = false;
    }

    private static float easeInExpo(float t) {
        return t == 0f ? 0f : (float) Math.pow(2, 10 * t - 10);
    }

    private static float easeOutExpo(float t) {
        return t == 1f ? 1f : 1f - (float) Math.pow(2, -10 * t);
    }
}
