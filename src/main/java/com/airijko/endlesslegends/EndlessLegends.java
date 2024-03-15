package com.airijko.endlesslegends;

import com.airijko.endlesscore.EndlessCore;
import com.airijko.endlesscore.managers.AttributeManager;
import com.airijko.endlesslegends.commands.ChooseClassCMD;
import com.airijko.endlesslegends.legends.LegendLoader;
import com.airijko.endlesslegends.listeners.*;
import com.airijko.endlesslegends.managers.DirectoryInitializer;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.providers.LegendAttributeProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class EndlessLegends extends JavaPlugin {
    private PlayerDataManager playerDataManager;
    private LegendAttributeProvider legendAttributeProvider;
    private LegendLoader legendLoader;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new DirectoryInitializer(this).initializeDirectories();

        legendLoader = new LegendLoader(this);
        playerDataManager = new PlayerDataManager(this, legendLoader);
        legendAttributeProvider = new LegendAttributeProvider(playerDataManager);

        EndlessCore endlessCore = EndlessCore.getInstance();
        AttributeManager attributeManager = endlessCore.getAttributeManager();
        attributeManager.registerProvider(legendAttributeProvider);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new ClassTypeListener(playerDataManager), this);

        Objects.requireNonNull(this.getCommand("chooseclass")).setExecutor(new ChooseClassCMD(playerDataManager, legendLoader));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
