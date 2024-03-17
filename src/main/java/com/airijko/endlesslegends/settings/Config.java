package com.airijko.endlesslegends.settings;
public enum Config {
    ALLOW_CLASS_CHANGE("allow_class_change", OptionType.BOOLEAN),
    CLASS_CHANGE_COOLDOWN("class_change_cooldown", OptionType.INT),
    RESET_CLASS_ON_DEATH("reset_class_on_death", OptionType.BOOLEAN),
    REBORN_COOLDOWN("reborn_cooldown", OptionType.INT),
    AWAKEN_CHANCE("awaken_chance", OptionType.DOUBLE);

    private final String path;
    private final OptionType type;

    Config(String path, OptionType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public OptionType getType() {
        return type;
    }
}
