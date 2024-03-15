package com.airijko.endlesslegends.legends;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LegendLoader {
    private final JavaPlugin plugin;

    public LegendLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Legend loadLegend(String className, Rank rank) {
        File configFile = new File(plugin.getDataFolder() + "/legends", className + ".yml");
        if (!configFile.exists()) {
            plugin.getLogger().severe("Legend class not found: " + className);
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String rankInFile = config.getString("Legend.Rank");

        if ("DYNAMIC".equals(rankInFile)) {
            config.set("Legend.Rank", rank.name());
        }

        return new Legend(configFile, rank);
    }
}
