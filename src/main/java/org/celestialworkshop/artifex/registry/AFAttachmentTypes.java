package org.celestialworkshop.artifex.registry;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityData;

public class AFAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Artifex.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AFEntityData>> ENTITY_DATA = ATTACHMENT_TYPES.register("entity_data",
            () -> AttachmentType.serializable(AFEntityData::new).build()
    );
}

