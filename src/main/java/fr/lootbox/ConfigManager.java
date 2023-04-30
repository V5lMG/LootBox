package fr.lootbox;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static FileConfiguration config;


    public static void loadConfig(LootBox plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public static Map<Integer, Map<String, Object>> getItemsConfig() {
        Map<Integer, Map<String, Object>> itemsConfig = new HashMap<>();
        ConfigurationSection itemsSection = config.getConfigurationSection("items");
        if (itemsSection == null) return itemsConfig;
        for (String key : itemsSection.getKeys(false)) {
            int id = Integer.parseInt(key);
            Map<String, Object> itemData = new HashMap<>();
            itemData.put("name", config.getString("items." + key + ".name"));
            itemData.put("quantity", config.getInt("items." + key + ".quantity"));
            itemData.put("percent", config.getDouble("items." + key + ".percent"));
            itemsConfig.put(id, itemData);
        }
        return itemsConfig;
    }

    public static int getMaxQuantity() {
        int maxQuantity = 0;
        ConfigurationSection itemsSection = config.getConfigurationSection("items");
        if (itemsSection == null) return 0;
        for (String key : itemsSection.getKeys(false)) {
            int quantity = config.getInt("items." + key + ".quantity");
            if (quantity > maxQuantity) {
                maxQuantity = quantity;
            }
        }
        return maxQuantity/64;
    }
}
