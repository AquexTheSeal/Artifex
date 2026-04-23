package org.celestialworkshop.artifex.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class AFEntityData implements INBTSerializable<CompoundTag> {

    private ItemStack boundItemStack = ItemStack.EMPTY;

    public ItemStack comboItemStack = ItemStack.EMPTY;
    public int comboCount;
    public int comboTimer;
    public int maxComboTimer;

    public ItemStack iaijutsuItemStack = ItemStack.EMPTY;
    public int iaijutsuTimer = 0;

    // BOUND STACK
    public void setBoundItemStack(ItemStack boundItemStack) {
        this.boundItemStack = boundItemStack;
    }

    public ItemStack getBoundItemStack() {
        return boundItemStack;
    }

    // COMBO COUNTER
    public int getMaxComboCount() {
        return 5;
    }

    // IAIJUTSU
    public int getMaxIaijutsuTime() {
        return 100;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("BoundItem", boundItemStack.save(new CompoundTag()));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        boundItemStack = ItemStack.of(nbt.getCompound("BoundItem"));
    }
}
