package org.celestialworkshop.artifex.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.celestialworkshop.artifex.registry.AFEntities;
import org.celestialworkshop.artifex.registry.AFItems;
import org.jetbrains.annotations.NotNull;

public class Bolt extends AbstractArrow {

    public Bolt(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Bolt(double pX, double pY, double pZ, Level pLevel) {
        super(AFEntities.BOLT.get(), pX, pY, pZ, pLevel);
    }

    public Bolt(LivingEntity pShooter, Level pLevel) {
        super(AFEntities.BOLT.get(), pShooter, pLevel);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(AFItems.BASIC_BOLT.get());
    }

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity * this.getVelocityMultiplier(), pInaccuracy);
        this.hasImpulse = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        this.setBaseDamage(this.getBaseDamage() / this.getVelocityMultiplier());
        super.onHitEntity(pResult);
    }

    public float getVelocityMultiplier() {
        return 10.0F;
    }
}
