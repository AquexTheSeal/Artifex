package org.celestialworkshop.artifex.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.item.base.AFCrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    private static boolean isChargedCrossbow(ItemStack pStack) {
        return false;
    }

    @Shadow
    private static ItemInHandRenderer.HandRenderSelection selectionUsingItemWhileHoldingBowLike(LocalPlayer pPlayer) {
        return null;
    }

    @Inject(method = "isChargedCrossbow", at = @At(value = "RETURN"), cancellable = true)
    private static void isChargedCrossbow(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || (pStack.getItem() instanceof AFCrossbowItem && CrossbowItem.isCharged(pStack)));
    }

    @Inject(method = "evaluateWhichHandsToRender", at = @At(value = "HEAD"), cancellable = true)
    private static void evaluateWhichHandsToRender(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack mainItem = pPlayer.getMainHandItem();
        ItemStack offItem = pPlayer.getOffhandItem();

        boolean flag1 = mainItem.getItem() instanceof AFCrossbowItem || offItem.getItem() instanceof AFCrossbowItem;
        if (flag1) {
            if (pPlayer.isUsingItem()) {
                cir.setReturnValue(selectionUsingItemWhileHoldingBowLike(pPlayer));
            } else {
                cir.setReturnValue(isChargedCrossbow(mainItem) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
            }
        }
    }

    @Inject(method = "selectionUsingItemWhileHoldingBowLike", at = @At(value = "HEAD"), cancellable = true)
    private static void selectionUsingItemWhileHoldingBowLike(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = pPlayer.getUseItem();
        InteractionHand interactionhand = pPlayer.getUsedItemHand();
        if (itemstack.getItem() instanceof AFCrossbowItem) {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand));
        }
    }
}
