package com.owen1212055.customname;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomNamePlugin extends JavaPlugin implements Listener {

    private final CustomNameManager customNameManager = new CustomNameManager();

    @Override
    public void onEnable() {
        super.onEnable();
        this.customNameManager.registerListeners(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        CustomName name = this.customNameManager.forEntity(event.getRightClicked());
        name.setName(MiniMessage.miniMessage().deserialize("<rainbow>%s</rainbow>".formatted(event.getPlayer().getName())));
    }

    public CustomNameManager getCustomNameManager() {
        return this.customNameManager;
    }
}