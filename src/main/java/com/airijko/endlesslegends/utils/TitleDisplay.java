package com.airijko.endlesslegends.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

public class TitleDisplay {

    public static void sendTitle(Player player, String title, String subtitle) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component titleComponent = miniMessage.deserialize(title);
        Component subtitleComponent = miniMessage.deserialize(subtitle);
        Title titleObj = Title.title(titleComponent, subtitleComponent);
        ((Audience) player).showTitle(titleObj);
    }
}