package com.airijko.endlesslegends.commands;

import com.airijko.endlesslegends.gui.LegendClassGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChooseClassCMD  implements CommandExecutor {
    private final LegendClassGUI gui;
    public ChooseClassCMD(LegendClassGUI gui) {
        this.gui = gui;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            gui.classSelectionGUI(player);
            return true;
        }
        return false;
    }
}
