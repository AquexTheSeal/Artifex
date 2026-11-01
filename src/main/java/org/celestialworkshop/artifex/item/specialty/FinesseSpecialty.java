package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;

public class FinesseSpecialty extends ComboBasedSpecialty {

    public FinesseSpecialty(Category category) {
        super(category);
    }

    @Override
    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        if (attacker instanceof Player pl) {
            if (pl.getAttackStrengthScale(0) > 0.9) {
                target.invulnerableTime = 0;
            }
        }
        return super.onDamageMelee(attacker, target, itemStack, originalDamage, wasCrit, specialityLevel);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        manageSpeed(attacker, specialityLevel);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        manageSpeed(attacker, specialityLevel);
    }

    private void manageSpeed(LivingEntity attacker, int specialityLevel) {
        if (attacker instanceof ServerPlayer) {
            AFEntityData entityData = attacker.getData(AFAttachmentTypes.ENTITY_DATA);

            AttributeInstance atkSpd = attacker.getAttribute(Attributes.ATTACK_SPEED);
            if (atkSpd != null) {
                atkSpd.removeModifier(Artifex.prefix("finesse_attack_speed_bonus"));
                double amount = this.calculateSpeedIncrement(specialityLevel) * entityData.comboCount;
                AttributeModifier modifier = new AttributeModifier(Artifex.prefix("finesse_attack_speed_bonus"), amount, AttributeModifier.Operation.ADD_VALUE);
                atkSpd.addTransientModifier(modifier);
            }
        }
    }

    @Override
    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {
        AttributeInstance atkSpd = attacker.getAttribute(Attributes.ATTACK_SPEED);
        if (atkSpd != null) {
            atkSpd.removeModifier(Artifex.prefix("finesse_attack_speed_bonus"));
        }
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(calculateSpeedIncrement(level))
        };
    }

    private float calculateSpeedIncrement(int level) {
        return level * 0.1f;
    }
}
