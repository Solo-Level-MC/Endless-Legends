package com.airijko.endlesslegends.managers;

import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.legends.Rank;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerDataManager {
    private final Map<Player, Legend> playerData;
    public final JavaPlugin plugin;

    public PlayerDataManager(JavaPlugin plugin) {
        playerData = new HashMap<>();
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
        double lifeForce = playerData.getDouble("Legend.Attributes.Life_Force");
        double strength = playerData.getDouble("Legend.Attributes.Strength");
        double toughness = playerData.getDouble("Legend.Attributes.Tenacity.Toughness");
        double knockbackResistance = playerData.getDouble("Legend.Attributes.Tenacity.Knockback_Resistance");
        double speed = playerData.getDouble("Legend.Attributes.Haste.Speed");
        double attackSpeed = playerData.getDouble("Legend.Attributes.Haste.Attack_Speed");
        double precision = playerData.getDouble("Legend.Attributes.Precision");
        double ferocity = playerData.getDouble("Legend.Attributes.Ferocity");
        Rank rank = Rank.valueOf(playerData.getString("Legend.Rank"));

        return new Legend(lifeForce, strength, toughness, knockbackResistance, speed, attackSpeed, precision, ferocity, rank);
    }

    private void createPlayerDataFolder() {
        File playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            if (!playerDataFolder.mkdir()) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create the playerdata folder.");
            }
        }
    }

    public void setPlayerClassAndRank(UUID playerUUID, Legend chosenClass, String rank) {
        File playerFile = new File(plugin.getDataFolder() + File.separator + "playerdata", playerUUID + ".yml");
        try {
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
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while saving player class and rank.", e);
        }
    }
}