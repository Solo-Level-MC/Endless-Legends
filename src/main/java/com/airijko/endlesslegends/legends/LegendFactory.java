package com.airijko.endlesslegends.legends;

public class LegendFactory {
    public Legend getLegend(String className, Rank rank) {
        switch (className.toLowerCase()) {
            case "draftclass":
                return new DraftClass(rank);
            default:
                return null;
        }
    }
}