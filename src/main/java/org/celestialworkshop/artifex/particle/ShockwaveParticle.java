package org.celestialworkshop.artifex.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ShockwaveParticle extends SimpleAnimatedParticle {

    protected ShockwaveParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
        this.lifetime = 10 + level.random.nextInt(4);
        this.quadSize = 3.5F + this.random.nextFloat() * 0.5F;
        this.hasPhysics = false;
        this.setSpriteFromAge(sprites);
        this.setAlpha(0.25F);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setAlpha(0.25F);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        Vector3f pos = camera.getPosition().toVector3f();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - pos.x());
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - pos.y());
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - pos.z());

        Quaternionf rotation = new Quaternionf();
        rotation.rotationX((float) Math.toRadians(90));

        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };

        float size = this.getQuadSize(partialTicks);
        for (int i = 0; i < 4; ++i) {
            Vector3f v = positions[i];
            v.rotate(rotation);
            v.mul(size);
            v.add(x, y, z);
        }

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTicks);

        buffer.vertex(positions[0].x(), positions[0].y(), positions[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(positions[1].x(), positions[1].y(), positions[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(positions[2].x(), positions[2].y(), positions[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(positions[3].x(), positions[3].y(), positions[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ShockwaveParticle(level, x, y, z, this.spriteSet);
        }
    }
}
