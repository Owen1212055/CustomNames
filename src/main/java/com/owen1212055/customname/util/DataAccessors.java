package com.owen1212055.customname.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.Pose;
import xyz.jpenilla.reflectionremapper.ReflectionRemapper;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

class DataAccessors {

    public static EntityDataAccessor<Byte> DATA_SHARED_FLAGS_ID;
    public static EntityDataAccessor<Pose> DATA_POSE;
    public static EntityDataAccessor<Optional<Component>> DATA_CUSTOM_NAME;
    public static EntityDataAccessor<Boolean> DATA_CUSTOM_NAME_VISIBLE;

    // Interaction entity
    public static EntityDataAccessor<Float> DATA_WIDTH_ID;
    public static EntityDataAccessor<Float> DATA_HEIGHT_ID;

    static {
        final ReflectionRemapper reflectionRemapper = ReflectionRemapper.forReobfMappingsInPaperJar();

        DATA_SHARED_FLAGS_ID = get(reflectionRemapper, Entity.class, "DATA_SHARED_FLAGS_ID");
        DATA_POSE = get(reflectionRemapper, Entity.class, "DATA_POSE");
        DATA_CUSTOM_NAME = get(reflectionRemapper, Entity.class, "DATA_CUSTOM_NAME");
        DATA_CUSTOM_NAME_VISIBLE = get(reflectionRemapper, Entity.class, "DATA_CUSTOM_NAME_VISIBLE");

        DATA_WIDTH_ID = get(reflectionRemapper, Interaction.class, "DATA_WIDTH_ID");
        DATA_HEIGHT_ID = get(reflectionRemapper, Interaction.class, "DATA_HEIGHT_ID");
    }

    @SuppressWarnings("unchecked")
    private static <T> EntityDataAccessor<T> get(ReflectionRemapper reflectionRemapper, Class<?> clazz, String name) {
        try {
            return (EntityDataAccessor<T>) MethodHandles.privateLookupIn(clazz, MethodHandles.lookup())
                    .findStaticGetter(clazz, reflectionRemapper.remapFieldName(clazz, name), EntityDataAccessor.class).invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
