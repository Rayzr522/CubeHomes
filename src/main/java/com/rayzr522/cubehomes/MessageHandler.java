package com.rayzr522.cubehomes;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Hashtable;

public class MessageHandler {
    private Hashtable<String, String> messages = new Hashtable<>();

    private static String getBaseKey(String key) {
        return key.substring(0, key.lastIndexOf('.'));
    }

    public void load(ConfigurationSection config) {
        messages.clear();
        config.getKeys(true).forEach(key -> messages.put(key, config.get(key).toString()));
    }

    public String trRaw(String key, Object... objects) {
        return ChatColor.translateAlternateColorCodes('&', String.format(messages.getOrDefault(key, key), objects));
    }

    public String tr(String key, Object... objects) {
        String prefix = messages.getOrDefault(getBaseKey(key) + ".prefix", messages.getOrDefault("prefix", ""));

        return ChatColor.translateAlternateColorCodes('&', prefix + trRaw(key, objects));
    }

}
