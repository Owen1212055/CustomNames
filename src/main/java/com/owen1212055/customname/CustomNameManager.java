package com.owen1212055.customname;

import com.owen1212055.customname.listener.EntityPassengerListener;
import com.owen1212055.customname.listener.PlayerQuitListener;
import com.owen1212055.customname.listener.PlayerSneakListener;
import com.owen1212055.customname.listener.PlayerTrackerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomNameManager {

    private final CustomNameStorage storage = new CustomNameStorage();

    public void registerListeners(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new PlayerTrackerListener(plugin, this.storage), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerSneakListener(this.storage), plugin);
        Bukkit.getPluginManager().registerEvents(new EntityPassengerListener(this.storage, plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(plugin, this.storage), plugin);
    }

    public CustomName forEntity(Entity entity) {
        CustomName customName = this.storage.getCustomPlayerName(entity.getUniqueId());
        if (customName == null) {
            customName = new CustomName(entity);
            this.storage.registerNew(entity.getUniqueId(), customName);

            // Send to trackers
            customName.setHidden(false);
        }

        return customName;
    }

    public void unregister(Entity entity) {
        CustomName customName = this.storage.remove(entity.getUniqueId());
        if (customName != null) {
            // Remove from trackers
            customName.setHidden(true);
        }
    }
}
