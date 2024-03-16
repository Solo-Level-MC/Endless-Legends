package com.airijko.endlesslegends.legends;

public enum ClassPath {
    LEGEND_DISPLAY_NAME("Legend.DisplayName"),
    LEGEND_DESCRIPTION("Legend.Description");

    private final String path;

    ClassPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}