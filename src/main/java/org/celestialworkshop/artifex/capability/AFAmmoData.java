package org.celestialworkshop.artifex.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;

public class AFAmmoData implements INBTSerializable<CompoundTag> {

    private int ammo = 0;

    public AFAmmoData(ItemStack stack) {
        this.setAmmo(this.getMaxAmmo(stack));
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int count) {
        this.ammo = count;
    }

    public int getMaxAmmo(ItemStack stack) {
        return stack.getItem() instanceof AFThrowableTieredItem throwable ? throwable.getMaximumAmmo(stack) : 0;
    }

    public void consume(int amount) {
        setAmmo(Math.max(0, getAmmo() - amount));
    }

    public void add(ItemStack stack, int amount) {
        setAmmo(Math.min(getMaxAmmo(stack), getAmmo() + amount));
    }

    public boolean isEmpty() {
        return getAmmo() <= 0;
    }

    public boolean isFull(ItemStack stack) {
        return getAmmo() >= getMaxAmmo(stack);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Ammo", ammo);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.ammo = nbt.getInt("Ammo");
    }
}
