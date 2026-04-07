package org.celestialworkshop.artifex.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class AFAmmoData implements INBTSerializable<CompoundTag> {

    private final ItemStack itemStack;
    private final int maxAmmo;

    private int ammo = 0;

    public AFAmmoData(ItemStack stack, int maxAmmo) {
        itemStack = stack;
        this.maxAmmo = maxAmmo;
        this.setAmmo(this.maxAmmo);
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int count) {
        this.ammo = count;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void consume(int amount) {
        setAmmo(Math.max(0, getAmmo() - amount));
    }

    public void add(int amount) {
        setAmmo(Math.min(getMaxAmmo(), getAmmo() + amount));
    }

    public boolean isEmpty() {
        return getAmmo() <= 0;
    }

    public boolean isFull() {
        return getAmmo() >= getMaxAmmo();
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
