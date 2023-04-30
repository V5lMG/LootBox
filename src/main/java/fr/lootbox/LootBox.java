package fr.lootbox;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LootBox extends JavaPlugin {

    @Override
    public void onEnable() {

        ConfigManager.loadConfig(this);

        // Enregistrer les commandes et les événements
        Objects.requireNonNull(getCommand("box")).setExecutor(new LootBoxCommand());
        getServer().getPluginManager().registerEvents(new LootBoxListener(this), this);
        getServer().getPluginManager().registerEvents(new LootBoxSecurity(this), this);

        getLogger().info("""


                 _                    _   ______                                 \s
                | |                  | |  | ___ \\                                \s
                | |      ___    ___  | |_ | |_/ /  ___  __  __        ___   _ __ \s
                | |     / _ \\  / _ \\ | __|| ___ \\ / _ \\ \\ \\/ /       / _ \\ | '_ \\\s
                | |____| (_) || (_) || |_ | |_/ /| (_) | >  <       | (_) || | | |
                \\_____/ \\___/  \\___/  \\__|\\____/  \\___/ /_/\\_\\       \\___/ |_| |_|
                                                                                 \s
                                                                                 \s
                """);

    }

    @Override
    public void onDisable() {
        getLogger().info("""


                 _                    _   ______                          __   __\s
                | |                  | |  | ___ \\                        / _| / _|
                | |      ___    ___  | |_ | |_/ /  ___  __  __     ___  | |_ | |_\s
                | |     / _ \\  / _ \\ | __|| ___ \\ / _ \\ \\ \\/ /    / _ \\ |  _||  _|
                | |____| (_) || (_) || |_ | |_/ /| (_) | >  <    | (_) || |  | | \s
                \\_____/ \\___/  \\___/  \\__|\\____/  \\___/ /_/\\_\\    \\___/ |_|  |_| \s
                                                                                 \s
                                                                                 \s
                """);

    }


}