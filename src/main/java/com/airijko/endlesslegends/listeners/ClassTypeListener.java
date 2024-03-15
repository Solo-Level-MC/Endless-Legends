package com.airijko.endlesslegends.listeners;

import com.airijko.endlesslegends.legends.ClassType;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClassTypeListener implements Listener {
    private final PlayerDataManager playerDataManager;
    public ClassTypeListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null) {
            ClassType playerClassType = playerDataManager.getPlayerClassType(player.getUniqueId());
            switch (playerClassType) {
                case WARRIOR:
                    if (playerClassType.canUse(item)) {
                        player.sendMessage("Warriors cannot use this item!");
                        event.setCancelled(true);
                    }
                    break;
                case ARCHER:
                    if (playerClassType.canUse(item)) {
                        player.sendMessage("Archers cannot use this item!");
                        event.setCancelled(true);
                    }
                    break;
                    case ASSASSIN:
                    if (playerClassType.canUse(item)) {
                        player.sendMessage("Assassins cannot use this item!");
                        event.setCancelled(true);
                    }
                    break;
                case TANK:
                    if (playerClassType.canUse(item)) {
                        player.sendMessage("Tanks cannot use this item!");
                        event.setCancelled(true);
                    }
                    break;
                default:
                    if (playerClassType.canUse(item)) {
                        player.sendMessage("Your class cannot use this item!");
                        event.setCancelled(true);
                    }
                    break;
            }
        }
    }
}
