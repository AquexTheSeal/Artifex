package org.celestialworkshop.artifex.client.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class ComboIndicatorOverlay {

    protected static final ResourceLocation COMBO_INDICATOR_LOCATION = Artifex.prefix("textures/gui/combo_indicator.png");
    protected static final ResourceLocation COMBO_INDICATOR_COUNT_LOCATION = Artifex.prefix("textures/gui/combo_indicator_count.png");

    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        if (!AFClientConfig.COMBO_OVERLAY_RENDER.get()) {
            return;
        }

        Minecraft instance = Minecraft.getInstance();

        if (instance.player != null && ItemStackUtil.hasComboBasedWeapon(instance.player.getMainHandItem())) {
            AFEntityDataCapability.get(instance.player).ifPresent(cap -> {

                int cx = (screenWidth / 2) + AFClientConfig.COMBO_OVERLAY_X_OFFSET.get();
                int cy = (screenHeight / 2) + AFClientConfig.COMBO_OVERLAY_Y_OFFSET.get();

                if (cap.comboCount > 0) {

                    int barLen = 48;
                    int barScale = Math.max(0, Mth.floor(barLen * ((cap.comboTimer - partialTick) / cap.maxComboTimer)));

                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.enableBlend();

                    int xx = cx - (barLen / 2);
                    int yy = cy - 24;
                    guiGraphics.blit(COMBO_INDICATOR_LOCATION, xx, yy, 0, 0, barScale, 16, barLen, 16);

                    if (cap.comboCount > 0) {
                        for (int i = 0; i < cap.comboCount; i++) {
                            guiGraphics.blit(COMBO_INDICATOR_COUNT_LOCATION, xx + i * 10, yy - 8, 0, 0, 8, 8, 8, 8);
                        }
                    }

                    RenderSystem.defaultBlendFunc();
                    poseStack.popPose();
                }
            });
        }
    }
}
