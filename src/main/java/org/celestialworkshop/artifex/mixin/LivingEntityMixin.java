package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import org.celestialworkshop.artifex.item.base.AFShieldItem;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Definition(id = "amount", local = @Local(type = float.class, argsOnly = true))
    @Expression("amount <= 0.0")
    @Inject(method = "hurt", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.BEFORE))
    private void shieldBlockEvent(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local ShieldBlockEvent event) {
        LivingEntity entity = (LivingEntity)(Object)this;
        ItemStack shield = entity.getUseItem();

        ItemStackUtil.getSpecialties(shield.getItem()).forEach((specialty, level) -> {
            specialty.onPostShieldBlock(entity, shield, source, amount, level);
        });

        if (shield.getItem() instanceof AFShieldItem shieldItem) {
            shieldItem.onShieldBlock(entity, shield, source, amount);
        }
    }
}
