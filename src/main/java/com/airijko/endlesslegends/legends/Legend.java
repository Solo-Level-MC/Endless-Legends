package com.airijko.endlesslegends.legends;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class Legend {
    public String className;
    public String type;
    public double lifeForce;
    public double strength;
    public double toughness;
    public double knockbackResistance;
    public double speed;
    public double attackSpeed;
    public double precision;
    public double ferocity;
    public Rank rank;

    public Legend(File configFile, Rank rank) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        this.className = config.getString("Legend.Class");
        String typeString = config.getString("Legend.Type");
        this.type = String.valueOf(typeString != null ? ClassType.valueOf(typeString.toUpperCase()) : ClassType.DEFAULT);
        this.lifeForce = config.getDouble("Legend.Attributes.Life_Force");
        this.strength = config.getDouble("Legend.Attributes.Strength");
        this.toughness = config.getDouble("Legend.Attributes.Toughness");
        this.knockbackResistance = config.getDouble("Legend.Attributes.Knockback_Resistance");
        this.speed = config.getDouble("Legend.Attributes.Speed");
        this.attackSpeed = config.getDouble("Legend.Attributes.Attack_Speed");
        this.precision = config.getDouble("Legend.Attributes.Precision");
        this.ferocity = config.getDouble("Legend.Attributes.Ferocity");
        this.rank = rank;
        applyRankBonus();
    }

    protected void applyRankBonus() {
        double factor;
        if (rank == Rank.NONE || rank == Rank.E) {
            factor = 1.0;
        } else {
            factor = 1 + 0.2 * (rank.ordinal() - 1);
        }
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
        return this.rank;
    }
}