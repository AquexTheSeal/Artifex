package org.celestialworkshop.artifex.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.celestialworkshop.artifex.entity.ThrownWeaponProjectile;
import org.celestialworkshop.artifex.item.base.AFThrowableTieredItem;

public class ThrownWeaponProjectileRenderer extends EntityRenderer<ThrownWeaponProjectile> {

    public ThrownWeaponProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ThrownWeaponProjectile pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.getHeldStack().getItem() instanceof AFThrowableTieredItem throwableItem) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) - 45.0F));

            float translate = throwableItem.getRenderTranslationOffset();
            pPoseStack.translate(translate, translate, 0);

            float scale = throwableItem.getRenderScale();
            pPoseStack.scale(scale, scale, scale);

            Minecraft.getInstance().getItemRenderer().renderStatic(pEntity.getHeldStack(), ItemDisplayContext.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pEntity.level(), 0);
            pPoseStack.popPose();

            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownWeaponProjectile pEntity) {
        return null;
    }
}
