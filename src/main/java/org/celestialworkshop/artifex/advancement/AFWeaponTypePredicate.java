package org.celestialworkshop.artifex.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFWeaponType;

public record AFWeaponTypePredicate(AFWeaponType weaponType) implements ItemSubPredicate {

    public static final ItemSubPredicate.Type<AFWeaponTypePredicate> TYPE = new ItemSubPredicate.Type<>(
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.comapFlatMap(name -> {
                        AFWeaponType type = AFWeaponType.byName(name);
                        if (type != null) {
                            return DataResult.success(type);
                        } else {
                            return DataResult.error(() -> "Unknown AFWeaponType: " + name);
                        }
                    }, AFWeaponType::getName).fieldOf("weapon_type").forGetter(AFWeaponTypePredicate::weaponType)
            ).apply(instance, AFWeaponTypePredicate::new))
    );

    @Override
    public boolean matches(ItemStack stack) {
        return AFWeaponType.isWeaponType(stack.getItem(), weaponType);
    }
}
