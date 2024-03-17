package com.airijko.endlesslegends.mechanics;

import com.airijko.endlesslegends.EndlessLegends;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.settings.Config;
import com.airijko.endlesslegends.utils.TitleDisplay;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RebornMechanic {
    private final PlayerDataManager playerDataManager;
    private final Map<UUID, Long> lastDeathTimes = new HashMap<>();
    private final int rebornCooldown;

    public RebornMechanic(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.rebornCooldown = JavaPlugin.getPlugin(EndlessLegends.class).getPluginConfig().getInt(Config.REBORN_COOLDOWN.getPath());
    }

    public int getRebornCooldown() {
        return this.rebornCooldown;
    }

    public void setLastDeathTimes(Player player) {
        long currentTime = System.currentTimeMillis() / 1000;
        lastDeathTimes.put(player.getUniqueId(), currentTime);
    }

    public void handlePlayerDeath(Player player) {
        EndlessLegends plugin = JavaPlugin.getPlugin(EndlessLegends.class);
        boolean resetClassOnDeath = plugin.getPluginConfig().getBoolean(Config.RESET_CLASS_ON_DEATH.getPath());
        UUID playerUUID = player.getUniqueId();

        deathCooldown(player);

        if (resetClassOnDeath) {
            playerDataManager.resetToDefaultClass(playerUUID);
        }
    }

    public void deathCooldown(Player player) {
        UUID playerUUID = player.getUniqueId();
        long lastDeathTime = lastDeathTimes.getOrDefault(playerUUID, 0L);
        long currentTime = System.currentTimeMillis() / 1000;

        if (currentTime - lastDeathTime < getRebornCooldown()) {
            player.sendMessage("On cooldown");
            String title = "<red><b> YOU DIED! </b></red>";
            String subtitle = "<dark_red> demoted </dark_red>";
            TitleDisplay.sendTitle(player, title, subtitle);
        } else {
            rebornPlayer(player);
        }

        setLastDeathTimes(player);
    }

    public void rebornPlayer(Player player) {
        player.sendMessage("Reborn");

        String title = "<green><b> REBORN! </b></green>";
        String subtitle = "<yellow> choose a class </yellow>";
        TitleDisplay.sendTitle(player, title, subtitle);
    }
}
