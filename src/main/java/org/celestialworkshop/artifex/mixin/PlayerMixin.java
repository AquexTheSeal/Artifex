package org.celestialworkshop.artifex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.item.base.AFShieldItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow public abstract void setItemSlot(EquipmentSlot pSlot, ItemStack pStack);

    @Shadow
    public abstract Inventory getInventory();

    @Unique public boolean af$CriticalProcess = false;

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @WrapOperation(
            method = "disableShield",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"
            )
    )
    public void disableShield(ItemCooldowns instance, Item pItem, int pTicks, Operation<Void> original) {
        if (pItem instanceof AFShieldItem afShieldItem) {
            float mult = afShieldItem.getShieldDisableMultiplier((Player) (Object) this);
            if (mult > 0.0F) {
                original.call(instance, pItem, (int) (pTicks * mult));
            }
        } else {
            original.call(instance, pItem, pTicks);
        }
    }

    @Inject(method = "getItemBySlot", at = @At("HEAD"), cancellable = true)
    public void hideOffhand(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot == EquipmentSlot.OFFHAND) {
            ItemStack mainhand = this.getInventory().getSelected();
            boolean twoHandedMainHand = !mainhand.isEmpty() && ItemStackUtil.hasSpecialty(mainhand, AFSpecialties.TWO_HANDED.get());

            ItemStack offhand = this.getInventory().offhand.get(0);
            boolean twoHandedOffHand = !offhand.isEmpty() && ItemStackUtil.hasSpecialty(offhand, AFSpecialties.TWO_HANDED.get());

            if (twoHandedMainHand || twoHandedOffHand) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }

    @ModifyVariable(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            shift = At.Shift.BEFORE
    ), ordinal = 0)
    private float modifyMeleeDamagePostCalculations(float damage, Entity target) {
        float result = damage;
        if (target instanceof LivingEntity livingTarget) {
            Item item = this.artifex$getAttackingItemStack().getItem();

            if (AFWeaponType.getWeaponCategory(item) != AFWeaponType.Category.RANGED) {

                for (Map.Entry<AFSpecialty, Integer> entry : ItemStackUtil.getSpecialties(item).entrySet()) {
                    result = entry.getKey().onDamageMelee(this, livingTarget, this.artifex$getAttackingItemStack(), result, this.af$CriticalProcess, entry.getValue());
                }
            }
        }
        return result;
    }

    @WrapOperation(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
    ))
    private boolean modifySweepingDamage(LivingEntity sweepTarget, DamageSource source, float sweepDamage, Operation<Boolean> original, @Local(ordinal = 0) float originalDamage) {
        float result = sweepDamage;
        Item item = this.artifex$getAttackingItemStack().getItem();

        if (AFWeaponType.getWeaponCategory(item) != AFWeaponType.Category.RANGED) {

        for (Map.Entry<AFSpecialty, Integer> entry : ItemStackUtil.getSpecialties(item).entrySet()) {
            result = entry.getKey().onDamageSweep(this, sweepTarget, this.artifex$getAttackingItemStack(), result, originalDamage, entry.getValue());
        }
        }

        return original.call(sweepTarget, source, result);
    }

    @Inject(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V"
    ))
    private void triggerPostAttack(Entity pTarget, CallbackInfo ci) {
        if (pTarget instanceof LivingEntity livingTarget) {
            Item item = this.artifex$getAttackingItemStack().getItem();

            if (AFWeaponType.getWeaponCategory(item) != AFWeaponType.Category.RANGED) {

                if (ItemStackUtil.hasComboBasedWeapon(this.artifex$getAttackingItemStack())) {
                    ComboBasedSpecialty.manageComboStack(this, this.artifex$getAttackingItemStack());
                }

                for (Map.Entry<AFSpecialty, Integer> entry : ItemStackUtil.getSpecialties(item).entrySet()) {
                    entry.getKey().onPostMelee(this, livingTarget, this.artifex$getAttackingItemStack(), this.af$CriticalProcess, entry.getValue());
                }
            }

            this.af$CriticalProcess = false;
        }
    }

    @Inject(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/event/entity/player/CriticalHitEvent;getDamageModifier()F"
    ))
    private void enableAFCriticalProcess(Entity pTarget, CallbackInfo ci) {
        this.af$CriticalProcess = true;
    }

    @Unique
    private ItemStack artifex$getAttackingItemStack() {
        return this.getMainHandItem();
    }
}
