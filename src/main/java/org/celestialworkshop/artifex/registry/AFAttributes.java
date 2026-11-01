package org.celestialworkshop.artifex.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.celestialworkshop.artifex.Artifex;

public class AFAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Artifex.MODID);

    public static final DeferredHolder<Attribute, Attribute> DAMAGE_REDUCTION = ATTRIBUTES.register("damage_reduction", () -> new RangedAttribute(
            "attribute.name.artifex.damage_reduction", 0.0D, 0.0D, 1.0D).setSyncable(true)
    );
}
