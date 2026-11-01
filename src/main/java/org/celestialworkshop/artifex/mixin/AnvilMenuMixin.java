package org.celestialworkshop.artifex.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    @Shadow
    @Final
    private DataSlot cost;

    public AnvilMenuMixin(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
    }

    @Inject(method = "createResult", at = @At(value = "TAIL"))
    private void adjustAmmoReplenishment(CallbackInfo ci) {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);

        if (!left.isEmpty() && !right.isEmpty() && left.is(right.getItem()) && left.getItem() instanceof AFThrowableTieredItem) {
            if (!ItemStackUtil.isFull(left) || !ItemStackUtil.isFull(right)) {
                ItemStack resultStack = this.resultSlots.getItem(0);

                if (resultStack.isEmpty()) {
                    resultStack = left.copy();
                }

                int currentAmmo = ItemStackUtil.getAmmo(left);
                int additionalAmmo = ItemStackUtil.getAmmo(right);
                int maxAmmo = ItemStackUtil.getMaxAmmo(left);

                int newAmmo = Math.min(maxAmmo, currentAmmo + additionalAmmo);
                ItemStackUtil.setAmmo(resultStack, newAmmo);

                if (this.cost.get() < 1) {
                    this.cost.set(1);
                }

                this.resultSlots.setItem(0, resultStack);
            }
        }
    }
}
