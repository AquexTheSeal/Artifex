package org.celestialworkshop.artifex.data.parent.material;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;

import java.util.List;

public class MaterialItemModelProvider extends ItemModelProvider {

    public final List<AFMaterial> materials;

    public MaterialItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper, String modid, List<AFMaterial> materials) {
        super(output, modid, existingFileHelper);
        this.materials = materials;
    }

    @Override
    protected void registerModels() {
        materials.forEach(this::materialSet);
    }

    public void materialSet(AFMaterial material) {
        for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
            Item weapon = material.getWeapon(weaponType);
            switch (weaponType) {
                case KNUCKLES -> this.basicParentedItem(weapon, "artifex:item/knuckles", "0");
                case SHORTSWORD, DAGGER, SICKLE, BATTLEAXE, FLANGED_MACE, KATANA, SCIMITAR, RAPIER -> this.handheldItem(weapon);
                case JAVELIN, SPEAR, GLAIVE, HALBERD -> this.basicParentedItem(weapon, "artifex:item/handheld_long_middle");
                case GREATSWORD, ODACHI, SCYTHE -> this.basicParentedItem(weapon, "artifex:item/handheld_long_base");
                case CROSSBOW -> this.crossbowItem(weapon, "item/crossbow");
                case ARBALEST -> this.crossbowItem(weapon, "artifex:item/crossbow_long");
                case BOW -> this.bowItem(weapon, "item/bow");
                case LONGBOW -> this.bowItem(weapon, "artifex:item/bow_long");
                case SHIELD, BUCKLER -> this.shieldItem(weapon, "artifex:item/shield");
                case WAR_DOOR -> this.shieldItem(weapon, "artifex:item/shield_long");
            }
        }
    }

    public void handheldItem(Item item) {
        this.basicParentedItem(item, "item/handheld");
    }

    public void basicParentedItem(Item item, String parent) {
        this.basicParentedItem(item, parent, "layer0");
    }

    public void basicParentedItem(Item item, String parent, String textureKey) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        this.getBuilder(loc.toString()).parent(new ModelFile.UncheckedModelFile(parent))
                .texture(textureKey, ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "item/" + loc.getPath()));
    }

    public void bowItem(Item item, String parentLocation) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = "item/" + loc.getPath();
        ModelFile parent = new ModelFile.UncheckedModelFile(parentLocation);
        this.getBuilder(loc.toString())
                .parent(parent)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath))
                .override().predicate(ResourceLocation.parse("pulling"), 1).model(getBuilder(itemPath + "_pulling_0")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_0")))
                .end()
                .override().predicate(ResourceLocation.parse("pulling"), 1).predicate(ResourceLocation.parse("pull"), 0.65F).model(getBuilder(itemPath + "_pulling_1")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_1")))
                .end()
                .override().predicate(ResourceLocation.parse("pulling"), 1).predicate(ResourceLocation.parse("pull"), 1.0F).model(getBuilder(itemPath + "_pulling_2")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_2")))
                .end();
    }

    public void crossbowItem(Item item, String parentLocation) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = "item/" + loc.getPath();
        ModelFile parent = new ModelFile.UncheckedModelFile(parentLocation);
        this.getBuilder(loc.toString())
                .parent(parent)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_standby"))
                .override().predicate(ResourceLocation.parse("pulling"), 1).model(getBuilder(itemPath + "_pulling_0")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_0")))
                .end()
                .override().predicate(ResourceLocation.parse("pulling"), 1).predicate(ResourceLocation.parse("pull"), 0.58f).model(getBuilder(itemPath + "_pulling_1")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_1")))
                .end()
                .override().predicate(ResourceLocation.parse("pulling"), 1).predicate(ResourceLocation.parse("pull"), 1.0f).model(getBuilder(itemPath + "_pulling_2")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_pulling_2")))
                .end()
                .override().predicate(ResourceLocation.parse("charged"), 1).model(getBuilder(itemPath + "_arrow")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_arrow")))
                .end()
                .override().predicate(ResourceLocation.parse("charged"), 1).predicate(ResourceLocation.parse("firework"), 1).model(getBuilder(itemPath + "_firework")
                        .parent(parent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath + "_firework")))
                .end();
    }

    public void shieldItem(Item item, String parentLocation) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = "item/" + loc.getPath();
        ModelFile parent = new ModelFile.UncheckedModelFile(parentLocation);
        ModelFile blockingParent = new ModelFile.UncheckedModelFile(parentLocation + "_blocking");
        this.getBuilder(loc.toString())
                .parent(parent)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath))
                .override().predicate(ResourceLocation.parse("blocking"), 1).model(getBuilder(itemPath + "_blocking")
                        .parent(blockingParent).texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), itemPath)))
                .end();
    }
}

