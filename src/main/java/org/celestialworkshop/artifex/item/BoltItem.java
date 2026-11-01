package org.celestialworkshop.artifex.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.entity.Bolt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoltItem extends ArrowItem {

    public BoltItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level pLevel, @NotNull ItemStack pAmmo, @NotNull LivingEntity pShooter, @Nullable ItemStack pWeapon) {
        return new Bolt(pShooter, pLevel, pAmmo.copyWithCount(1), pWeapon);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level pLevel, @NotNull Position pPos, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        Bolt bolt = new Bolt(pPos.x(), pPos.y(), pPos.z(), pLevel, pStack.copyWithCount(1), null);
        bolt.pickup = AbstractArrow.Pickup.ALLOWED;
        return bolt;
    }
}
