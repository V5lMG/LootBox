package fr.lootbox;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;


public class LootBoxSecurity implements Listener {

    public final LootBox plugin;

    public LootBoxSecurity(LootBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.CHEST && block.getState() instanceof Chest chest) {
            if (chest.getCustomName() != null && chest.getCustomName().equals(ChatColor.BLUE + "Lootbox")) {
                Player player = event.getPlayer();
                player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission pour casser ce bloc.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.BLUE + "Lootbox")) {
            event.setCancelled(true);
        }
    }



}
