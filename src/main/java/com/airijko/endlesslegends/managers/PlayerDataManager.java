package com.airijko.endlesslegends.managers;

import com.airijko.endlesscore.EndlessCore;

import com.airijko.endlesslegends.managers.LegendManager;
import com.airijko.endlesslegends.legends.ClassType;
import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.legends.Rank;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerDataManager {
    public final JavaPlugin plugin;
    public final LegendManager legendManager;

    public PlayerDataManager(JavaPlugin plugin, LegendManager legendManager) {
        this.plugin = plugin;
        this.legendManager = legendManager;
    }

    public File getPlayerDataFile(UUID playerUUID) {
        File playerFile = new File(plugin.getDataFolder() + File.separator + "playerdata", playerUUID + ".yml");
        if (!playerFile.exists()) {
            try {
                boolean fileCreated = playerFile.createNewFile();
                if (!fileCreated) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to create player data file for UUID " + playerUUID);
                }
                YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
                Player player = plugin.getServer().getPlayer(playerUUID);
                if (player != null) {
                    playerData.set("uuid", playerUUID.toString());
                    playerData.set("name", player.getName());
                    playerData.save(playerFile);
                    setDefaultClass(playerUUID);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create player data file for UUID " + playerUUID, e);
            }
        }
        return playerFile;
    }


    public Legend getPlayerData(UUID playerUUID) {
        File playerFile = getPlayerDataFile(playerUUID);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        return loadPlayerData(playerUUID, playerData);
    }

    private Legend loadPlayerData(UUID playerUUID, YamlConfiguration playerData) {
        String className = playerData.getString("Legend.Class");
        String rankName = playerData.getString("Legend.Rank");

        // If the class or rank is null, set the default class and rank
        if (className == null || rankName == null) {
            return setDefaultClass(playerUUID);
        }

        Rank rank = Rank.valueOf(rankName);
        return new LegendManager(plugin).loadLegend(className, rank);
    }

    public Legend setDefaultClass(UUID playerUUID) {
        File configFile = new File(plugin.getDataFolder() + "/legends", "Default.yml");
        if (!configFile.exists()) {
            plugin.getLogger().severe("Default class not found");
            return null;
        }

        Legend defaultClass = new Legend(configFile, Rank.NONE);
        setPlayerClassAndRank(playerUUID, defaultClass, Rank.NONE.name());
        return defaultClass;
    }

    public void setPlayerClassAndRank(UUID playerUUID, Legend chosenClass, String rank) {
        Player player = Bukkit.getPlayer(playerUUID);
        Legend legend = legendManager.loadLegend(chosenClass.className, Rank.valueOf(rank));
        chosenClass.className = legend.className;
        chosenClass.type = legend.type;
        chosenClass.rank = Rank.valueOf(rank);
        savePlayerData(playerUUID, chosenClass);
        EndlessCore.getInstance().getAttributeManager().applyAttributeModifiers(player);
    }

    public ClassType getPlayerClassType(UUID playerUUID) {
        Legend playerClass = getPlayerData(playerUUID);
        return ClassType.valueOf(playerClass.type.toUpperCase());
    }

    public Rank getPlayerRank(UUID playerUUID) {
        Legend playerClass = getPlayerData(playerUUID);
        return playerClass.rank;
    }

    public Rank setPlayerRank(UUID playerUUID, Rank rank) {
        Legend playerClass = getPlayerData(playerUUID);
        playerClass.rank = rank;
        savePlayerData(playerUUID, playerClass);
        return playerClass.rank;
    }

    public void savePlayerData(UUID playerUUID, Legend chosenClass) {
        File playerFile = getPlayerDataFile(playerUUID);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        playerData.set("Legend.Class", chosenClass.className);
        playerData.set("Legend.Rank", chosenClass.rank.name());
        playerData.set("Legend.Type", chosenClass.type);
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save player data for UUID " + playerUUID, e);
        }
    }
}