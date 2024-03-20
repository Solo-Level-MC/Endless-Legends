package com.airijko.endlesslegends;

import com.airijko.endlesscore.EndlessCore;
import com.airijko.endlesscore.interfaces.RespawnInterface;
import com.airijko.endlesscore.managers.AttributeManager;

import com.airijko.endlesslegends.commands.EndlessLegendCMD;
import com.airijko.endlesslegends.commands.TestChooseClassCMD;
import com.airijko.endlesslegends.commands.TestClassCMD;
import com.airijko.endlesslegends.gui.LegendClassGUI;
import com.airijko.endlesslegends.legends.ClassType;
import com.airijko.endlesslegends.managers.LegendManager;
import com.airijko.endlesslegends.legends.Rank;
import com.airijko.endlesslegends.listeners.*;
import com.airijko.endlesslegends.managers.DirectoryInitializer;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.mechanics.RebornMechanic;
import com.airijko.endlesslegends.providers.LegendAttributeProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EndlessLegends extends JavaPlugin {
    private PlayerDataManager playerDataManager;
    private LegendAttributeProvider legendAttributeProvider;
    private LegendManager legendManager;
    private LegendClassGUI legendClassGUI;
    private RebornMechanic rebornMechanic;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        new DirectoryInitializer(this).initializeDirectories();
        ClassType.loadFromYaml(this);

        legendManager = new LegendManager(this);
        playerDataManager = new PlayerDataManager(this, legendManager);
        legendAttributeProvider = new LegendAttributeProvider(playerDataManager);
        legendClassGUI = new LegendClassGUI();
        rebornMechanic = new RebornMechanic(playerDataManager);

        Rank.loadWeights(this);
        EndlessCore endlessCore = EndlessCore.getInstance();
        AttributeManager attributeManager = endlessCore.getAttributeManager();
        attributeManager.registerProvider(legendAttributeProvider);

        getServer().getServicesManager().register(RespawnInterface.class, rebornMechanic, this, ServicePriority.Normal);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(rebornMechanic), this);
        getServer().getPluginManager().registerEvents(new ClassTypeListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new EndlessGUIListener(legendClassGUI, legendManager, playerDataManager), this);

        Objects.requireNonNull(this.getCommand("chooseclass")).setExecutor(new TestChooseClassCMD(playerDataManager, legendManager));
        Objects.requireNonNull(this.getCommand("testchooseclass")).setExecutor(new TestClassCMD(playerDataManager, legendManager));
        Objects.requireNonNull(this.getCommand("endlesslegends")).setExecutor(new EndlessLegendCMD(legendClassGUI));
    }

    public FileConfiguration getPluginConfig() {
        return this.getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
