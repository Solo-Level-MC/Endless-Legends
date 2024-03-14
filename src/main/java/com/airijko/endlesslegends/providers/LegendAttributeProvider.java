package com.airijko.endlesslegends.providers;

import com.airijko.endlesscore.interfaces.AttributeModifierInterface;
import com.airijko.endlesslegends.managers.PlayerDataManager;
import com.airijko.endlesslegends.legends.Legend;
import org.bukkit.entity.Player;

import java.util.*;

public class LegendAttributeProvider implements AttributeModifierInterface {
    private final PlayerDataManager playerDataManager;

    public LegendAttributeProvider(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @Override
    public double getModifier(String attributeName, Player player) {
        Legend legend = playerDataManager.getPlayerData(player.getUniqueId());
        if (legend == null) {
            return 0;
        }
        switch (attributeName) {
            case "Life_Force":
                return legend.lifeForce;
            case "Strength":
                return legend.strength;
            case "Toughness":
                return legend.toughness;
            case "Knockback_Resistance":
                return legend.knockbackResistance;
            case "Movement_Speed":
                return legend.speed;
            case "Attack_Speed":
                return legend.attackSpeed;
            case "Precision":
                return legend.precision;
            case "Ferocity":
                return legend.ferocity;
            default:
                return 0;
        }
    }
    @Override
    public Map<String, Double> getModifiers(String attributeName, Player player) {
        Map<String, Double> attributeModifiers = new HashMap<>();
        attributeModifiers.put(attributeName, getModifier(attributeName, player));
        return attributeModifiers;
    }

    @Override
    public Set<String> getAttributeNames() {
        return new HashSet<>(Arrays.asList("Life_Force", "Strength", "Toughness", "Knockback_Resistance", "Movement_Speed", "Attack_Speed", "Precision", "Ferocity"));
    }
}