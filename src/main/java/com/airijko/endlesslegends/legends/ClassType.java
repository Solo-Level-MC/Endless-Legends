package com.airijko.endlesslegends.legends;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ClassType {
    DEFAULT {
        @Override
        public boolean canUse(ItemStack item) {
            return false;
        }
    },
    WARRIOR {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW || item.getType() == Material.SHIELD;
        }
    },
    ARCHER {
        @Override
        public boolean canUse(ItemStack item) {
            String typeName = item.getType().name();
            return typeName.endsWith("_SWORD") || typeName.endsWith("_AXE") || typeName.equals(Material.TRIDENT.name());
        }
    },
    ASSASSIN {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.SHIELD;
        }
    },
    TANK {
        @Override
        public boolean canUse(ItemStack item) {
            return item.getType() == Material.BOW || item.getType() == Material.CROSSBOW;
        }
    };
    public abstract boolean canUse(ItemStack item);
}