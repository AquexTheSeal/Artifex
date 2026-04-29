package org.celestialworkshop.artifex.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.entity.Bolt;
import org.jetbrains.annotations.NotNull;

public class BoltItem extends ArrowItem {

    public BoltItem(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level pLevel, @NotNull ItemStack pStack, @NotNull LivingEntity pShooter) {
        return new Bolt(pShooter, pLevel);
    }
}
