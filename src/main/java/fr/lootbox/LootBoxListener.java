package fr.lootbox;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class LootBoxListener implements Listener {
    // Equivalent du __init__ en python
    private final LootBox plugin;
    private Map<Integer, Map<String, Object>> itemsConfig;
    private static final int MAX_INTERFACE_SLOTS = 27;

    public LootBoxListener(LootBox plugin) {
        this.plugin = plugin;
    }

    // Lancement des sous-programmes quand une action est effectuée avec le coffre
    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;

        if (clickedBlock.getType() == Material.CHEST && clickedBlock.getState() instanceof Chest) {

            Chest chest = (Chest) clickedBlock.getState();


            if (chest.getCustomName() != null && chest.getCustomName().equals(ChatColor.BLUE + "Lootbox")) {
                if (action == Action.LEFT_CLICK_BLOCK) {
                    player.closeInventory();
                    AfficherInterface(player);
                    // Définition de l'action à effectuer après 3 secondes
                    Bukkit.getScheduler().runTaskLater(plugin, player::closeInventory, 60L);
                }

                if (action == Action.RIGHT_CLICK_BLOCK) {
                    player.closeInventory();
                    AfficherApercu(player);
                }
            }

        }
    }

    private void AfficherApercu(Player player) {
        // Récupération du bloc de la lootbox
        Block clickedBlock = player.getTargetBlockExact(5);

        // Vérification
        if (clickedBlock == null || clickedBlock.getType() != Material.CHEST) {
            return;
        }

        // Récupération de l'inventaire du coffre
        Chest chest = (Chest) clickedBlock.getState();
        Inventory chestInventory = chest.getBlockInventory();

        // Création de l'inventaire temporaire
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Aperçu des items");

        // Récupération des items dans la config
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("items");
        if (itemsSection == null) {
            player.sendMessage(ChatColor.RED + "La configuration de la loot box est incorrecte.");
            return;
        }

        int slot = 0;

        // Boucle sur le nombre d'item
        for (String key : itemsSection.getKeys(false)) {
            // Récupère le numéro de chaque item (section)
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            if (itemSection == null) {
                // Si la section n'existe pas, passer à l'item suivant
                continue;
            }

            // Récupère le nom, la quantité et le pourcentage de l'item
            String itemName = itemSection.getString("name");
            int itemQuantity = itemSection.getInt("quantity", 1);
            double itemPercent = itemSection.getDouble("percent", 0);

            if (itemName == null) {
                // Si le nom de l'item est null, passer à l'item suivant
                continue;
            }

            // Conversion du nom de l'item en objet Material
            Material itemType = Material.matchMaterial(itemName);
            if (itemType == null) {
                // Si le nom de l'item est invalide, passer à l'item suivant
                continue;
            }

            // Création de l'objet ItemStack correspondant à l'item
            ItemStack itemStack = new ItemStack(itemType, itemQuantity);

            // Modification de la meta de l'item, pour afficher son nom et sa probabilité, et le formater
            ItemMeta itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.AQUA + itemName.substring(0, 1).toUpperCase() + itemName.substring(1).toLowerCase());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_PURPLE + "Probabilité : " + itemPercent + "%");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);


            // Ajout de l'item dans l'inventaire temporaire à la position 'slot'
            inventory.setItem(slot, itemStack);
            slot++;
        }

        // Copie des éléments de l'inventaire créé précédemment dans l'inventaire du coffre
        chestInventory.setContents(inventory.getContents());

        // Utilisation de la classe pour rendre les items intouchables
        ChestMetadataManager.makeInventoryUnmodifiable(inventory);

        // Ouverture de l'inventaire pour le joueur
        player.openInventory(chestInventory);

    }

    // Action à effectuer sur un clique gauche
    private void AfficherInterface(Player player) {
        recupItemsConfig();
        createInterfaceAnimation(player);
        donnerRecompence(player);
    }

    public void recupItemsConfig() {
        // Charge la config
        itemsConfig = ConfigManager.getItemsConfig();
    }

    private void createInterfaceAnimation(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Lootbox");

        // Récupération des items dans la config
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("items");
        if (itemsSection == null) {
            player.sendMessage(ChatColor.RED + "La configuration de la loot box est incorrecte.");
            return;
        }

        // Mélanger les clés des sections pour obtenir des éléments aléatoires
        List<String> itemKeys = new ArrayList<>(itemsSection.getKeys(false));
        Collections.shuffle(itemKeys);

        // Boucle pour ajouter des éléments aléatoires dans l'inventaire
        int addedItems = 0; // nombre d'items ajoutés dans l'inventaire temporaire
        while (addedItems < 9) {
            // Choix aléatoire d'un élément dans la configuration
            String key = itemKeys.get(new Random().nextInt(itemKeys.size()));

            // Récupère le numéro de chaque item (section)
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            if (itemSection == null) {
                continue;
            }

            // Récupère le nom, la quantité et le pourcentage de l'item
            String itemName = itemSection.getString("name");
            int itemQuantity = itemSection.getInt("quantity", 1);
            double itemPercent = itemSection.getDouble("percent", 0);

            if (itemName == null || itemName.isEmpty()) {
                continue;
            }

            // Conversion du nom de l'item en objet Material
            Material itemType = Material.matchMaterial(itemName);
            if (itemType == null) {
                continue;
            }

            // Création de l'objet ItemStack correspondant à l'item
            ItemStack itemStack = new ItemStack(itemType, itemQuantity);

            // Vérification que l'objet ItemStack n'est pas nul ou correspond à de l'air (une case vide)
            if (itemStack.getType() == Material.AIR) {
                continue;
            }

            // Modification de la meta de l'item, pour afficher son nom et sa probabilité, et le formater
            ItemMeta itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.AQUA + itemName.substring(0, 1).toUpperCase() + itemName.substring(1).toLowerCase());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_PURPLE + "Probabilité : " + itemPercent + "%");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            // Choix d'un emplacement aléatoire dans l'inventaire
            int slot = new Random().nextInt(9);

            // Vérification que l'emplacement est vide
            if (inventory.getItem(slot) == null || Objects.requireNonNull(inventory.getItem(slot)).getType() == Material.AIR) {
                // Ajout de l'item dans l'inventaire temporaire à l'emplacement choisi
                inventory.setItem(slot, itemStack);
                addedItems++;
            }
        }



        // Affichage de l'inventaire temporaire pour le joueur
        player.openInventory(inventory);

        // Définition de l'action à effectuer après chaque changement de position
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!inventory.getViewers().contains(player)) {
                // Si l'inventaire a été fermé, ne pas relancer l'animation
                return;
            }
            createInterfaceAnimation(player);
        }, 10L);
    }

    public void donnerRecompence(Player player) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                // Choisir un item au hasard dans la config en tenant compte des probabilités
                // Envoyer dans le chat un message de récompense.
                int totalPercentages = 0;
                for (int i = 1; i <= MAX_INTERFACE_SLOTS && itemsConfig.containsKey(i); i++) {
                    Map<String, Object> itemData = itemsConfig.get(i);
                    double percentage = (double) itemData.get("percent");
                    totalPercentages += percentage;
                }
                double randomPercentage = Math.random() * totalPercentages;
                totalPercentages = 0;
                for (int i = 1; i <= MAX_INTERFACE_SLOTS && itemsConfig.containsKey(i); i++) {
                    Map<String, Object> itemData = itemsConfig.get(i);
                    double percentage = (double) itemData.get("percent");
                    totalPercentages += percentage;
                    if (randomPercentage < totalPercentages) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) itemData.get("name")), (int) itemData.get("quantity"));
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                        player.getInventory().addItem(itemStack);
                        int quantity = (int) itemData.get("quantity");
                        player.sendMessage(ChatColor.GREEN + "Vous avez reçu " + quantity + " " + itemStack.getType().name());
                        break;
                    }
                }
            }
        }, 3000); // Délai de 3 secondes en millisecondes
    }
}

