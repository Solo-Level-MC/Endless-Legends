package com.airijko.endlesslegends.listeners;

import com.airijko.endlesslegends.gui.LegendClassGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class EndlessGUIListener implements Listener {
    private final LegendClassGUI legendClassGUI;
    public EndlessGUIListener(LegendClassGUI legendClassGUI) {
        this.legendClassGUI = legendClassGUI;
    }
    @EventHandler
    public void handleEndlessGUI(InventoryClickEvent event) {
        // Retrieve the Inventory object from the EndlessSkillsGUI instance
        Inventory guiInventory = legendClassGUI.getInventory();

        // Check if the inventory involved in the event is the gui inventory
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(guiInventory)) {

            // Check for actions that could move items out of the gui
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(guiInventory)) {
                cancelItemMovement(event);

                // Handle attribute level increase
                Player player = (Player) event.getWhoClicked();

                legendClassGUI.classSelectionGUI(player);
            }
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
}
