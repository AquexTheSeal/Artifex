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
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class IaijutsuIndicatorOverlay {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (AFClientConfig.IAIJUTSU_OVERLAY_RENDER.get()) {
            Minecraft instance = Minecraft.getInstance();
            Options options = instance.options;
            Player player = instance.player;

            if (player != null && options.getCameraType().isFirstPerson()) {
                ItemStack mainHandItem = player.getMainHandItem();

                if (ItemStackUtil.hasSpecialty(mainHandItem, AFSpecialties.IAIJUTSU.get())) {
                    AFEntityData cap = player.getData(AFAttachmentTypes.ENTITY_DATA);
                    int cx = (guiGraphics.guiWidth() / 2) + AFClientConfig.IAIJUTSU_OVERLAY_X_OFFSET.get();
                    int cy = (guiGraphics.guiHeight() / 2) + AFClientConfig.IAIJUTSU_OVERLAY_Y_OFFSET.get();

                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.enableBlend();

                    float delta = 1.0F - (float) cap.iaijutsuTimer / cap.getMaxIaijutsuTime();
                    int width = (int) (delta * 32);

                    if (delta < 1.0F) {
                        RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
                    }
                    guiGraphics.blit(Artifex.prefix("textures/gui/iaijutsu_indicator.png"), cx - 16, cy + 16, 0, 0, width, 16, 32, 16);
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

                    RenderSystem.defaultBlendFunc();
                    poseStack.popPose();
                }
            }
        }
    }
}
