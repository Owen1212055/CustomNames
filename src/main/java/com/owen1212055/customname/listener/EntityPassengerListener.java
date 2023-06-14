package com.owen1212055.customname.listener;

import com.owen1212055.customname.CustomName;
import com.owen1212055.customname.CustomNameStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

/**
 * Responsible for hiding the name on entities that have
 * vehicles on them.
 *
 * This matches vanilla behavior.
 */
public class EntityPassengerListener implements Listener {

    private final JavaPlugin plugin;
    private final CustomNameStorage nameStorage;

    public EntityPassengerListener(CustomNameStorage nameStorage, JavaPlugin plugin) {
        this.nameStorage = nameStorage;
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void mountEntity(EntityMountEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getMount().getUniqueId());

        if (playerName != null) {
            playerName.setHidden(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void dismountEntity(EntityDismountEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getDismounted().getUniqueId());

        if (playerName != null && event.getDismounted().getPassengers().size() == 1) {
            // Run 2 ticks later, we need to ensure that the game sends the packets to update the
            // passengers.
            new BukkitRunnable(){

                @Override
                public void run() {
                    playerName.setHidden(false);
                }
            }.runTaskLater(this.plugin, 2);
        }
    }

}
