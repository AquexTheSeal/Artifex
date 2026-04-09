package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {

    @Shadow
    public abstract boolean isCritArrow();

    protected AbstractArrowMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @WrapOperation(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    protected boolean modifyArrowDamage(Entity target, DamageSource pSource, float pAmount, Operation<Boolean> original) {
        float result = pAmount;
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        ItemStack heldStack = AFEntityDataCapability.get(arrow).map(AFEntityData::getBoundItemStack).orElse(ItemStack.EMPTY);

        if (!heldStack.isEmpty() &&
                this.getOwner() instanceof LivingEntity leOwner &&
                target instanceof LivingEntity leTarget &&
                heldStack.getItem() instanceof AFPropertyItem materialItem
        ) {
            for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                result = entry.getKey().onDamageRanged(leOwner, leTarget, heldStack, arrow, result, this.isCritArrow(), entry.getValue());
            }
        }

        return original.call(target, pSource, result);
    }

    @Inject(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;)V"
            )
    )
    protected void addPostHurtArrow(EntityHitResult pResult, CallbackInfo ci, @Local(ordinal = 0) Entity target) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        ItemStack heldStack = AFEntityDataCapability.get(arrow).map(AFEntityData::getBoundItemStack).orElse(ItemStack.EMPTY);

        if (!heldStack.isEmpty() &&
                this.getOwner() instanceof LivingEntity leOwner &&
                target instanceof LivingEntity leTarget &&
                heldStack.getItem() instanceof AFPropertyItem materialItem
        ) {
            for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                entry.getKey().onPostRanged(leOwner, leTarget, heldStack, arrow, this.isCritArrow(), entry.getValue());
            }
        }
    }
}
