package org.celestialworkshop.artifex;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;
import org.celestialworkshop.artifex.item.specialty.ComboBasedSpecialty;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;
import org.celestialworkshop.artifex.registry.AFAttributes;
import org.celestialworkshop.artifex.registry.AFSpecialties;
import org.celestialworkshop.artifex.util.ItemStackUtil;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Artifex.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AFCommonEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AFEntityDataCapability.get(event.player).ifPresent(cap -> {
                ItemStack handItem = event.player.getMainHandItem();

                // IAIJUTSU MANAGEMENT
                boolean hasSpecialty = ItemStackUtil.hasSpecialty(handItem, AFSpecialties.IAIJUTSU.get());
                if (hasSpecialty) {
                    cap.iaijutsuTimer = Math.max(cap.iaijutsuTimer - 1, 0);
                }
                if (event.side == LogicalSide.SERVER) {
                    if (cap.iaijutsuItemStack != handItem) {
                        boolean oldHadSpecialty = ItemStackUtil.hasSpecialty(cap.iaijutsuItemStack, AFSpecialties.IAIJUTSU.get());
                        cap.iaijutsuItemStack = handItem;
                        if (hasSpecialty || oldHadSpecialty) {
                            cap.iaijutsuTimer = cap.getMaxIaijutsuTime();
                            AFNetwork.sendToPlayer((ServerPlayer) event.player, new S2CSyncIaijutsuPacket(cap.iaijutsuTimer));
                        }
                    }
                }

                // COMBO MANAGEMENT
                boolean endCombo = false;
                if (cap.comboCount > 0) {
                    cap.comboTimer = Math.max(cap.comboTimer - 1, 0);
                    if (cap.comboTimer == 0) {
                        if (event.side == LogicalSide.SERVER) {
                            endCombo = true;
                        }
                    }
                    if (event.side == LogicalSide.SERVER) {
                        if (!ItemStackUtil.sameItemMatchesEnchantments(cap.comboItemStack, handItem)) {
                            endCombo = true;
                        }
                    }
                } else {
                    cap.comboTimer = 0;
                }
                if (endCombo) {
                    if (cap.comboItemStack.getItem() instanceof AFPropertyItem af) {
                        for (Map.Entry<AFSpecialty, Integer> specialty : af.getSpecialties().entrySet()) {
                            if (specialty.getKey() instanceof ComboBasedSpecialty comboBasedSpecialty) {
                                comboBasedSpecialty.onComboEnd(event.player, cap.comboItemStack, specialty.getValue());
                            }
                        }
                    }
                    cap.comboCount = 0;
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
    public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof AFThrowableTieredItem af) {
            AFAmmoDataCapability.Provider provider = new AFAmmoDataCapability.Provider(event.getObject());
            event.addCapability(Artifex.prefix("ammo"), provider);
            event.addListener(provider::invalidate);
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
    }

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        int result = event.getLootingLevel();
        DamageSource damageSource = event.getDamageSource();
        if (damageSource != null && damageSource.getEntity() instanceof LivingEntity entity) {
            ItemStack weaponStack = entity.getMainHandItem();
            if (weaponStack.getItem() instanceof AFPropertyItem af) {
                if (af.getSpecialties().containsKey(AFSpecialties.BOUNTIFUL_HARVEST.get())) {
                    result += af.getSpecialties().get(AFSpecialties.BOUNTIFUL_HARVEST.get());
                }
            }
        }
        event.setLootingLevel(result);
    }
}
