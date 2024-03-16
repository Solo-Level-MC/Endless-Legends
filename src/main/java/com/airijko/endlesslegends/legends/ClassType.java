package com.airijko.endlesslegends.legends;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.*;

public enum ClassType {
    DEFAULT("Default") {
        @Override
        public boolean canUse(ItemStack item) {
            return false;
        }
    },
    WARRIOR("Warrior") {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW || item.getType() == Material.SHIELD;
        }
    },
    ARCHER("Archer") {
        @Override
        public boolean canUse(ItemStack item) {
            String typeName = item.getType().name();
            return typeName.endsWith("_SWORD") || typeName.endsWith("_AXE") || typeName.equals(Material.TRIDENT.name());
        }
    },
    ASSASSIN("Assassin") {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.SHIELD;
        }
    },
    TANK("Paladin") {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW;
        }
    };
    private final String fileName;
    private String displayName;
    private List<String> description;
    private Map<String, Double> attributes = new HashMap<>();

    ClassType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract boolean canUse(ItemStack item);

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public static List<String> assignAttributesToDescription(ClassType classType, Map<String, Double> attributes) {
        List<String> description = new ArrayList<>(classType.getDescription());
        Map<String, Integer> attributeLineIndices = new HashMap<>();
        attributeLineIndices.put("Life_Force", 3);
        attributeLineIndices.put("Strength", 4);
        attributeLineIndices.put("Toughness", 5);
        attributeLineIndices.put("Knockback_Resistance", 6);
        attributeLineIndices.put("Speed", 7);
        attributeLineIndices.put("Attack_Speed", 8);
        attributeLineIndices.put("Precision", 9);
        attributeLineIndices.put("Ferocity", 10);

        for (Map.Entry<String, Double> attribute : attributes.entrySet()) {
            Integer lineIndex = attributeLineIndices.get(attribute.getKey());
            if (lineIndex != null && lineIndex < description.size()) {
                String line = description.get(lineIndex);
                line = line.replaceAll("»&7.*", "»&7 " + attribute.getValue());
                description.set(lineIndex, line);
            }
        }

        classType.setDescription(description);
        return description;
    }

    public static void loadFromYaml(JavaPlugin plugin) {
        for (ClassType classType : values()) {
            String fileName = classType.getFileName();
            File configFile = new File(plugin.getDataFolder() + "/legends", fileName + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            String displayNameKey = ClassPath.LEGEND_DISPLAY_NAME.getPath();
            String descriptionKey = ClassPath.LEGEND_DESCRIPTION.getPath();
            classType.displayName = config.getString(displayNameKey, fileName);
            classType.description = config.getStringList(descriptionKey);
            // Load attributes
            ConfigurationSection attributesSection = config.getConfigurationSection("Legend.Attributes");
            if (attributesSection != null) {
                Map<String, Double> attributes = new HashMap<>();
                for (String key : attributesSection.getKeys(false)) {
                    attributes.put(key, attributesSection.getDouble(key));
                }
                ClassType.assignAttributesToDescription(classType, attributes);
            }
        }
    }
}