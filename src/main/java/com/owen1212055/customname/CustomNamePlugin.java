package com.owen1212055.customname;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomNamePlugin extends JavaPlugin implements Listener {

    private final CustomNameManager customNameManager = new CustomNameManager();

    @Override
    public void onEnable() {
        super.onEnable();
        this.customNameManager.registerListeners(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public CustomNameManager getCustomNameManager() {
        return this.customNameManager;
    }
}