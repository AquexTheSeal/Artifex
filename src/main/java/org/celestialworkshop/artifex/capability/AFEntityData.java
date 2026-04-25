package org.celestialworkshop.artifex.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.item.specialty.IaijutsuSpecialty;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;

import java.util.Map;

public class AFEntityData implements INBTSerializable<CompoundTag> {

    private ItemStack boundItemStack = ItemStack.EMPTY;

    public ItemStack comboItemStack = ItemStack.EMPTY;
    public int comboCount;
    public int comboTimer;
    public int maxComboTimer;

    public ItemStack iaijutsuItemStack = ItemStack.EMPTY;
    public int iaijutsuTimer = 0;
    public boolean iaijutsuSpeedUp = false;

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
        Map<AFSpecialty, Integer> specialties = ItemStackUtil.getSpecialties(iaijutsuItemStack.getItem());
        AFSpecialty iaijutsu = AFSpecialties.IAIJUTSU.get();
        Integer level = specialties.get(iaijutsu);
        if (level != null) {
            return ((IaijutsuSpecialty) iaijutsu).calculateTime(level);
        }
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
