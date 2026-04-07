package org.celestialworkshop.artifex.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AFEntityDataCapability {
    public static final Capability<AFEntityData> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){});

    public static LazyOptional<AFEntityData> get(Entity entity) {
        return entity.getCapability(INSTANCE);
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private final AFEntityData handler = new AFEntityData();
        private final LazyOptional<AFEntityData> lazyHandler = LazyOptional.of(() -> handler);

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
}
