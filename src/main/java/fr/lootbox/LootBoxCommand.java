package fr.lootbox;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class LootBoxCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Vérifie si la commande est exécutée par un joueur
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande doit être exécutée par un joueur.");
            return true;
        }

        // Créer l'objet coffre
        ItemStack lootbox = new ItemStack(Material.CHEST);
        ItemMeta meta = lootbox.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.BLUE + "Lootbox");
            lootbox.setItemMeta(meta);
        } else {
            player.sendMessage(ChatColor.RED + "Une erreur est survenue lors de la création de l'objet Lootbox.");
        }

        // Ajouter l'objet à l'inventaire du joueur
        player.getInventory().addItem(lootbox);
        player.sendMessage(ChatColor.DARK_GREEN + "Vous avez reçu une Lootbox.");

        return true;
    }
}