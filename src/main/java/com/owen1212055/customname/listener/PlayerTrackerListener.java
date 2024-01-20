package com.owen1212055.customname.listener;

import com.owen1212055.customname.CustomName;
import com.owen1212055.customname.CustomNameStorage;
import io.papermc.paper.event.player.PlayerTrackEntityEvent;
import io.papermc.paper.event.player.PlayerUntrackEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerTrackerListener implements Listener {

    private final JavaPlugin plugin;
    private final CustomNameStorage nameStorage;

    public PlayerTrackerListener(JavaPlugin plugin, CustomNameStorage nameStorage) {
        this.plugin = plugin;
        this.nameStorage = nameStorage;
    }

    @EventHandler
    public void trackEntity(PlayerTrackEntityEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getEntity().getUniqueId());
        // Does the entity have a custom name?
        if (playerName != null) {
            new BukkitRunnable(){

                @Override
                public void run() {
                    playerName.sendToClient(event.getPlayer());
                }
            }.runTaskLater(this.plugin, 1);
        }
    }

    @EventHandler
    public void untrackEntity(PlayerUntrackEntityEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getEntity().getUniqueId());
        // Does the entity have a custom name?
        if (playerName != null) {
            playerName.removeFromClient(event.getPlayer());
        }
    }
}
