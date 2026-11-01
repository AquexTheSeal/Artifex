package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import org.celestialworkshop.artifex.item.base.AFShieldItem;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapOperation(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/common/CommonHooks;onDamageBlock(Lnet/minecraft/world/entity/LivingEntity;Lnet/neoforged/neoforge/common/damagesource/DamageContainer;Z)Lnet/neoforged/neoforge/event/entity/living/LivingShieldBlockEvent;"
            )
    )
    private LivingShieldBlockEvent shieldBlockEvent(LivingEntity blocker, DamageContainer container, boolean originalBlocked, Operation<LivingShieldBlockEvent> original, DamageSource source, float amount) {
        LivingShieldBlockEvent event = original.call(blocker, container, originalBlocked);
        if (event.getBlocked()) {
            ItemStack shield = blocker.getUseItem();

            ItemStackUtil.getSpecialties(shield.getItem()).forEach((specialty, level) -> {
                specialty.onPostShieldBlock(blocker, shield, source, amount, level);
            });

            if (shield.getItem() instanceof AFShieldItem shieldItem) {
                shieldItem.onShieldBlock(blocker, shield, source, amount);
            }
        }
        return event;
    }
}
