package org.celestialworkshop.artifex.client.tooltip;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.*;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Map;

public record SpecialtyTooltip(Map<AFSpecialty, Integer> values) implements TooltipComponent, ClientTooltipComponent {

    @Override
    public int getHeight() {
        Font font = Minecraft.getInstance().font;
        int rY = font.lineHeight * 2;
        for (Map.Entry<AFSpecialty, Integer> entry : values.entrySet()) {
            rY += font.lineHeight;
            if (this.shouldShowDescription()) {
                rY += font.wordWrapHeight(entry.getKey().getDisplayDescription(entry.getValue()), 200);
            }
        }
        return rY;
    }

    @Override
    public int getWidth(Font pFont) {
        int largest = pFont.width(this.getSpecialityTitle());

        for (Map.Entry<AFSpecialty, Integer> entry : values.entrySet()) {
            largest = Math.max(largest, pFont.width(entry.getKey().getDisplayName(entry.getValue())) + pFont.lineHeight + 2);
            if (this.shouldShowDescription()) {
                List<FormattedText> descLines = pFont.getSplitter().splitLines(entry.getKey().getDisplayDescription(entry.getValue()), 200, Style.EMPTY);
                for (FormattedText line : descLines) {
                    largest = Math.max(largest, pFont.width(line) + pFont.lineHeight + 2);
                }
            }
        }

        return largest;
    }

    @Override
    public void renderText(Font pFont, int pX, int pY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        int rY = pY + pFont.lineHeight;

        pGuiGraphics.drawString(pFont, this.getSpecialityTitle(), pX, rY, 0xFFFFFF);

        for (Map.Entry<AFSpecialty, Integer> entry : values.entrySet()) {
            rY += pFont.lineHeight;

            MutableComponent name = entry.getKey().getDisplayName(entry.getValue());
            MutableComponent description = entry.getKey().getDisplayDescription(entry.getValue());

            int intColor = entry.getKey().getCategory().getColor().getColor().intValue();
            pGuiGraphics.drawString(pFont, name, pX + pFont.lineHeight + 2, rY, intColor);

            float r = FastColor.ARGB32.red(intColor) / 255.0F;
            float g = FastColor.ARGB32.green(intColor) / 255.0F;
            float b = FastColor.ARGB32.blue(intColor) / 255.0F;
            pGuiGraphics.setColor(r, g, b, 1.0F);
            pGuiGraphics.blit(entry.getKey().getIcon(), pX, rY, 0, 0, 8, 8, 8, 8);
            pGuiGraphics.setColor(1f, 1f, 1f, 1f);

            if (this.shouldShowDescription()) {
                rY += pFont.lineHeight;
                pGuiGraphics.drawWordWrap(pFont, description, pX + pFont.lineHeight + 2, rY, 200, 0x636363);
                rY += pFont.wordWrapHeight(description, 200) - pFont.lineHeight;
            }
        }
    }

    public Component getSpecialityTitle() {
        return Component.translatable("tooltip.artifex.specialty")
                .withStyle(style -> style.withColor(TextColor.fromRgb(0x6e6e6e)))
                .append(Component.literal(" [Shift]")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(0x4d4d4d)))
                );
    }

    public boolean shouldShowDescription() {
        return Screen.hasShiftDown() || Screen.hasControlDown();
    }
}
