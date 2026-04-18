package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import org.celestialworkshop.artifex.util.itemextension.AFExtension;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
 
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
 
    @Shadow
    public Input input;
 
    public LocalPlayerMixin(ClientLevel p_250460_, GameProfile p_249912_) {
        super(p_250460_, p_249912_);
    }
 
    @Inject(
            method = "aiStep",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    private void cancelItemUseSlow(CallbackInfo ci) {
        if (this.getUseItem().getItem() instanceof AFExtension ext) {
            this.input.leftImpulse *= ext.getItemUsingSlowdownMultiplier();
            this.input.forwardImpulse *= ext.getItemUsingSlowdownMultiplier();
        }
    }

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z",
                    ordinal = 1
            )
    )
    private boolean cancelItemUseSprintBlock(boolean original) {
        if (this.getUseItem().getItem() instanceof AFExtension shield) {
            if (shield.allowUseSprinting((LocalPlayer) (Object) this)) {
                return false;
            }
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "canStartSprinting",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"
            )
    )
    private boolean allowSprintingInCanStart(boolean original) {
        if (this.getUseItem().getItem() instanceof AFExtension shield) {
            if (shield.allowUseSprinting((LocalPlayer) (Object) this)) {
                return false;
            }
        }
        return original;
    }

    @WrapWithCondition(
            method = "aiStep",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private boolean shouldSetSprintTriggerTime(LocalPlayer instance, int value) {
        if (this.getUseItem().getItem() instanceof AFExtension shield) {
            return !shield.allowUseSprinting((LocalPlayer) (Object) this);
        }
        return true;
    }
}
