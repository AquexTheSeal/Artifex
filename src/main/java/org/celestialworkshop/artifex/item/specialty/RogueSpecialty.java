package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class RogueSpecialty extends AFSpecialty {

    public RogueSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        if (ammo instanceof ThrownWeaponProjectile throwable && !throwable.isRogueProjectile) {
            ItemStack mainhand = attacker.getMainHandItem();
            ItemStack offhand = attacker.getOffhandItem();
            if (ItemStackUtil.sameItemMatchesEnchantments(mainhand, throwable.getHeldStack()) && ItemStackUtil.sameItemMatchesEnchantments(offhand, throwable.getHeldStack())) {
                ThrownWeaponProjectile projectile = new ThrownWeaponProjectile(ammo.level(), attacker);
                projectile.setHeldStack(throwable.getHeldStack().copy());
                projectile.shootFromRotation(attacker, attacker.getXRot(), attacker.getYRot(), 0.0F, (float)ammo.getDeltaMovement().length(), 1.0F);
                projectile.setBaseDamage(throwable.getBaseDamage() * this.getExtraDamageMultiplier(specialityLevel));
                projectile.isRogueProjectile = true;
                projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                ammo.level().addFreshEntity(projectile);
            }
        }
    }

    public float getExtraDamageMultiplier(int level) {
        return 0.5f + ((level - 1) * 0.25f);
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(this.getExtraDamageMultiplier(level))
        };
    }
}
