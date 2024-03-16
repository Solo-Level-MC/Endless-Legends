package com.airijko.endlesslegends.managers;

import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.legends.Rank;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LegendManager {
    private final JavaPlugin plugin;

    public LegendManager(JavaPlugin plugin) {
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

    public Legend chooseClass(String chosenClassName) {
        List<Rank> weightedRanks = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE) {
                for (int i = 0; i < rank.getWeight(); i++) {
                    weightedRanks.add(rank);
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(weightedRanks.size());
        Rank randomRank = weightedRanks.get(randomIndex);

        return loadLegend(chosenClassName, randomRank);
    }
}
