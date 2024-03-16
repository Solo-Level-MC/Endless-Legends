package com.airijko.endlesslegends.legends;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ClassType {
    DEFAULT {
        @Override
        public boolean canUse(ItemStack item) {
            return false;
        }
    },
    WARRIOR {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW || item.getType() == Material.SHIELD;
        }
    },
    ARCHER {
        @Override
        public boolean canUse(ItemStack item) {
            String typeName = item.getType().name();
            return typeName.endsWith("_SWORD") || typeName.endsWith("_AXE") || typeName.equals(Material.TRIDENT.name());
        }
    },
    ASSASSIN {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.SHIELD;
        }
    },
    TANK {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW;
        }
    };

    private String displayName;
    private String description;
    private Map<String, Double> attributes = new HashMap<>();

    public abstract boolean canUse(ItemStack item);

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public static void loadFromYaml(JavaPlugin plugin) {
        for (ClassType classType : values()) {
            File configFile = new File(plugin.getDataFolder() + "/legends", classType.name() + ".yml");
            if (configFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                classType.displayName = config.getString(ClassPath.LEGEND_DISPLAY_NAME.getPath(), "Default Display Name");
                List<String> description = config.getStringList(ClassPath.LEGEND_DESCRIPTION.getPath());
                if (description.isEmpty()) {
                    description.add("Default Description");
                }
                classType.description = String.join("\n", description);
            } else {
                classType.description = "Default Description";
            }
        }
    }
}