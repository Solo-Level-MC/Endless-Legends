package com.airijko.endlesslegends;

import com.airijko.endlesscore.EndlessCore;
import com.airijko.endlesscore.managers.AttributeManager;
import com.airijko.endlesslegends.commands.ChooseClassCMD;
import com.airijko.endlesslegends.listeners.*;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.providers.LegendAttributeProvider;
import com.airijko.endlesslegends.legends.Legend;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class EndlessLegends extends JavaPlugin {
    private PlayerDataManager playerDataManager;
    private LegendAttributeProvider legendAttributeProvider;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createDataFolder();

        playerDataManager = new PlayerDataManager(this);
        legendAttributeProvider = new LegendAttributeProvider(playerDataManager);

        EndlessCore endlessCore = EndlessCore.getInstance();
        AttributeManager attributeManager = endlessCore.getAttributeManager();
        attributeManager.registerProvider(legendAttributeProvider);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(playerDataManager), this);

        Objects.requireNonNull(this.getCommand("chooseclass")).setExecutor(new ChooseClassCMD(this, playerDataManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createDataFolder() {
        File dataFolder = this.getDataFolder();
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.mkdir()) {
                    getLogger().severe("Could not create data folder: " + dataFolder);
                }
            } catch (SecurityException e) {
                getLogger().severe("Permission denied: " + e.getMessage());
            }
        }
    }
}
