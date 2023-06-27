package com.owen1212055.customname.listener;

import com.owen1212055.customname.CustomNameStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerQuitListener implements Listener {

    private final JavaPlugin plugin;
    private final CustomNameStorage nameStorage;

    public PlayerQuitListener(JavaPlugin plugin, CustomNameStorage nameStorage) {
        this.plugin = plugin;
        this.nameStorage = nameStorage;
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                nameStorage.remove(event.getPlayer().getUniqueId()); // Be safe, remove long enough so the entity is removed
            }
        }.runTaskLater(this.plugin, 5);
    }

}
