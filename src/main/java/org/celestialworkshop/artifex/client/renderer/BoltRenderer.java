package org.celestialworkshop.artifex.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.entity.Bolt;
import org.jetbrains.annotations.NotNull;

public class BoltRenderer extends ArrowRenderer<Bolt> {

    public BoltRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Bolt pEntity) {
        return Artifex.prefix("textures/entity/basic_bolt.png");
    }
}
