package com.airijko.endlesslegends.listeners;

import com.airijko.endlesslegends.EndlessLegends;
import com.airijko.endlesslegends.gui.LegendClassGUI;

import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.managers.LegendManager;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.settings.Config;
import com.airijko.endlesslegends.settings.Messages;
import com.airijko.endlesslegends.utils.TitleDisplay;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EndlessGUIListener implements Listener {
    private final LegendClassGUI legendClassGUI;
    private final LegendManager legendManager;
    private final PlayerDataManager playerDataManager;
    public EndlessGUIListener(LegendClassGUI legendClassGUI, LegendManager legendManager, PlayerDataManager playerDataManager) {
        this.legendClassGUI = legendClassGUI;
        this.legendManager = legendManager;
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void handleEndlessGUI(InventoryClickEvent event) {
        // Retrieve the Inventory object from the EndlessSkillsGUI instance
        Inventory guiInventory = legendClassGUI.getInventory();

        // Check if the inventory involved in the event is the gui inventory
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(guiInventory)) {
            // Check for actions that could move items out of the gui
            cancelItemMovement(event);

            // Handle attribute level increase
            Player player = (Player) event.getWhoClicked();
            handleAction(event, player);

            legendClassGUI.classSelectionGUI(player);
        }
    }

    private void cancelItemMovement(InventoryClickEvent event) {
        // Check for actions that could move items out of the gui
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                event.getAction() == InventoryAction.PICKUP_ALL ||
                event.getAction() == InventoryAction.PICKUP_HALF ||
                event.getAction() == InventoryAction.PICKUP_ONE ||
                event.getAction() == InventoryAction.PICKUP_SOME) {
            // Cancel the event to prevent any interaction with the custom gui
            event.setCancelled(true);
        }
    }

    private void handleAction(InventoryClickEvent event, Player player) {
        long remainingCooldown = getRemainingCooldown(player);
        if (remainingCooldown > 0) {
            player.sendMessage(Messages.ON_COOLDOWN.format(remainingCooldown));
            return;
        }

        // Get the clicked item
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null) {
            selectClass(clickedItem, player);
            legendClassGUI.closeForPlayer(player);
        }
    }

    private long getRemainingCooldown(Player player) {
        EndlessLegends plugin = JavaPlugin.getPlugin(EndlessLegends.class);
        long classChangeCooldownSeconds = plugin.getPluginConfig().getInt(Config.CLASS_CHANGE_COOLDOWN.getPath());

        long lastClassChangeTimeSeconds = legendClassGUI.getLastClassChangeTime(player);
        long currentSecond = System.currentTimeMillis() / 1000;
        return classChangeCooldownSeconds - (currentSecond - lastClassChangeTimeSeconds);
    }

    private void selectClass(ItemStack clickedItem, Player player) {
        // Get the item's type
        Material itemType = clickedItem.getType();

        // Choose the class based on the item type
        String className;
        switch (itemType) {
            case BOW:
                className = "Archer";
                break;
            case IRON_SWORD:
                className = "Warrior";
                break;
            case DIAMOND_SWORD:
                className = "Assassin";
                break;
            case SHIELD:
                className = "Tank";
                break;
            default:
                return;
        }

        Legend chosenClass = legendManager.chooseClass(className);
        playerDataManager.setPlayerClassAndRank(player.getUniqueId(), chosenClass, chosenClass.rank.name());

        // Set the last class change time to the current time
        legendClassGUI.setLastClassChangeTime(player);

        String title = "<green><b> " + className.toUpperCase() + " CLASS.</b></green>";
        String subtitle = "<yellow>" + playerDataManager.getPlayerRank(player.getUniqueId()) + " Rank Hunter</yellow>";
        TitleDisplay.sendTitle(player, title, subtitle);
    }
}
