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
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande doit être exécutée par un joueur.");
            return true;
        }

        // Récupère le joueur qui a exécuté la commande
        Player player = (Player) sender;

        // Créer l'objet coffre
        ItemStack lootbox = new ItemStack(Material.CHEST);
        ItemMeta meta = lootbox.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.BLUE + "Lootbox");
        lootbox.setItemMeta(meta);

        // Ajouter l'objet à l'inventaire du joueur
        player.getInventory().addItem(lootbox);
        player.sendMessage(ChatColor.DARK_GREEN + "Vous avez reçu une Lootbox.");

        return true;
    }
}