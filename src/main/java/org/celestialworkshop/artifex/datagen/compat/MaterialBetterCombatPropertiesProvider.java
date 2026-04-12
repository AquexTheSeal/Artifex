package org.celestialworkshop.artifex.datagen.compat;

import net.bettercombat.api.AttributesContainer;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.List;

public class MaterialBetterCombatPropertiesProvider extends BetterCombatPropertiesProvider {

    public final List<AFMaterial> materials;

    public MaterialBetterCombatPropertiesProvider(PackOutput output, String modid, List<AFMaterial> materials) {
        super(output, modid);
        this.materials = materials;
    }

    @Override
    protected void registerAttributes() {
        materials.forEach(material -> {
            for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
                Item weapon = material.getWeapon(weaponType);
                switch (weaponType) {
                    case KNUCKLES -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:claw", new WeaponAttributes(
                                2.25, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case SHORTSWORD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sword", new WeaponAttributes(
                                2.25, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case DAGGER -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:dagger", new WeaponAttributes(
                                2.25, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case SICKLE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sickle", new WeaponAttributes(
                                2.5, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case BATTLEAXE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:double_axe", new WeaponAttributes(
                                2.5, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case FLANGED_MACE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:mace", new WeaponAttributes(
                                2.5, null, null, false, null, new WeaponAttributes.Attack[]{
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
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:claymore", new WeaponAttributes(
                                3.25, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case JAVELIN -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", new WeaponAttributes(
                                3.5, null, null, false, "af_javelin", new WeaponAttributes.Attack[]{
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                                0.85F, 0, 0.5, "bettercombat:one_handed_stab",
                                                new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                                null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        0.85F, 0, 0.5, "bettercombat:one_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                        null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        1.15F, 0, 0.5, "bettercombat:dual_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                        null)
                                })
                        ));
                    }
                    case SPEAR -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", new WeaponAttributes(
                                3.5, null, null, false, "af_spear", new WeaponAttributes.Attack[]{
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        1.0F, 0, 0.5, "bettercombat:one_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:scythe_slash"),
                                        null)
                                })
                        ));
                    }
                    case GLAIVE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:glaive", new WeaponAttributes(
                                3.25, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case HALBERD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:halberd", new WeaponAttributes(
                                3.5, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case SCYTHE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:scythe", new WeaponAttributes(
                                3.5, null, null, null, null, new WeaponAttributes.Attack[]{})
                        ));
                    }
                    case KATANA -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", new WeaponAttributes(
                                2.5, "bettercombat:pose_two_handed_katana", null, false, "af_katana", new WeaponAttributes.Attack[]{}
                        )));
                    }
                    case ODACHI -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", new WeaponAttributes(
                                3.25, "bettercombat:pose_two_handed_katana", null, true, "af_odachi", new WeaponAttributes.Attack[]{
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                                0.8F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_right",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                                1.2F, 120, 0.5, "bettercombat:two_handed_slash_vertical_left",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                                1.2F, 120, 0.5, "bettercombat:two_handed_slash_vertical_right",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                                0.8F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_left",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null)
                                })
                        ));
                    }
                }
            }
        });
    }
}
