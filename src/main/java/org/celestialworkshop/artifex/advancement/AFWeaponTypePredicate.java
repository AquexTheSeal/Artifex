package org.celestialworkshop.artifex.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.jetbrains.annotations.NotNull;

public class AFWeaponTypePredicate extends ItemPredicate {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Artifex.MODID, "weapon_type");

    private final AFWeaponType weaponType;

    public AFWeaponTypePredicate(AFWeaponType weaponType) {
        this.weaponType = weaponType;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return AFWeaponType.isWeaponType(stack.getItem(), weaponType);
    }

    public static AFWeaponTypePredicate fromJson(JsonObject json) {
        String typeName = json.get("weapon_type").getAsString();
        AFWeaponType type = AFWeaponType.byName(typeName);
        if (type == null) {
            throw new JsonSyntaxException("Unknown AFWeaponType: " + typeName);
        }
        return new AFWeaponTypePredicate(type);
    }

    @Override
    public @NotNull JsonElement serializeToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", ID.toString());
        obj.addProperty("weapon_type", weaponType.getName());
        return obj;
    }

    public static void register() {
        ItemPredicate.register(ID, AFWeaponTypePredicate::fromJson);
    }
}