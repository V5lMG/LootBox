package fr.lootbox;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigManager {
    // Déclare une variable statique qui contient l'emplacement du fichier config.yml
    private static final File configFile = new File("plugins/LootBox/config.yml");

    // Déclare une variable statique qui contiendra les données de configuration chargées depuis le fichier YAML
    private static YamlConfiguration config;

    // Méthode pour charger le fichier de configuration et initialiser la variable config
    public static void loadConfig() {
        if (!configFile.exists()) {
            // Si le fichier n'existe pas, le créer avec les valeurs par défaut
            createDefaultConfig();
        }
        // Charger les données de configuration à partir du fichier
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    // Méthode pour récupérer les données d'élément à partir du fichier de configuration
    public static Map<Integer, Map<String, Object>> getItemsConfig() {
        // Créer une nouvelle map pour stocker les données d'élément
        Map<Integer, Map<String, Object>> itemsConfig = new HashMap<>();
        // Parcourir toutes les clés dans la section "items" du fichier YAML
        for (String key : Objects.requireNonNull(config.getConfigurationSection("items")).getKeys(false)) {
            // Convertir la clé en entier pour obtenir l'ID de l'élément
            int id = Integer.parseInt(key);
            // Créer une nouvelle map pour stocker les données de l'élément
            Map<String, Object> itemData = new HashMap<>();
            // Récupérer le nom, la quantité et le pourcentage de l'élément à partir du fichier YAML
            itemData.put("name", config.getString("items." + key + ".name"));
            itemData.put("quantity", config.getInt("items." + key + ".quantity"));
            itemData.put("percent", config.getDouble("items." + key + ".percent"));
            // Ajouter les données de l'élément à la map itemsConfig
            itemsConfig.put(id, itemData);
        }
        // Retourner la map itemsConfig contenant toutes les données d'élément
        return itemsConfig;
    }

    // Méthode pour enregistrer les modifications apportées au fichier de configuration
    public static void saveConfig() {
        try {
            // Enregistrer les données de configuration dans le fichier YAML
            config.save(configFile);
        } catch (IOException e) {
            // Afficher une trace d'erreur si une exception est levée
            e.printStackTrace();
        }
    }

    // Méthode pour créer un fichier de configuration avec des valeurs par défaut
    private static void createDefaultConfig() {
        // Créer un nouveau fichier de configuration avec des sections et des valeurs par défaut
        config = new YamlConfiguration();
        config.createSection("items");
        config.set("items.1.name", "IRON_INGOT");
        config.set("items.1.quantity", 16);
        config.set("items.1.percent", 50.0);
        config.set("items.2.name", "GOLD_INGOT");
        config.set("items.2.quantity", 8);
        config.set("items.2.percent", 25.0);
        config.set("items.3.name", "DIAMOND");
        config.set("items.3.quantity", 4);
        config.set("items.3.percent", 12.5);
        config.set("items.4.name", "EMERALD");
        config.set("items.4.quantity", 2);
        config.set("items.4.percent", 6.25);
        // Enregistrer les données de configuration dans le fichier YAML
        saveConfig();
    }
}