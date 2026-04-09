package org.celestialworkshop.artifex.api;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.item.base.AFPropertyItem;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import java.text.DecimalFormat;

public class AFSpecialty {
    protected final Category category;

    public AFSpecialty(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public float onDamageMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, float originalDamage, boolean wasCrit, int specialityLevel) {
        return originalDamage;
    }

    public float onDamageSweep(LivingEntity attacker, LivingEntity sweepTarget, ItemStack itemStack, float sweepDamage, float originalDamage, int specialityLevel) {
        return sweepDamage;
    }

    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
    }

    public float onDamageRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, float originalDamage, boolean wasCrit, int specialityLevel) {
        return originalDamage;
    }

    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
    }

    public static boolean hasSpecialty(Item item, AFSpecialty specialty) {
        return item instanceof AFPropertyItem artifexItem && artifexItem.getSpecialties().containsKey(specialty);
    }

    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(getId().getNamespace(), "textures/icons/specialties/" + getId().getPath() + ".png");
    }

    public MutableComponent getDisplayName(int level) {
        Component levelComponent = Component.translatable("enchantment.level." + level);
        Component translatedComponent = Component.translatable(this.getDisplayNameKey());
        return Component.literal(translatedComponent.getString() + " " + levelComponent.getString());
    }

    public String getDisplayNameKey() {
        return String.format("material_specialty.%s.%s", getId().getNamespace(), getId().getPath());
    }

    public MutableComponent getDisplayDescription(int level) {
        Object[] args = this.getDisplayDescriptionArgs(level);
        // Color code args
        Object[] coloredArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            coloredArgs[i] = Component.literal(String.valueOf(args[i])).withStyle(ChatFormatting.YELLOW);
        }
        return Component.translatable(this.getDisplayDescriptionKey(), coloredArgs);
    }

    public String getDisplayDescriptionKey() {
        return String.format("material_specialty.%s.%s_description", this.getId().getNamespace(), this.getId().getPath());
    }

    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[0];
    }

    public static String asPercentFormat(float value) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value * 100) + "%";
    }

    public ResourceLocation getId() {
        return AFSpecialties.REGISTRY.get().getKey(this);
    }

    public enum Category {
        BENEFICIAL(ChatFormatting.GREEN),
        HARMFUL(ChatFormatting.RED),
        NEUTRAL(ChatFormatting.GRAY)
        ;

        private final ChatFormatting color;

        Category(ChatFormatting color) {
            this.color = color;
        }

        public ChatFormatting getColor() {
            return this.color;
        }
    }
}

