package com.airijko.endlesslegends.legends;

public enum Rank {
    E, D, C, B, A, S;

    public double getValue() {
        switch (this) {
            case E:
                return 1.0;
            case D:
                return 2.0;
            case C:
                return 3.0;
            case B:
                return 4.0;
            case A:
                return 5.0;
            case S:
                return 6.0;
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}