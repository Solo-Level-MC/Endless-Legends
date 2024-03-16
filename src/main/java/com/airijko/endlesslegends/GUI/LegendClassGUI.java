package com.airijko.endlesslegends.gui;

import com.airijko.endlesslegends.legends.ClassType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LegendClassGUI {
    private Inventory gui;

    public Inventory getInventory() {
        return gui;
    }

    public void classSelectionGUI(Player player) {
        gui = Bukkit.createInventory(null, InventoryType.CHEST, Component.text("Class Selection"));
        ItemStack archerItem = createItem(Material.BOW, ClassType.ARCHER);
        ItemStack warriorItem = createItem(Material.IRON_SWORD, ClassType.WARRIOR);
        ItemStack assassinItem = createItem(Material.DIAMOND_SWORD, ClassType.ASSASSIN);
        ItemStack tankItem = createItem(Material.SHIELD, ClassType.TANK);
        gui.setItem(4, archerItem);
        gui.setItem(11, warriorItem);
        gui.setItem(15, assassinItem);
        gui.setItem(22, tankItem);
        player.openInventory(gui);
    }

    private ItemStack createItem(Material material, ClassType classType) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            // Translate color codes in displayName
            String displayName = classType.getDisplayName();
            Component translatedDisplayName = LegacyComponentSerializer.legacyAmpersand().deserialize(displayName);
            meta.displayName(translatedDisplayName);
            List<String> description = classType.getDescription();
            List<Component> lore = new ArrayList<>();
            LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
            for (String line : description) {
                Component translatedLine = serializer.deserialize(line);
                lore.add(translatedLine);
            }
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
