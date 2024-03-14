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
    public Map<String, Double> getModifiers(String attributeName, Player player) {
        Map<String, Double> attributeModifiers = new HashMap<>();
        Legend legend = playerDataManager.getPlayerData(player.getUniqueId());
        if (legend != null) {
            switch (attributeName) {
                case "Life_Force":
                    attributeModifiers.put(attributeName, legend.lifeForce);
                    break;
                case "Strength":
                    attributeModifiers.put(attributeName, legend.strength);
                    break;
                case "Toughness":
                    attributeModifiers.put(attributeName, legend.toughness);
                    break;
                case "Knockback_Resistance":
                    attributeModifiers.put(attributeName, legend.knockbackResistance);
                    break;
                case "Movement_Speed":
                    attributeModifiers.put(attributeName, legend.speed);
                    break;
                case "Attack_Speed":
                    attributeModifiers.put(attributeName, legend.attackSpeed);
                    break;
                case "Precision":
                    attributeModifiers.put(attributeName, legend.precision);
                    break;
                case "Ferocity":
                    attributeModifiers.put(attributeName, legend.ferocity);
                    break;
                default:
                    attributeModifiers.put(attributeName, 0.0);
                    break;
            }
        }
        return attributeModifiers;
    }

    @Override
    public Set<String> getAttributeNames() {
        return new HashSet<>(Arrays.asList("Life_Force", "Strength", "Toughness", "Knockback_Resistance", "Movement_Speed", "Attack_Speed", "Precision", "Ferocity"));
    }
}