package com.rayzr522.cubehomes.utils;

import com.rayzr522.cubehomes.CubeHomes;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {
    private final CubeHomes plugin;

    public ConfigManager(CubeHomes plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public YamlConfiguration getConfig(String path) {
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    public YamlConfiguration getOrCreate(String path) {
        File file = getFile(path);

        if (!file.exists()) {
            if (plugin.getResource(path) != null) {
                plugin.saveResource(path, false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to create file at '" + path + "'", e);
                }
            }
        }

        return getConfig(path);
    }

    public File getFile(String path) {
        return new File(plugin.getDataFolder(), path.replace('/', File.separatorChar));
    }

    public void saveConfig(String path, YamlConfiguration config) {
        try {
            File file = getFile(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save config file at '" + path + "'", e);
        }
    }
}
