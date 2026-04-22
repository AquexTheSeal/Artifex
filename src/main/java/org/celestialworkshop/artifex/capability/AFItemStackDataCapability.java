package org.celestialworkshop.artifex.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AFItemStackDataCapability {
    public static final Capability<AFItemStackData> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){});

    public static LazyOptional<AFItemStackData> get(ItemStack stack) {
        return stack.getCapability(INSTANCE);
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private final AFItemStackData handler;
        private final LazyOptional<AFItemStackData> lazyHandler;

        public Provider(ItemStack stack) {
            this.handler = new AFItemStackData(stack);
            this.lazyHandler = LazyOptional.of(() -> handler);
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return INSTANCE.orEmpty(cap, lazyHandler);
        }

        @Override
        public CompoundTag serializeNBT() {
            return handler.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            handler.deserializeNBT(nbt);
        }

        public void invalidate() {
            lazyHandler.invalidate();
        }
    }

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(AFItemStackData.class);
    }
}
