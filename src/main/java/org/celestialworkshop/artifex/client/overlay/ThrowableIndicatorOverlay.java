package org.celestialworkshop.artifex.client.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;

public class ThrowableIndicatorOverlay {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (AFClientConfig.THROWABLE_OVERLAY_RENDER.get()) {
            Minecraft instance = Minecraft.getInstance();
            Options options = instance.options;
            Player player = instance.player;

            if (player != null && options.getCameraType().isFirstPerson()) {
                ItemStack usedItem = player.getUseItem();

                if (usedItem.getItem() instanceof AFThrowableTieredItem throwable) {
                    int cx = (guiGraphics.guiWidth() / 2) + AFClientConfig.THROWABLE_OVERLAY_X_OFFSET.get();
                    int cy = (guiGraphics.guiHeight() / 2) + AFClientConfig.THROWABLE_OVERLAY_Y_OFFSET.get();

                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.enableBlend();

                    int textureSize = 16;
                    float ratio = Math.min(1f, player.getTicksUsingItem() / (float) throwable.getThrowMaxTicks());
                    int textureProgress = (int) (textureSize * ratio);

                    if (textureProgress > 0) {
                        int barX = cx + 8;
                        int barBottom = cy + 8;
                        int barTop = barBottom - textureProgress;
                        int vOffset = textureSize - textureProgress;

                        if (textureProgress >= textureSize) {
                            RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 1.0F);
                        }
                        guiGraphics.blit(Artifex.prefix("textures/gui/throwable_indicator.png"), barX, barTop, 0, vOffset, textureSize, textureProgress, textureSize, textureSize);
                        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    }

                    RenderSystem.defaultBlendFunc();
                    poseStack.popPose();
                }
            }
        }
    }
}
