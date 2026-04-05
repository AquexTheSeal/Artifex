package org.celestialworkshop.artifex.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFMaterial;
import org.celestialworkshop.artifex.api.AFWeaponType;
import org.celestialworkshop.artifex.registry.AFItems;

public class AFItemModelProvider extends ItemModelProvider {

    public AFItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Artifex.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        AFItems.MATERIALS.forEach(this::materialSet);
    }

    public void materialSet(AFMaterial material) {
        for (AFWeaponType weaponType : material.getAvailableWeaponTypes()) {
            Item weapon = material.getWeapon(weaponType);
            switch (weaponType) {
                case KNUCKLES, SHORTSWORD, DAGGER, SICKLE, BATTLEAXE, FLANGED_MACE, KATANA -> this.handheldItem(weapon);
                case JAVELIN, SPEAR, GLAIVE, HALBERD -> this.basicParentedItem(weapon, "artifex:item/handheld_long_middle");
                case GREATSWORD, ODACHI, SCYTHE -> this.basicParentedItem(weapon, "artifex:item/handheld_long_base");
                case CROSSBOW -> this.crossbowItem(weapon, "item/crossbow");
                case ARBALEST -> this.crossbowItem(weapon, "artifex:item/crossbow_long");
                case BOW -> this.bowItem(weapon, "item/bow");
                case LONGBOW -> this.bowItem(weapon, "artifex:item/bow_long");
            }
        }
    }

    public void handheldItem(Item item) {
        this.basicParentedItem(item, "item/handheld");
    }

    public void basicParentedItem(Item item, String parent) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        this.getBuilder(loc.toString()).parent(new ModelFile.UncheckedModelFile(parent))
                .texture("layer0", new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath()));
    }

    public void bowItem(Item item, String parentLocation) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = "item/" + loc.getPath();
        ModelFile parent = new ModelFile.UncheckedModelFile(parentLocation);
        this.getBuilder(loc.toString())
                .parent(parent)
                .texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath))
                .override().predicate(new ResourceLocation("pulling"), 1).model(getBuilder(itemPath + "_pulling_0")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_0")))
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(getBuilder(itemPath + "_pulling_1")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_1")))
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(getBuilder(itemPath + "_pulling_2")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_2")))
                .end();
    }

    public void crossbowItem(Item item, String parentLocation) {
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = "item/" + loc.getPath();
        ModelFile parent = new ModelFile.UncheckedModelFile(parentLocation);
        this.getBuilder(loc.toString())
                .parent(parent)
                .texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_standby"))
                .override().predicate(new ResourceLocation("pulling"), 1).model(getBuilder(itemPath + "_pulling_0")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_0")))
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58f).model(getBuilder(itemPath + "_pulling_1")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_1")))
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1.0f).model(getBuilder(itemPath + "_pulling_2")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_pulling_2")))
                .end()
                .override().predicate(new ResourceLocation("charged"), 1).model(getBuilder(itemPath + "_arrow")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_arrow")))
                .end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(getBuilder(itemPath + "_firework")
                        .parent(parent).texture("layer0", new ResourceLocation(loc.getNamespace(), itemPath + "_firework")))
                .end();
    }
}

