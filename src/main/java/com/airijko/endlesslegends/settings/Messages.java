package com.airijko.endlesslegends.settings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public enum Messages {
    NOT_ALLOWED_TO_SWITCH("&cYou are not allowed to switch classes."),
    ON_COOLDOWN("&ePlease wait %s seconds before switching classes.");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public Component getMessage() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public Component format(Object... args) {
        String formattedMessage = String.format(message, args);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(formattedMessage);
    }
}