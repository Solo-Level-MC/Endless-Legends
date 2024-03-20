package com.airijko.endlesslegends.mechanics;

import com.airijko.endlesscore.interfaces.RespawnInterface;
import com.airijko.endlesscore.utils.TitleDisplay;

import com.airijko.endlesslegends.EndlessLegends;
import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.settings.Config;
import com.airijko.endlesslegends.legends.Rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RebornMechanic implements RespawnInterface {
    private final PlayerDataManager playerDataManager;
    private final Map<UUID, Long> lastDeathTimes = new HashMap<>();
    private final Map<UUID, Boolean> playerAwakenedMap = new HashMap<>();
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

        if (resetClassOnDeath) {
            deathCooldown(player);
        }
    }

    @Override
    public void handleRespawn(Player player) {
        boolean playerAwakened = playerAwakenedMap.getOrDefault(player.getUniqueId(), false);

        if (playerAwakened) {
            reAwakened(player);
        }

        playerAwakenedMap.remove(player.getUniqueId());
    }

    public void deathCooldown(Player player) {
        UUID playerUUID = player.getUniqueId();
        Rank currentRank = playerDataManager.getPlayerRank(playerUUID);

        long lastDeathTime = lastDeathTimes.getOrDefault(playerUUID, 0L);
        long currentTime = System.currentTimeMillis() / 1000;

        boolean playerAwakened = playerAwakened();

        if (currentRank == Rank.NONE || currentTime - lastDeathTime >= getRebornCooldown()) {
            if (currentRank == Rank.S || currentRank == Rank.NONE) {
                playerAwakened = false;
            }

            if (playerAwakened) {
                player.sendMessage("re-awakening...");
                playerAwakenedMap.put(player.getUniqueId(), true);
            } else {
                playerDeath(player);
                setLastDeathTimes(player);
            }

        } else {
            String title = "<red><b> YOU DIED! </b></red>";

            Rank newRank = Rank.getRandomRankLowerOrEqual(currentRank);
            playerDataManager.setPlayerRank(playerUUID, newRank);

            String subtitle = (newRank == currentRank) ?
                    "<yellow> maintained rank " + newRank.name() + " </yellow>" :
                    "<dark_red> demoted to " + newRank.name() + " </dark_red>";

            TitleDisplay.sendTitle(player, title, subtitle);
        }
    }

    public void playerDeath(Player player) {
        player.sendMessage("<yellow> Skill Issue... Choose a class! </yellow>");
        playerDataManager.resetToDefaultClass(player.getUniqueId());

        String title = "<red><b> YOU DIED! </b></red>";
        String subtitle = "<yellow> choose a class </yellow>";
        TitleDisplay.sendTitle(player, title, subtitle);
    }

    public void reAwakened(Player player) {
        UUID playerUUID = player.getUniqueId();
        Rank currentRank = playerDataManager.getPlayerRank(playerUUID);
        Rank newRank = Rank.getRandomRankHigher(currentRank);
        playerDataManager.setPlayerRank(playerUUID, newRank);

        String title = "<blue><b> AWAKENED! </b></blue>";
        String subtitle = "<green> Promoted to " + newRank.name() + " </green>";
        TitleDisplay.sendTitle(player, title, subtitle);
    }

    public boolean playerAwakened() {
        EndlessLegends plugin = JavaPlugin.getPlugin(EndlessLegends.class);
        double awakenChance = plugin.getPluginConfig().getDouble(Config.AWAKEN_CHANCE.getPath());
        return new Random().nextDouble() <= awakenChance / 100;
    }
}
