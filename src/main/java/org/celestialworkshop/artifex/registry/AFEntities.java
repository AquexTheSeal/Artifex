package org.celestialworkshop.artifex.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;

public class AFEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Artifex.MODID);

    public static final RegistryObject<EntityType<ThrownWeaponProjectile>> THROWABLE_PROJECTILE = ENTITIES.register("throwable_projectile",
            () -> EntityType.Builder.<ThrownWeaponProjectile>of(ThrownWeaponProjectile::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .updateInterval(10)
                    .build("throwable_projectile")
    );
}
