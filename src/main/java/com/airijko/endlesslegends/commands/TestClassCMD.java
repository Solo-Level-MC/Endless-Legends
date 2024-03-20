package com.airijko.endlesslegends.commands;

import com.airijko.endlesscore.utils.TitleDisplay;

import com.airijko.endlesslegends.legends.Legend;
import com.airijko.endlesslegends.managers.LegendManager;
import com.airijko.endlesslegends.managers.PlayerDataManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestClassCMD implements CommandExecutor {
    private final PlayerDataManager playerDataManager;
    private final LegendManager legendManager;

    public TestClassCMD(PlayerDataManager playerDataManager, LegendManager legendManager) {
        this.playerDataManager = playerDataManager;
        this.legendManager = legendManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String chosenClassName = args[0];
                Legend chosenClass = legendManager.chooseClass(chosenClassName);
                playerDataManager.setPlayerClassAndRank(player.getUniqueId(), chosenClass, chosenClass.rank.name());
                String title = "<green><b> " + chosenClassName.toUpperCase() + " CLASS.</b></green>";
                String subtitle = "<yellow>" + playerDataManager.getPlayerRank(player.getUniqueId()) + " Rank Hunter</yellow>";
                TitleDisplay.sendTitle(player, title, subtitle);
            } else {
                player.sendMessage("You must specify a class to choose.");
            }
            return true;
        }
        return false;
    }
}