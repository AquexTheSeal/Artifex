package org.celestialworkshop.artifex.client.itemdecoration;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class AFAmmoDecoration implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (!ItemStackUtil.isFull(stack)) {
            int barWidth = (int) (13.0F * (float) ItemStackUtil.getAmmo(stack) / (float) ItemStackUtil.getMaxAmmo(stack));

            int barY = yOffset + 1;
            int barX = xOffset + 2;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 200);

            guiGraphics.fill(barX, barY, barX + 13, barY + 2, 0xFF000000);

            guiGraphics.fill(barX, barY, barX + 13, barY + 1, ItemStackUtil.isEmpty(stack) ? 0xFFff0000 : 0xFF000000);
            guiGraphics.fill(barX, barY, barX + barWidth, barY + 1, 0xFF4FC3F7);

            guiGraphics.pose().popPose();
        }

        return false;
    }
}
