package org.celestialworkshop.artifex.registry;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;

public class AFAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Artifex.MODID);

    public static final RegistryObject<Attribute> DAMAGE_REDUCTION = ATTRIBUTES.register("damage_reduction", () -> new RangedAttribute(
            "attribute.name.artifex.damage_reduction", 0.0D, 0.0D, 1.0D).setSyncable(true)
    );
}
