package fr.lootbox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class ChestMetadataManager {

    public static void makeInventoryUnmodifiable(Inventory inventory) {
        // Récupération de la liste des emplacements de l'inventaire
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            slots.add(i);
        }

        // Création d'un ItemStack non modifiable
        ItemStack unmodifiable = new ItemStack(Material.AIR);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.AIR);
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET.toString());
        unmodifiable.setItemMeta(meta);

        // Définition d'un flag pour indiquer que l'item est intouchable
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        // Remplacement des items de l'inventaire par des items intouchables
        for (Integer slot : slots) {
            inventory.setItem(slot, unmodifiable);
        }
    }
}
