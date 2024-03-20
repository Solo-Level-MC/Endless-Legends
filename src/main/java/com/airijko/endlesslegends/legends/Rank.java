package com.airijko.endlesslegends.legends;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static Rank getRandomRank() {
        List<Rank> weightedRanks = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE) {
                for (int i = 0; i < rank.getWeight(); i++) {
                    weightedRanks.add(rank);
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(weightedRanks.size());
        return weightedRanks.get(randomIndex);
    }

    public static Rank getRandomRankLowerOrEqual(Rank upperBound) {
        List<Rank> weightedRanks = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE && rank.ordinal() <= upperBound.ordinal()) {
                for (int i = 0; i < rank.getWeight(); i++) {
                    weightedRanks.add(rank);
                }
            }
        }

        if (weightedRanks.isEmpty()) {
            return Rank.NONE;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(weightedRanks.size());
        return weightedRanks.get(randomIndex);
    }

    public static Rank getRandomRankHigher(Rank lowerBound) {
        if (lowerBound == Rank.S) {
            return Rank.S;
        }

        List<Rank> weightedRanks = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE && rank.ordinal() > lowerBound.ordinal()) {
                for (int i = 0; i < rank.getWeight(); i++) {
                    weightedRanks.add(rank);
                }
            }
        }

        if (weightedRanks.isEmpty()) {
            return Rank.NONE;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(weightedRanks.size());
        return weightedRanks.get(randomIndex);
    }
}