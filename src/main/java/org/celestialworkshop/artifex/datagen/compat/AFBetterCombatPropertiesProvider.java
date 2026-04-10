package org.celestialworkshop.artifex.datagen.compat;

import net.bettercombat.api.AttributesContainer;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFItems;

public class AFBetterCombatPropertiesProvider extends BetterCombatPropertiesProvider {

    public AFBetterCombatPropertiesProvider(PackOutput output) {
        super(output, Artifex.MODID);
    }

    @Override
    protected void registerAttributes() {
        AFItems.MATERIALS.forEach(material -> {
            for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
                Item weapon = material.getWeapon(weaponType);
                switch (weaponType) {
                    case KNUCKLES -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:claw", null));
                    }
                    case SHORTSWORD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sword", null));
                    }
                    case DAGGER -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:dagger", null));
                    }
                    case SICKLE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sickle", null));
                    }
                    case BATTLEAXE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:double_axe", null));
                    }
                    case FLANGED_MACE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:mace", new WeaponAttributes(
                                0, null, null, null, null, new WeaponAttributes.Attack[]{
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        1.1F, 90, 0.5, "bettercombat:one_handed_slam",
                                        new WeaponAttributes.Sound("bettercombat:mace_slam"),
                                        null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        0.9F, 90, 0.5, "bettercombat:one_handed_uppercut_right",
                                        new WeaponAttributes.Sound("bettercombat:mace_slash"),
                                        null)
                                })
                        ));
                    }
                    case GREATSWORD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:claymore", null));
                    }
                    case JAVELIN -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", null));
                    }
                    case SPEAR -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", null));
                    }
                    case GLAIVE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:glaive", null));
                    }
                    case HALBERD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:halberd", null));
                    }
                    case SCYTHE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:scythe", null));
                    }
                    case KATANA -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", null));
                    }
                    case ODACHI -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", null));
                    }
                }
            }
        });
    }
}
