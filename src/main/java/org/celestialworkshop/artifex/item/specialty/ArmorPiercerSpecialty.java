package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;

import java.text.DecimalFormat;

public class ArmorPiercerSpecialty extends AFSpecialty {
    public ArmorPiercerSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        float armor = target.getArmorValue();
        float bonus = armor * calculateArmorToPowerRatio(specialityLevel);
        return originalDamage + bonus;
    }

    @Override
    public float onDamageRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, float originalDamage, boolean wasCrit, int specialityLevel) {
        float armor = target.getArmorValue();
        float bonus = armor * calculateArmorToPowerRatio(specialityLevel);
        return originalDamage + bonus;
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                new DecimalFormat("0.00").format(calculateArmorToPowerRatio(level))
        };
    }

    private float calculateArmorToPowerRatio(int level) {
        return 0.15F + (level * 0.15f);
    }
}
