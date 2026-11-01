package org.celestialworkshop.artifex.client.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class ComboIndicatorOverlay {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (AFClientConfig.COMBO_OVERLAY_RENDER.get()) {
            Minecraft instance = Minecraft.getInstance();

            if (instance.player != null && ItemStackUtil.hasComboBasedWeapon(instance.player.getMainHandItem())) {
                AFEntityData cap = instance.player.getData(AFAttachmentTypes.ENTITY_DATA);

                int cx = (guiGraphics.guiWidth() / 2) + AFClientConfig.COMBO_OVERLAY_X_OFFSET.get();
                int cy = (guiGraphics.guiHeight() / 2) + AFClientConfig.COMBO_OVERLAY_Y_OFFSET.get();

                if (cap.comboCount > 0) {
                    float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
                    int barLen = 48;
                    int barScale = Math.max(0, Mth.floor(barLen * ((cap.comboTimer - partialTick) / cap.maxComboTimer)));

                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.enableBlend();

                    int xx = cx - (barLen / 2);
                    int yy = cy - 24;
                    guiGraphics.blit(Artifex.prefix("textures/gui/combo_indicator.png"), xx, yy, 0, 0, barScale, 16, barLen, 16);

                    for (int i = 0; i < cap.comboCount; i++) {
                        guiGraphics.blit(Artifex.prefix("textures/gui/combo_indicator_count.png"), xx + i * 10, yy - 8, 0, 0, 8, 8, 8, 8);
                    }

                    RenderSystem.defaultBlendFunc();
                    poseStack.popPose();
                }
            }
        }
    }
}
