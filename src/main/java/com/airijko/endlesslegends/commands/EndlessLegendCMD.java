package com.airijko.endlesslegends.commands;

import com.airijko.endlesslegends.gui.LegendClassGUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EndlessLegendCMD implements CommandExecutor {
    private final Map<String, CommandExecutor> subCommands = new HashMap<>();
    public EndlessLegendCMD(LegendClassGUI gui) {
        subCommands.put("class", new ChooseClassCMD(gui));
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            String subCommand = args[0].toLowerCase();
            CommandExecutor executor = subCommands.get(subCommand);
            if (executor != null) {
                return executor.onCommand(sender, command, label, args);
            }
        }
        sender.sendMessage(Component.text("Usage: /endlesslegends", NamedTextColor.RED));
        return false;
    }
}
