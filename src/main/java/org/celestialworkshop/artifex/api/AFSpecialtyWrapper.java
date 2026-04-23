package org.celestialworkshop.artifex.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.celestialworkshop.artifex.registry.AFSpecialties;

import java.util.Map;

public record AFSpecialtyWrapper(Map<AFSpecialty, Integer> specialties) {

    public static final Codec<AFSpecialtyWrapper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(AFSpecialties.REGISTRY.get().getCodec(), Codec.INT).fieldOf("specialties").forGetter(AFSpecialtyWrapper::specialties)
    ).apply(instance, AFSpecialtyWrapper::new));

}
