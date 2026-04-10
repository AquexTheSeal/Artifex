package org.celestialworkshop.artifex.datagen.compat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.bettercombat.api.AttributesContainer;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class BetterCombatPropertiesProvider implements DataProvider {
    public final Map<String, Pair<Item, AttributesContainer>> data = new TreeMap<>();
    public final PackOutput output;
    public final String modid;

    public BetterCombatPropertiesProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    public void addAttribute(Item item, AttributesContainer attribute) {
        String path = ForgeRegistries.ITEMS.getKey(item).getPath();
        data.put(path, Pair.of(item, attribute));
    }

    protected abstract void registerAttributes();

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        registerAttributes();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[this.data.size()];
        int i = 0;
        for (Pair<Item, AttributesContainer> pairs : this.data.values()) {
            futures[i++] = save(pOutput, pairs.getFirst(), pairs.getSecond());
        }
        return CompletableFuture.allOf(futures);
    }

    protected CompletableFuture<?> save(CachedOutput pOutput, Item item, AttributesContainer attribute) {
        String path = ForgeRegistries.ITEMS.getKey(item).getPath();
        Path target = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(this.modid).resolve("weapon_attributes").resolve(path + ".json");
        return DataProvider.saveStable(pOutput, compileAttributeJson(attribute), target);
    }

    protected JsonObject compileAttributeJson(AttributesContainer attribute) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", attribute.parent());
        if (attribute.attributes() != null) {
            JsonObject attributes = attributeToJson(attribute);
            if (attribute.attributes().attacks() != null) {
                JsonArray attacksArray = new JsonArray();
                for (WeaponAttributes.Attack attack : attribute.attributes().attacks()) {
                    attacksArray.add(createAttackJsonArray(attack));
                }
                attributes.add("attacks", attacksArray);
            }
            json.add("attributes", attributes);
        }
        return json;
    }

    private static @NotNull JsonObject attributeToJson(AttributesContainer attribute) {
        JsonObject attributes = new JsonObject();
        if (attribute.attributes().attackRange() != 0) {
            attributes.addProperty("attack_range", attribute.attributes().attackRange());
        }
        if (attribute.attributes().pose() != null) {
            attributes.addProperty("pose", attribute.attributes().pose());
        }
        if (attribute.attributes().offHandPose() != null) {
            attributes.addProperty("off_hand_pose", attribute.attributes().offHandPose());
        }
        if (attribute.attributes().two_handed() != null) {
            attributes.addProperty("two_handed", attribute.attributes().two_handed());
        }
        if (attribute.attributes().category() != null) {
            attributes.addProperty("category", attribute.attributes().category());
        }
        return attributes;
    }

    private static JsonObject createAttackJsonArray(WeaponAttributes.Attack attack) {
        JsonObject json = new JsonObject();
        JsonArray conditionsArray = new JsonArray();
        if (attack.conditions() != null) {
            for (WeaponAttributes.Condition condition : attack.conditions()) {
                conditionsArray.add(condition.name());
            }
            json.add("conditions", conditionsArray);
        }
        if (attack.hitbox() != null) {
            json.addProperty("hitbox", attack.hitbox().name());
        }
        if (attack.damageMultiplier() != 1.0) {
            json.addProperty("damage_multiplier", attack.damageMultiplier());
        }
        if (attack.angle() != 0) {
            json.addProperty("angle", attack.angle());
        }
        if (attack.upswing() != 0) {
            json.addProperty("upswing", attack.upswing());
        }
        if (attack.animation() != null) {
            json.addProperty("animation", attack.animation());
        }
        if (attack.swingSound() != null) {
            JsonObject swingSound = new JsonObject();
            swingSound.addProperty("id", attack.swingSound().id());
            swingSound.addProperty("volume", attack.swingSound().volume());
            swingSound.addProperty("pitch", attack.swingSound().pitch());
            swingSound.addProperty("randomness", attack.swingSound().randomness());
            json.add("swing_sound", swingSound);
        }
        JsonObject impactSound = new JsonObject();
        if (attack.impactSound() != null) {
            impactSound.addProperty("id", attack.impactSound().id());
            impactSound.addProperty("volume", attack.impactSound().volume());
            impactSound.addProperty("pitch", attack.impactSound().pitch());
            impactSound.addProperty("randomness", attack.impactSound().randomness());
            json.add("impact_sound", impactSound);
        }
        return json;
    }

    @Override
    public String getName() {
        return StringUtils.capitalize(modid) + ": Better Combat Compatibility";
    }
}