package org.celestialworkshop.artifex.data.compat;

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
                                2.25, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case SHORTSWORD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sword", new WeaponAttributes(
                                2.25, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case DAGGER -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:dagger", new WeaponAttributes(
                                2.25, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case SICKLE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:sickle", new WeaponAttributes(
                                2.5, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case BATTLEAXE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:double_axe", new WeaponAttributes(
                                2.5, 0, null, null, false, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case FLANGED_MACE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:mace", new WeaponAttributes(
                                2.5, 0, null, null, false, null, new WeaponAttributes.Attack[]{
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        1.1F, 1.0F, 1.0F, 90, 0.5, "bettercombat:one_handed_slam",
                                        new WeaponAttributes.Sound("bettercombat:mace_slam"),
                                        null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        0.9F, 1.0F, 1.0F, 90, 0.5, "bettercombat:one_handed_uppercut_right",
                                        new WeaponAttributes.Sound("bettercombat:mace_slash"),
                                        null, null)
                                }, null)
                        ));
                    }
                    case GREATSWORD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:claymore", new WeaponAttributes(
                                3.5, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case JAVELIN -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", new WeaponAttributes(
                                3.75, 0, null, null, false, "af_javelin", new WeaponAttributes.Attack[]{
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                                0.85F, 1.0F, 1.0F, 0, 0.5, "bettercombat:one_handed_stab",
                                                new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                                null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        0.85F, 1.0F, 1.0F, 0, 0.5, "bettercombat:one_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                        null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        1.15F, 1.0F, 1.0F, 0, 0.5, "bettercombat:dual_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:spear_stab"),
                                        null, null)
                                }, null)
                        ));
                    }
                    case SPEAR -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:lance", new WeaponAttributes(
                                3.5, 0, null, null, false, "af_spear", new WeaponAttributes.Attack[]{
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.FORWARD_BOX,
                                        1.0F, 1.0F, 1.0F, 0, 0.5, "bettercombat:one_handed_stab",
                                        new WeaponAttributes.Sound("bettercombat:scythe_slash"),
                                        null, null)
                                }, null)
                        ));
                    }
                    case GLAIVE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:glaive", new WeaponAttributes(
                                3.00, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case HALBERD -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:halberd", new WeaponAttributes(
                                4.00, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case SCYTHE -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:scythe", new WeaponAttributes(
                                3.25, 0, null, null, null, null, new WeaponAttributes.Attack[]{}, null)
                        ));
                    }
                    case KATANA -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", new WeaponAttributes(
                                2.5, 0, null, null, null, "katana", new WeaponAttributes.Attack[]{
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                        0.95F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_right",
                                        new WeaponAttributes.Sound("bettercombat:katana_slash"),
                                        null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        1.05F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_vertical_left",
                                        new WeaponAttributes.Sound("bettercombat:katana_slash"),
                                        null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                        1.05F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_vertical_right",
                                        new WeaponAttributes.Sound("bettercombat:katana_slash"),
                                        null, null),
                                new WeaponAttributes.Attack(
                                        new WeaponAttributes.Condition[]{},
                                        WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                        0.95F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_left",
                                        new WeaponAttributes.Sound("bettercombat:katana_slash"),
                                        null, null)
                        }, null
                        )));
                    }
                    case ODACHI -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:katana", new WeaponAttributes(
                                3.5, 0, "bettercombat:pose_two_handed_katana", null, true, "odachi", new WeaponAttributes.Attack[]{
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                                0.95F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_right",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null, null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                                1.05F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_vertical_left",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null, null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.VERTICAL_PLANE,
                                                1.05F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_vertical_right",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null, null),
                                        new WeaponAttributes.Attack(
                                                new WeaponAttributes.Condition[]{},
                                                WeaponAttributes.HitBoxShape.HORIZONTAL_PLANE,
                                                0.95F, 1.0F, 1.0F, 120, 0.5, "bettercombat:two_handed_slash_horizontal_left",
                                                new WeaponAttributes.Sound("bettercombat:claymore_swing"),
                                                null, null)
                                }, null)
                        ));
                    }
                    case RAPIER -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:rapier", new WeaponAttributes(
                                2.75, 0, null, null, false, "rapier", new WeaponAttributes.Attack[]{}, null
                        )));
                    }
                    case SCIMITAR -> {
                        this.addAttribute(weapon, new AttributesContainer("bettercombat:cutlass", new WeaponAttributes(
                                2.75, 0, null, null, false, "scimitar", new WeaponAttributes.Attack[]{}, null
                        )));
                    }
                }
            }
        });
    }
}
