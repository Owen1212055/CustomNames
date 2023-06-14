package com.owen1212055.customname;

import com.owen1212055.customname.listener.EntityPassengerListener;
import com.owen1212055.customname.listener.PlayerSneakListener;
import com.owen1212055.customname.listener.PlayerTrackerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomNameManager {

    private final CustomNameStorage storage = new CustomNameStorage();

    public void registerListeners(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new PlayerTrackerListener(this.storage), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerSneakListener(this.storage), plugin);
        Bukkit.getPluginManager().registerEvents(new EntityPassengerListener(this.storage, plugin), plugin);
    }

    public CustomName forEntity(Entity entity) {
        CustomName customName = this.storage.getCustomPlayerName(entity.getUniqueId());
        if (customName == null) {
            customName = new CustomName(entity);
            this.storage.registerNew(entity.getUniqueId(), customName);

            // Send to trackers
            for (Player player : entity.getTrackedPlayers()) {
                customName.sendToClient(player);
            }
        }

        return customName;
    }
}
