package org.celestialworkshop.artifex.mixin;

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
    private void modifyItemUseSlowdown(CallbackInfo ci) {
        if (this.getUseItem().getItem() instanceof AFExtension ext) {
            this.input.leftImpulse *= ext.getItemUsingSlowdownMultiplier();
            this.input.forwardImpulse *= ext.getItemUsingSlowdownMultiplier();
        }
    }
}
