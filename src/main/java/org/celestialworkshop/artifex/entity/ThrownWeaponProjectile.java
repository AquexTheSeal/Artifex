package org.celestialworkshop.artifex.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.registry.AFEntities;
import org.celestialworkshop.artifex.util.ItemStackUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThrownWeaponProjectile extends AbstractArrow {

    public static final EntityDataAccessor<ItemStack> HELD_STACK = SynchedEntityData.defineId(ThrownWeaponProjectile.class, EntityDataSerializers.ITEM_STACK);
    public @Nullable SoundEvent hitSound;

    public ThrownWeaponProjectile(EntityType<? extends ThrownWeaponProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownWeaponProjectile(Level level, LivingEntity shooter) {
        super(AFEntities.THROWABLE_PROJECTILE.get(), shooter, level);
    }

    public ThrownWeaponProjectile(Level level, double x, double y, double z) {
        super(AFEntities.THROWABLE_PROJECTILE.get(), x, y, z, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        Entity owner = this.getOwner();
        DamageSource damagesource;
        if (owner == null) {
            damagesource = this.damageSources().arrow(this, this);
        } else {
            damagesource = this.damageSources().arrow(this, owner);
            if (owner instanceof LivingEntity le) {
                le.setLastHurtMob(owner);
            }
        }

        float damage = (float) this.getBaseDamage();
        if (this.getOwner() instanceof LivingEntity leOwner && entity instanceof LivingEntity leTarget && this.getHeldStack().getItem() instanceof AFPropertyItem materialItem) {
            for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                damage = entry.getKey().onDamageRanged(leOwner, leTarget, this.getHeldStack(), this, damage, this.isCritArrow(), entry.getValue());
            }
        }

        if (entity.hurt(damagesource, damage)) {
            entity.invulnerableTime = 0;
            if (entity instanceof LivingEntity le) {
                if (!this.level().isClientSide && owner instanceof LivingEntity leOwner) {
                    EnchantmentHelper.doPostHurtEffects(le, leOwner);
                    EnchantmentHelper.doPostDamageEffects(leOwner, le);
                }

                this.doPostHurtEffects(le);

                if (this.getOwner() instanceof LivingEntity leOwner && entity instanceof LivingEntity leTarget && this.getHeldStack().getItem() instanceof AFPropertyItem materialItem) {
                    for (Map.Entry<AFSpecialty, Integer> entry : materialItem.getSpecialties().entrySet()) {
                        entry.getKey().onPostRanged(leOwner, leTarget, this.getHeldStack(), this, this.isCritArrow(), entry.getValue());
                    }

                    if (ItemStackUtil.hasComboBasedWeapon(this.getHeldStack())) {
                        ComboBasedSpecialty.manageComboStack(leOwner, this.getHeldStack());
                    }
                }

                this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            }
        } else {
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            }
        }

        if (owner != null && !this.level().isClientSide) {
            this.shootFromRotation(this, -15, -this.getYRot() + 180, 0.0F, 0.2F, 0.5F);
            this.hasImpulse = true;
        } else if (owner == null) {
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
        }
    }

    @Override
    protected @Nullable EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        EntityHitResult result = super.findHitEntity(pStartVec, pEndVec);
        if (result != null) {
            return result.getEntity() == this.getOwner() ? null : result;
        }
        return null;
    }

    public SoundEvent getHitSound() {
        if (this.hitSound != null) {
            return this.hitSound;
        }
        return SoundEvents.ARROW_HIT;
    }

    public void setHitSound(@Nullable SoundEvent hitSound) {
        this.hitSound = hitSound;
    }

    @Override
    protected boolean tryPickup(Player pPlayer) {
        if (this.pickup != Pickup.ALLOWED) {
            return super.tryPickup(pPlayer);
        }
        ItemStack pickupItem = this.getPickupItem();
        Inventory inventory = pPlayer.getInventory();

        List<Integer> slots = new ArrayList<>();
        slots.add(Inventory.SLOT_OFFHAND);
        slots.add(inventory.selected);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (i != Inventory.SLOT_OFFHAND && i != inventory.selected) {
                slots.add(i);
            }
        }

        for (int i : slots) {
            ItemStack stack = inventory.getItem(i);
            if (ItemStackUtil.sameItemMatchesEnchantments(pickupItem, stack)) {
                return AFAmmoDataCapability.get(stack).map(cap -> {
                    if (!cap.isFull(stack)) {
                        cap.add(stack, 1);
                        if (pPlayer instanceof ServerPlayer serverPlayer) {
                            AFNetwork.sendToPlayer(serverPlayer, new S2CSyncAmmoPacket(i, cap.getAmmo()));
                        }
                        return true;
                    }
                    return false;
                }).orElse(false);
            }
        }
        return false;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return this.getHeldStack();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HELD_STACK, ItemStack.EMPTY);
    }

    public ItemStack getHeldStack() {
        return this.entityData.get(HELD_STACK);
    }

    public void setHeldStack(ItemStack heldStack) {
        this.entityData.set(HELD_STACK, heldStack);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("HeldItem", this.getHeldStack().save(new CompoundTag()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setHeldStack(ItemStack.of(pCompound.getCompound("HeldItem")));
    }
}
