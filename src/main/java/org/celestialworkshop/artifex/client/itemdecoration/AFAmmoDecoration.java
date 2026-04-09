package org.celestialworkshop.artifex.client.itemdecoration;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;

public class AFAmmoDecoration implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        AFAmmoDataCapability.get(stack).ifPresent(cap -> {
            if (!cap.isFull(stack)) {
                int barWidth = (int) (13.0F * (float) cap.getAmmo() / (float) cap.getMaxAmmo(stack));

                int barY = yOffset + 1;
                int barX = xOffset + 2;

                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(0, 0, 200);

                guiGraphics.fill(barX, barY, barX + 13, barY + 2, 0xFF000000);

                guiGraphics.fill(barX, barY, barX + 13, barY + 1, cap.isEmpty() ? 0xFFff0000 : 0xFF000000);
                guiGraphics.fill(barX, barY, barX + barWidth, barY + 1, 0xFF4FC3F7);

                guiGraphics.pose().popPose();
            }
        });

        return false;
    }
}
