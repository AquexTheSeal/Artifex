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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.registry.AFEnchantments;
import org.celestialworkshop.artifex.registry.AFSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AFThrowableTieredItem extends AFTieredItem {

    public final float thrownBaseDamage;
    public final float baseVelocity;

    public AFThrowableTieredItem(AFMaterial material, float attackDamage, float attackSpeed, float movementSpeedPercent, float reach, boolean canSweep, float thrownBaseDamage, float baseVelocity, Supplier<Map<AFSpecialty, Integer>> specialtyMapSupplier) {
        super(material, attackDamage, attackSpeed, movementSpeedPercent, reach, canSweep, specialtyMapSupplier);
        this.thrownBaseDamage = thrownBaseDamage;
        this.baseVelocity = baseVelocity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        if (AFAmmoDataCapability.get(item).isPresent()) {
            if (!AFAmmoDataCapability.get(item).resolve().get().isEmpty()) {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(item);
            }
        }
        return InteractionResultHolder.fail(item);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int timeElapsed = this.getUseDuration(pStack) - pTimeLeft;
        if (pLivingEntity instanceof Player player) {
            pStack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));

            ThrownWeaponProjectile projectile = new ThrownWeaponProjectile(pLevel, player);
            float maxVelocity = this.getBaseVelocity();
            float calculatedVelocity = Math.min((timeElapsed / 20F) * maxVelocity, maxVelocity);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, calculatedVelocity, 1.0F);
            projectile.setHeldStack(pStack.copy());

            float punctureAdd = pStack.getEnchantmentLevel(AFEnchantments.PUNCTURE.get()) * 0.75F;
            projectile.setBaseDamage(this.getThrownBaseDamage() + punctureAdd + (this.getMaterial().getItemTier().getAttackDamageBonus() * 1.2F));

            if (player.getAbilities().instabuild) {
                projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            pLevel.addFreshEntity(projectile);
            pLevel.playSound(null, projectile, AFSoundEvents.THROWABLE_THROWN.get(), SoundSource.PLAYERS, 1.0F, 1.25F);
 
            if (!player.getAbilities().instabuild) {
                AFAmmoDataCapability.get(pStack).ifPresent(cap -> {
                    cap.consume(1);
                    if (player instanceof ServerPlayer serverPlayer) {
                        int usedIdx = player.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getInventory().selected : Inventory.SLOT_OFFHAND;
                        AFNetwork.sendToPlayer(serverPlayer, new S2CSyncAmmoPacket(usedIdx, cap.getAmmo()));
                    }
                });
            }
 
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public int getMaximumAmmo(ItemStack stack) {
        int stockpileLevel = stack.getEnchantmentLevel(AFEnchantments.STOCKPILE.get());
        if (stockpileLevel > 0) {
            return 16 + stockpileLevel * 8;
        }
        return 16;
    }

    public float getThrownBaseDamage() {
        return thrownBaseDamage;
    }

    public float getBaseVelocity() {
        return baseVelocity;
    }

    public float getRenderScale() {
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type != null) {
            switch (type) {
                case SPEAR, JAVELIN: {
                    return 3.0F;
                }
            }
        }
        return 2.0F;
    }

    public float getRenderTranslationOffset() {
        AFWeaponType type = AFWeaponType.getWeaponType(this);
        if (type != null) {
            switch (type) {
                case SPEAR, JAVELIN: {
                    return -0.35F;
                }
            }
        }
        return -0.2F;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.artifex.throwable_description").withStyle(ChatFormatting.DARK_GRAY));
        AFAmmoDataCapability.get(pStack).ifPresent(cap -> {
            pTooltipComponents.add(Component.translatable("tooltip.artifex.throwable_ammo_stack", cap.getAmmo(), cap.getMaxAmmo(pStack)).withStyle(ChatFormatting.GRAY));
        });
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(this);
    }
}
