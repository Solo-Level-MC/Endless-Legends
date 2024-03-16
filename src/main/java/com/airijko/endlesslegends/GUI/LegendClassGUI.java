package com.airijko.endlesslegends.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class LegendClassGUI {
    private Inventory gui;

    public Inventory getInventory() {
        return gui;
    }

    public void classSelectionGUI(Player player) {
        gui = Bukkit.createInventory(null, InventoryType.CHEST, Component.text("Class Selection"));
        ItemStack warriorItem = createItem(Material.IRON_SWORD, "Warrior", "A strong and durable fighter.");
        ItemStack archerItem = createItem(Material.BOW, "Archer", "A ranged fighter with high precision.");
        ItemStack assassinItem = createItem(Material.DIAMOND_SWORD, "Assassin", "A stealthy fighter with high damage.");
        ItemStack tankItem = createItem(Material.SHIELD, "Tank", "A defensive fighter with high health.");
        gui.setItem(11, warriorItem);
        gui.setItem(13, archerItem);
        gui.setItem(15, assassinItem);
        gui.setItem(22, tankItem);
        player.openInventory(gui);
    }

    private ItemStack createItem(Material material, String className, String description) {
        return null;
    }

}
