package com.owen1212055.customname.listener;

import com.owen1212055.customname.CustomName;
import com.owen1212055.customname.CustomNameStorage;
import io.papermc.paper.event.player.PlayerTrackEntityEvent;
import io.papermc.paper.event.player.PlayerUntrackEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTrackerListener implements Listener {

    private final CustomNameStorage nameStorage;

    public PlayerTrackerListener(CustomNameStorage nameStorage) {
        this.nameStorage = nameStorage;
    }

    @EventHandler
    public void trackEntity(PlayerTrackEntityEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getEntity().getUniqueId());
        // Does the entity have a custom name?
        if (playerName != null) {
            playerName.sendToClient(event.getPlayer());
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
