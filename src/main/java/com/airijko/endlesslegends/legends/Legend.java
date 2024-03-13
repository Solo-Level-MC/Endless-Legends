package com.airijko.endlesslegends.legends;

public class Legend {
    public double lifeForce;
    public double strength;
    public double toughness;
    public double knockbackResistance;
    public double speed;
    public double attackSpeed;
    public double precision;
    public double ferocity;
    protected Rank rank;

    public Legend(double lifeForce, double strength, double toughness, double knockbackResistance, double speed, double attackSpeed, double precision, double ferocity, Rank rank) {
        this.lifeForce = lifeForce;
        this.strength = strength;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        this.precision = precision;
        this.ferocity = ferocity;
        this.rank = rank;
        applyRankBonus();
    }

    private void applyRankBonus() {
        double factor = 1 + 0.2 * rank.ordinal();
        lifeForce *= factor;
        strength *= factor;
        toughness *= factor;
        knockbackResistance *= factor;
        speed *= factor;
        attackSpeed *= factor;
        precision *= factor;
        ferocity *= factor;
    }

    public Rank getRank() {
        return rank;
    }
}