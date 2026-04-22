package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.celestialworkshop.artifex.client.AFCreativeTabOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Inject(
            method = "renderTabButton",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/CreativeModeTab;getIconItem()Lnet/minecraft/world/item/ItemStack;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    protected void overrideDisplayItem(GuiGraphics pGuiGraphics, CreativeModeTab pCreativeModeTab, CallbackInfo ci, @Local(ordinal = 3) int x, @Local(ordinal = 4) int y) {
        if (AFCreativeTabOverride.renderAnimatedIcon(pGuiGraphics, pCreativeModeTab, font, x, y)) {
            ci.cancel();
        }
    }
}
