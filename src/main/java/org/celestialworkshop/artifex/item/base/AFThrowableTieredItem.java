package org.celestialworkshop.artifex.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.registry.AFDataComponents;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.registry.AFSoundEvents;
import org.celestialworkshop.artifex.util.ItemStackUtil;

import java.util.List;

public class AFThrowableTieredItem extends AFTieredItem {

    public final int throwMaxTicks;
    public final float thrownBaseDamage;
    public final float baseVelocity;

    public AFThrowableTieredItem(AFMaterial material, float attackDamage, float attackDamageTierScale, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep, int throwMaxTicks, float thrownBaseDamage, float baseVelocity) {
        super(material, material.getItemPropertiesSupplier().get().component(AFDataComponents.AMMO.get(), 16), attackDamage, attackDamageTierScale, attackSpeed, movementSpeedPercent, reach, canSweep);
        this.throwMaxTicks = throwMaxTicks;
        this.thrownBaseDamage = thrownBaseDamage;
        this.baseVelocity = baseVelocity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        if (!ItemStackUtil.isEmpty(item)) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(item);
        } else {
            return InteractionResultHolder.fail(item);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int timeElapsed = this.getUseDuration(pStack, pLivingEntity) - pTimeLeft;
        if (pLivingEntity instanceof Player player) {
            pStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

            ThrownWeaponProjectile projectile = new ThrownWeaponProjectile(pLevel, player, pStack.copy());

            float delta = (float) timeElapsed / this.getThrowMaxTicks();

            float maxVelocity = this.getBaseVelocity();
            float calculatedVelocity = Math.min(delta * maxVelocity, maxVelocity);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, calculatedVelocity, 1.0F);

            float maxDamage = this.getThrownBaseDamage() + this.getMaterial().getItemTier().getAttackDamageBonus() * 1.12F;
            float punctureBonus = ItemStackUtil.getEnchantmentLevel(pStack, AFEnchantments.PUNCTURE) * 0.75F;
            float calculatedDamage = Math.min(delta * maxDamage, maxDamage);
            projectile.setBaseDamage(calculatedDamage + punctureBonus);

            if (player.getAbilities().instabuild) {
                projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            pLevel.addFreshEntity(projectile);
            pLevel.playSound(null, projectile, AFSoundEvents.THROWABLE_THROWN.get(), SoundSource.PLAYERS, 1.0F, 1.25F);

            if (!player.getAbilities().instabuild) {
                ItemStackUtil.consumeAmmo(pStack, 1);
                if (player instanceof ServerPlayer serverPlayer) {
                    int usedIdx = player.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getInventory().selected : Inventory.SLOT_OFFHAND;
                    AFNetwork.sendToPlayer(serverPlayer, new S2CSyncAmmoPacket(usedIdx, ItemStackUtil.getAmmo(pStack)));
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public int getThrowMaxTicks() {
        return throwMaxTicks;
    }

    public int getMaximumAmmo(ItemStack stack) {
        int stockpileLevel = ItemStackUtil.getEnchantmentLevel(stack, AFEnchantments.STOCKPILE);
        if (stockpileLevel > 0) {
            return 16 + stockpileLevel * 8;
        } else {
            return 16;
        }
    }

    public float getThrownBaseDamage() {
        return thrownBaseDamage;
    }

    public float getBaseVelocity() {
        return baseVelocity;
    }

    public float getRenderScale() {
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type == AFWeaponType.SPEAR || type == AFWeaponType.JAVELIN) {
            return 3.0F;
        } else {
            return 2.0F;
        }
    }

    public float getRenderTranslationOffset() {
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type == AFWeaponType.SPEAR || type == AFWeaponType.JAVELIN) {
            return -0.35F;
        } else {
            return -0.2F;
        }
    }

    @Override
    public boolean allowUseSprinting(Player player) {
        return AFWeaponType.isWeaponType(this, AFWeaponType.DAGGER);
    }

    @Override
    public float getItemUsingSlowdownMultiplier() {
        if (AFWeaponType.isWeaponType(this, AFWeaponType.DAGGER)) {
            return 5.0F;
        } else {
            return 3.5F;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("tooltip.artifex.throwable_description").withStyle(ChatFormatting.DARK_GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.artifex.throwable_max_throw_time", this.getThrowMaxTicks() / 20.0).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.artifex.throwable_ammo_stack", ItemStackUtil.getAmmo(pStack), ItemStackUtil.getMaxAmmo(pStack)).withStyle(ChatFormatting.GRAY));
    }
}
