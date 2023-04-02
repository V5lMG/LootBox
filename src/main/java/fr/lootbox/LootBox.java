package fr.lootbox;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LootBox extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.loadConfig();

        // Enregistrer les commandes et les événements
        Objects.requireNonNull(getCommand("box")).setExecutor(new LootBoxCommand());
        getServer().getPluginManager().registerEvents(new LootBoxListener(this), this);

    }

    @Override
    public void onDisable() {

    }

}
