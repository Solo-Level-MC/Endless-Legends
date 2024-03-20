package com.airijko.endlesslegends.listeners;

import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.mechanics.RebornMechanic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerEventListener implements Listener {
    private final PlayerDataManager playerDataManager;

    public PlayerEventListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        playerDataManager.getPlayerDataFile(player);
    }
}