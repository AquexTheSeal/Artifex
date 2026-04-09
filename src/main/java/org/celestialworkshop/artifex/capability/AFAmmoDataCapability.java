package org.celestialworkshop.artifex.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AFAmmoDataCapability {
    public static final Capability<AFAmmoData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    public static LazyOptional<AFAmmoData> get(ItemStack stack) {
        return stack.getCapability(INSTANCE);
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private final AFAmmoData handler;
        private final LazyOptional<AFAmmoData> lazyHandler;

        public Provider(ItemStack stack) {
            this.handler = new AFAmmoData(stack);
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
        event.register(AFAmmoData.class);
    }
}
