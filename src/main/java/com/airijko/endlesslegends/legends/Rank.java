package com.airijko.endlesslegends.legends;

import org.bukkit.plugin.java.JavaPlugin;

public enum Rank {
    NONE("rank_weights.NONE"),
    E("rank_weights.E"),
    D("rank_weights.D"),
    C("rank_weights.C"),
    B("rank_weights.B"),
    A("rank_weights.A"),
    S("rank_weights.S");

    private int weight;
    private final String path;

    Rank(String path) {
        this.path = path;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static void loadWeights(JavaPlugin plugin) {
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE) {
                int weight = plugin.getConfig().getInt(rank.path);
                rank.setWeight(weight);
            }
        }
    }
}