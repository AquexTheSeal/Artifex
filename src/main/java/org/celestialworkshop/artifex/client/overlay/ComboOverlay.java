package org.celestialworkshop.artifex.client.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.config.AFClientConfig;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class ComboOverlay {

    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        if (!AFClientConfig.COMBO_OVERLAY_RENDER.get()) {
            return;
        }

        Minecraft instance = Minecraft.getInstance();

        if (instance.player != null && ItemStackUtil.hasComboBasedWeapon(instance.player.getMainHandItem())) {
            AFEntityDataCapability.get(instance.player).ifPresent(cap -> {
                if (cap.comboCount > 0) {

                    int barLen = 48;
                    int barScale = Math.max(0, Mth.floor(barLen * ((cap.comboTimer - partialTick) / cap.getMaxComboTime())));
                    int barX = (screenWidth / 2) - (barLen / 2);
                    int barY = (screenHeight / 2) - 12;

                    guiGraphics.fill(barX, barY, barX + barScale, barY + 2, 0xAAFFFFFF);

                    guiGraphics.renderItem(cap.comboItemStack, barX, barY - 16);

                    String display = "" + cap.comboCount;
                    guiGraphics.drawString(gui.getFont(), display, barX + 16, barY - 8, 0xAAFFFFFF);
                }
            });
        }
    }
}
