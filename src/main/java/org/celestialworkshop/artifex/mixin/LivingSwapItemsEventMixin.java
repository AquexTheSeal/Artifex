package org.celestialworkshop.artifex.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingSwapItemsEvent.Hands.class)
public class LivingSwapItemsEventMixin {

    @Shadow
    private ItemStack toMainHand;

    @Shadow
    private ItemStack toOffHand;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    public void modifyItemsToSwap(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof Player pl) {
            this.toMainHand = pl.getInventory().offhand.get(0);
            this.toOffHand = pl.getInventory().getSelected();
        }
    }
}
