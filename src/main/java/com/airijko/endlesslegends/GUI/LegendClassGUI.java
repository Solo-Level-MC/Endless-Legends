package com.airijko.endlesslegends.gui;

import com.airijko.endlesslegends.EndlessLegends;
import com.airijko.endlesslegends.legends.ClassType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class LegendClassGUI {
    private Inventory gui;
    private final Map<UUID, Long> lastClassChangeTimes = new HashMap<>();
    public Inventory getInventory() {
        return gui;
    }

    public long getLastClassChangeTime(Player player) {
        return lastClassChangeTimes.getOrDefault(player.getUniqueId(), 0L);
    }

    public void setLastClassChangeTime(Player player) {
        long currentTime = System.currentTimeMillis() / 1000;
        lastClassChangeTimes.put(player.getUniqueId(), currentTime);
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

        fillEmptySlots(gui);
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

    private void fillEmptySlots(Inventory inventory) {
        ItemStack invisibleItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = invisibleItem.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(" "));
            invisibleItem.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, invisibleItem);
            }
        }
    }

    public void closeForAllPlayers() {
        Inventory guiInventory = getInventory(); // Get the custom GUI's inventory

        for (Player player : Bukkit.getOnlinePlayers()) {
            Inventory openInventory = player.getOpenInventory().getTopInventory();
            if (openInventory.equals(guiInventory)) { // Check if the open inventory is the custom GUI's inventory
                player.closeInventory();
                Bukkit.getLogger().info("Closed skills GUI for player: " + player.getName());
            }
        }

        Bukkit.getLogger().info("Finished closing skills GUI for all online players.");
    }

    public void closeForPlayer(Player player) {
        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(EndlessLegends.class), () -> player.closeInventory(), 1);
    }
}
