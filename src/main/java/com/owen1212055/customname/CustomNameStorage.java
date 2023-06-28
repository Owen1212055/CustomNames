package com.owen1212055.customname;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomNameStorage {

    private final Map<UUID, CustomName> customPlayerNameMap = new HashMap<>();

    @Nullable
    public CustomName getCustomPlayerName(UUID uuid) {
        return this.customPlayerNameMap.get(uuid);
    }

    public void registerNew(UUID entityId, CustomName name) {
        this.customPlayerNameMap.put(entityId, name);
    }

    @Nullable
    public CustomName remove(UUID uuid) {
        CustomName name = this.customPlayerNameMap.remove(uuid);
        if (name != null) {
            name.close();
        }

        return name;
    }
}
