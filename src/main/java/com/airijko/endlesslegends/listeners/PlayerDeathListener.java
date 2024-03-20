package com.airijko.endlesslegends.listeners;

import com.airijko.endlesslegends.mechanics.RebornMechanic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final RebornMechanic rebornMechanic;

    public PlayerDeathListener(RebornMechanic rebornMechanic) {
        this.rebornMechanic = rebornMechanic;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        rebornMechanic.handlePlayerDeath(player);
    }
}