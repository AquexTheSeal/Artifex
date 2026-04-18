package org.celestialworkshop.artifex.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class IaijutsuParticle extends SimpleAnimatedParticle {

    protected IaijutsuParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
        this.lifetime = 6 + level.random.nextInt(2);
        this.quadSize = 1.5F + this.random.nextFloat() * 0.5F;
        this.hasPhysics = false;
        this.setSpriteFromAge(sprites);
        this.setAlpha(1.0F);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new IaijutsuParticle(level, x, y, z, this.spriteSet);
        }
    }
}
