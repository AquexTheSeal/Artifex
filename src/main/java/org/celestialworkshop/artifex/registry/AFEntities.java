package org.celestialworkshop.artifex.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.entity.Bolt;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;

public class AFEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Artifex.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownWeaponProjectile>> THROWABLE_PROJECTILE = ENTITIES.register("throwable_projectile",
            () -> EntityType.Builder.<ThrownWeaponProjectile>of(ThrownWeaponProjectile::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .updateInterval(10)
                    .build("throwable_projectile")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<Bolt>> BOLT = ENTITIES.register("bolt",
            () -> EntityType.Builder.<Bolt>of(Bolt::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .updateInterval(10)
                    .build("bolt")
    );
}
