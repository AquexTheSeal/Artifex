package org.celestialworkshop.artifex;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.enchanting.GetEnchantmentLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.C2SSyncIaijutsuMovementStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncSpecialtiesDataPacket;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
import org.celestialworkshop.artifex.registry.AFAttributes;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;

@EventBusSubscriber(modid = Artifex.MODID)
public class AFCommonEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        AFEntityData cap = player.getData(AFAttachmentTypes.ENTITY_DATA);
        ItemStack handItem = player.getMainHandItem();

        boolean hasSpecialty = ItemStackUtil.hasSpecialty(handItem, AFSpecialties.IAIJUTSU.get());

        if (player.level().isClientSide()) {
            boolean isMovingSlowly = player.getDeltaMovement().lengthSqr() < 0.01;
            if (isMovingSlowly != cap.iaijutsuSpeedUp) {
                cap.iaijutsuSpeedUp = isMovingSlowly;
                AFNetwork.sendToServer(new C2SSyncIaijutsuMovementStatePacket(isMovingSlowly));
            }
        }

        if (hasSpecialty) {
            int sub = cap.iaijutsuSpeedUp ? 3 : 1;
            cap.iaijutsuTimer = Math.max(cap.iaijutsuTimer - sub, 0);
        }

        if (!player.level().isClientSide()) {
            if (cap.iaijutsuItemStack != handItem) {
                boolean oldHadSpecialty = ItemStackUtil.hasSpecialty(cap.iaijutsuItemStack, AFSpecialties.IAIJUTSU.get());
                cap.iaijutsuItemStack = handItem;

                if (hasSpecialty || oldHadSpecialty) {
                    cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
                    AFNetwork.sendToPlayer((ServerPlayer) player, new S2CSyncIaijutsuPacket(cap.iaijutsuItemStack, cap.iaijutsuTimer, cap.iaijutsuSpeedUp));
                }
            }
        }

        boolean syncPacket = false;

        if (cap.comboCount > 0) {
            cap.comboTimer = Math.max(cap.comboTimer - 1, 0);
            if (!player.level().isClientSide()) {
                if (cap.comboTimer == 0 || !ItemStackUtil.sameItemMatchesEnchantments(cap.comboItemStack, handItem)) {
                    syncPacket = true;
                }
            }
        } else {
            cap.comboTimer = 0;
        }

        if (syncPacket) {
            ItemStackUtil.getSpecialties(cap.comboItemStack.getItem()).forEach((key, value) -> {
                if (key instanceof ComboBasedSpecialty comboBasedSpecialty) {
                    comboBasedSpecialty.onComboEnd(player, cap.comboItemStack, value);
                }
            });
            cap.comboCount = 0;
            cap.maxComboTimer = 0;
            cap.comboItemStack = ItemStack.EMPTY;
            AFNetwork.sendToPlayer((ServerPlayer) player, new S2CSyncComboStatePacket(cap.comboItemStack, cap.comboCount, cap.comboTimer));
        }
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new AFSpecialtiesReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        if (player != null) {
            AFNetwork.sendToPlayer(player, new S2CSyncSpecialtiesDataPacket(AFSpecialtiesReloadListener.SPECIALTY_DATA));
        } else {
            AFNetwork.sendToAll(new S2CSyncSpecialtiesDataPacket(AFSpecialtiesReloadListener.SPECIALTY_DATA));
        }
    }

    @SubscribeEvent
    public static void onPlayerCrit(CriticalHitEvent event) {
        ItemStack mainHandItem = event.getEntity().getMainHandItem();
        if (ItemStackUtil.hasSpecialty(mainHandItem, AFSpecialties.IAIJUTSU.get())) {
            AFEntityData cap = event.getEntity().getData(AFAttachmentTypes.ENTITY_DATA);
            if (cap.iaijutsuTimer <= 0) {
                event.setCriticalHit(false);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        AttributeInstance damageReduction = event.getEntity().getAttribute(AFAttributes.DAMAGE_REDUCTION);
        if (damageReduction != null && damageReduction.getValue() > 0) {
            float amount = event.getAmount();
            float reduction = (float) damageReduction.getValue();
            event.setAmount(amount * (1.0F - reduction));
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            AFEntityData cap = player.getData(AFAttachmentTypes.ENTITY_DATA);
            cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
            AFNetwork.sendToPlayer(player, new S2CSyncIaijutsuPacket(cap.iaijutsuItemStack, cap.iaijutsuTimer, cap.iaijutsuSpeedUp));
        }
    }

    @SubscribeEvent
    public static void onGetEnchantmentLevel(GetEnchantmentLevelEvent event) {
        if (event.isTargetting(Enchantments.LOOTING)) {
            ItemStack weaponStack = event.getStack();
            if (ItemStackUtil.hasSpecialty(weaponStack.getItem(), AFSpecialties.BOUNTIFUL_HARVEST.get())) {
                event.getHolder(Enchantments.LOOTING).ifPresent(holder -> {
                    int bonus = ItemStackUtil.getSpecialtyLevel(weaponStack.getItem(), AFSpecialties.BOUNTIFUL_HARVEST.get());
                    event.getEnchantments().set(holder, event.getEnchantments().getLevel(holder) + bonus);
                });
            }
        }
    }
}
