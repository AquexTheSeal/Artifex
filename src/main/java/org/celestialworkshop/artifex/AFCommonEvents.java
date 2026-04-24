package org.celestialworkshop.artifex;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.capability.AFItemStackDataCapability;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.C2SSyncIaijutsuMovementStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncSpecialtiesDataPacket;
import org.celestialworkshop.artifex.registry.AFAttributes;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AFCommonEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AFEntityDataCapability.get(event.player).ifPresent(cap -> {
                ItemStack handItem = event.player.getMainHandItem();

                // IAIJUTSU MANAGEMENT
                boolean hasSpecialty = ItemStackUtil.hasSpecialty(handItem, AFSpecialties.IAIJUTSU.get());

                if (event.side == LogicalSide.CLIENT) {
                    boolean isMovingSlowly = event.player.getDeltaMovement().lengthSqr() < 0.01;
                    if (isMovingSlowly != cap.iaijutsuSpeedUp) {
                        cap.iaijutsuSpeedUp = isMovingSlowly;
                        AFNetwork.sendToServer(new C2SSyncIaijutsuMovementStatePacket(isMovingSlowly));
                    }
                }

                if (hasSpecialty) {
                    int sub = cap.iaijutsuSpeedUp ? 3 : 1;
                    cap.iaijutsuTimer = Math.max(cap.iaijutsuTimer - sub, 0);
                }

                if (event.side == LogicalSide.SERVER) {
                    if (cap.iaijutsuItemStack != handItem) {
                        boolean oldHadSpecialty = ItemStackUtil.hasSpecialty(cap.iaijutsuItemStack, AFSpecialties.IAIJUTSU.get());
                        cap.iaijutsuItemStack = handItem;

                        if (hasSpecialty || oldHadSpecialty) {
                            cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
                            AFNetwork.sendToPlayer((ServerPlayer) event.player, new S2CSyncIaijutsuPacket(cap.iaijutsuItemStack, cap.iaijutsuTimer, cap.iaijutsuSpeedUp));
                        }
                    }
                }

                // COMBO MANAGEMENT
                boolean syncPacket = false;

                if (cap.comboCount > 0) {
                    cap.comboTimer = Math.max(cap.comboTimer - 1, 0);
                    if (cap.comboTimer == 0) {
                        if (event.side == LogicalSide.SERVER) {
                            syncPacket = true;
                        }
                    }
                    if (event.side == LogicalSide.SERVER) {
                        if (!ItemStackUtil.sameItemMatchesEnchantments(cap.comboItemStack, handItem)) {
                            syncPacket = true;
                        }
                    }
                } else {
                    cap.comboTimer = 0;
                }

                if (syncPacket) {
                    ItemStackUtil.getSpecialties(cap.comboItemStack.getItem()).forEach((key, value) -> {
                        if (key instanceof ComboBasedSpecialty comboBasedSpecialty) {
                            comboBasedSpecialty.onComboEnd(event.player, cap.comboItemStack, value);
                        }
                    });
                    cap.comboCount = 0;
                    cap.maxComboTimer = 0;
                    cap.comboItemStack = ItemStack.EMPTY;
                    AFNetwork.sendToPlayer((ServerPlayer) event.player, new S2CSyncComboStatePacket(cap.comboItemStack, cap.comboCount, cap.comboTimer));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        AFEntityDataCapability.Provider provider = new AFEntityDataCapability.Provider();
        event.addCapability(Artifex.prefix("artifex_entity_data"), provider);
        event.addListener(provider::invalidate);
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
    public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof AFThrowableTieredItem) {
                AFItemStackDataCapability.Provider provider = new AFItemStackDataCapability.Provider(stack);
                event.addCapability(Artifex.prefix("artifex_data"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCrit(CriticalHitEvent event) {
        ItemStack mainHandItem = event.getEntity().getMainHandItem();
        if (ItemStackUtil.hasSpecialty(mainHandItem, AFSpecialties.IAIJUTSU.get())) {
            AFEntityDataCapability.get(event.getEntity()).ifPresent(cap -> {
                if (cap.iaijutsuTimer <= 0) {
                    event.setResult(Event.Result.DENY);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        AttributeInstance damageReduction = event.getEntity().getAttribute(AFAttributes.DAMAGE_REDUCTION.get());
        if (damageReduction != null && damageReduction.getValue() > 0) {
            float amount = event.getAmount();
            float reduction = (float) damageReduction.getValue();
            event.setAmount(amount * (1.0F - reduction));
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            AFEntityDataCapability.get(player).ifPresent(cap -> {
                cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
                AFNetwork.sendToPlayer(player, new S2CSyncIaijutsuPacket(cap.iaijutsuItemStack, cap.iaijutsuTimer, cap.iaijutsuSpeedUp));
            });
        }
    }

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        int result = event.getLootingLevel();
        DamageSource damageSource = event.getDamageSource();
        if (damageSource != null && damageSource.getEntity() instanceof LivingEntity entity) {
            ItemStack weaponStack = entity.getMainHandItem();
            if (ItemStackUtil.hasSpecialty(weaponStack.getItem(), AFSpecialties.BOUNTIFUL_HARVEST.get())) {
                result += ItemStackUtil.getSpecialtyLevel(weaponStack.getItem(), AFSpecialties.BOUNTIFUL_HARVEST.get());
            }
        }
        event.setLootingLevel(result);
    }
}
