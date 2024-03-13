package com.airijko.endlesslegends.commands;

import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.legends.LegendFactory;
import com.airijko.endlesslegends.legends.Rank;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChooseClassCMD implements CommandExecutor {
    private final JavaPlugin plugin;
    private final PlayerDataManager playerDataManager;
    private final LegendFactory legendFactory;

    public ChooseClassCMD(JavaPlugin plugin, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.legendFactory = new LegendFactory();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            if (args.length > 1) {
                String chosenClassName = args[0];
                Rank rank = Rank.valueOf(args[1].toUpperCase());
                Legend chosenClass = legendFactory.getLegend(chosenClassName, rank);
                playerDataManager.setPlayerClassAndRank(playerUUID, chosenClass, rank.name());
                player.sendMessage("You have chosen the " + chosenClassName + " class with " + rank.name() + " rank.");
            } else {
                player.sendMessage("You must specify a class and rank to choose.");
            }
        }
        return true;
    }
}