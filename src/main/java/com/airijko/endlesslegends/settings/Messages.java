package com.airijko.endlesslegends.settings;

import com.airijko.endlesscore.utils.FormatMessage;
import net.kyori.adventure.text.Component;

public enum Messages {
    NOT_ALLOWED_TO_SWITCH("&cYou are not allowed to switch classes."),
    ON_COOLDOWN("&ePlease wait %s seconds before switching classes."),
    REAWAKENED("&are-awaking..."),
    PLAYER_DEATH("&cSkill Issue... \n&eChoose a class!");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public Component format(Object... args) {
        return FormatMessage.format(this.message, args);
    }
}