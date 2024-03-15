package com.airijko.endlesslegends.managers;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DirectoryInitializer {
    private final JavaPlugin plugin;

    public DirectoryInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeDirectories() {
        createDirectory("playerdata");
        createDirectory("legends");
        loadDefaultClasses();
    }

    private void createDirectory(String directoryName) {
        File directory = new File(plugin.getDataFolder(), directoryName);
        if (!directory.exists()) {
            plugin.saveResource(directoryName + "/.keep", false);
        }
    }

    private void loadDefaultClasses() {
        saveClassIfNotExists("legends/Default.yml");
        saveClassIfNotExists("legends/SampleClass.yml");
        saveClassIfNotExists("legends/Warrior.yml");
        saveClassIfNotExists("legends/Archer.yml");
        saveClassIfNotExists("legends/Assassin.yml");
        saveClassIfNotExists("legends/Paladin.yml");
    }


    private void saveClassIfNotExists(String resourcePath) {
        File resourceFile = new File(plugin.getDataFolder(), resourcePath);
        if (!resourceFile.exists()) {
            plugin.saveResource(resourcePath, false);
        }
    }
}