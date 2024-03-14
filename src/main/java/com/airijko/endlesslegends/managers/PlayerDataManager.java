package com.airijko.endlesslegends.managers;

import com.airijko.endlesscore.EndlessCore;
import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.legends.LegendFactory;
import com.airijko.endlesslegends.legends.Rank;
import com.airijko.endlesslegends.legends.Default;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerDataManager {
    public final JavaPlugin plugin;

    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        createPlayerDataFolder();
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
        return new LegendFactory().getLegend(className, rank);
    }

    private void createPlayerDataFolder() {
        File playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            if (!playerDataFolder.mkdir()) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create the playerdata folder.");
            }
        }
    }

    public Legend setDefaultClass(UUID playerUUID) {
        Legend defaultClass = new Default();
        setPlayerClassAndRank(playerUUID, defaultClass, Rank.NONE.name());
        return defaultClass;
    }

    public void setPlayerClassAndRank(UUID playerUUID, Legend chosenClass, String rank) {
        File playerFile = new File(plugin.getDataFolder() + File.separator + "playerdata", playerUUID + ".yml");
        try {
            Player player = plugin.getServer().getPlayer(playerUUID);
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
            playerData.set("Legend.Class", chosenClass.getClass().getSimpleName());
            playerData.set("Legend.Rank", rank);
            playerData.set("Legend.Attributes.Life_Force", chosenClass.lifeForce);
            playerData.set("Legend.Attributes.Strength", chosenClass.strength);
            playerData.set("Legend.Attributes.Tenacity.Toughness", chosenClass.toughness);
            playerData.set("Legend.Attributes.Tenacity.Knockback_Resistance", chosenClass.knockbackResistance);
            playerData.set("Legend.Attributes.Haste.Speed", chosenClass.speed);
            playerData.set("Legend.Attributes.Haste.Attack_Speed", chosenClass.attackSpeed);
            playerData.set("Legend.Attributes.Precision", chosenClass.precision);
            playerData.set("Legend.Attributes.Ferocity", chosenClass.ferocity);
            playerData.save(playerFile);
            EndlessCore.getInstance().getAttributeManager().applyAttributeModifiers(player);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while saving player class and rank.", e);
        }
    }
}