package com.owen1212055.customname.listener;

import com.owen1212055.customname.CustomName;
import com.owen1212055.customname.CustomNameStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Responsible for forwarding sneaking state over the
 * nametag entity. This causes the name tag to appear transparent
 * when sneaking.
 *
 * This matches vanilla behavior.
 */
public class PlayerSneakListener implements Listener {

    private final CustomNameStorage nameStorage;

    public PlayerSneakListener(CustomNameStorage nameStorage) {
        this.nameStorage = nameStorage;
    }

    @EventHandler(ignoreCancelled = true)
    public void toggleSneak(PlayerToggleSneakEvent event) {
        CustomName playerName = this.nameStorage.getCustomPlayerName(event.getPlayer().getUniqueId());
        // Does the entity have a custom name?
        if (playerName != null) {
            playerName.setTargetEntitySneaking(event.isSneaking());
        }
    }

}
