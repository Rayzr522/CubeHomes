package com.rayzr522.cubehomes.data;

import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;

public class HomeManager {
    private List<Home> homes = new ArrayList<>();
    private Map<UUID, Boolean> players = new HashMap<>();

    public YamlConfiguration save() {
        YamlConfiguration config = new YamlConfiguration();

        ConfigurationSection homesSection = config.createSection("homes");
        ConfigurationSection playersSection = config.createSection("players");

        for (Home home : homes) {
            homesSection.set(TextUtils.safeString(home.getName()), home.serialize());
        }

        for (Entry<UUID, Boolean> entry : players.entrySet()) {
            playersSection.set(entry.getKey().toString(), entry.getValue());
        }

        return config;
    }

    public List<Home> get(Player p) {
        List<Home> homeList = new ArrayList<Home>();
        for (Home home : homes) {
            if (home.isOwner(p)) {
                homeList.add(home);
            }
        }
        return homeList;
    }

    public Home get(String name) {
        for (Home home : homes) {
            if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(home.getName()))) {
                return home;
            }
        }
        return null;
    }

    public void add(Home home) {
        homes.add(home);
    }

    public void add(Player player, String name) {
        homes.add(new Home(player, name));
    }

    public boolean update(Player player, String name) {
        Home home = get(name);
        if (home == null) {
            return false;
        }
        return update(player, home);
    }

    public boolean update(Player player, Home home) {
        if (!home.isOwner(player) && !player.hasPermission(Settings.PERM_OTHERS)) {
            return false;
        }
        home.setLocation(player.getLocation());
        return true;
    }

    public boolean del(String name) {
        Home home = get(name);
        if (home == null) {
            return false;
        }
        return del(home);
    }

    public boolean del(Home home) {
        if (homes.remove(home)) {
            return true;
        }
        return false;
    }

    public List<Home> all() {
        return homes;
    }

    public boolean isAccessible(UUID id) {
        if (!players.containsKey(id)) {
            players.put(id, true);
        }
        return players.get(id);
    }

    public boolean setAccessible(UUID id, boolean accessible) {
        return players.put(id, accessible);
    }

    public boolean toggleAccessibility(Player p) {
        return setAccessible(p.getUniqueId(), !isAccessible(p.getUniqueId()));
    }

    public void load(ConfigurationSection config) {
        homes.clear();

        ConfigurationSection section = config.getConfigurationSection("homes");

        // If there's no section then there's no point trying to load
        if (section == null) {
            config.createSection("homes");
        } else {
            for (String key : section.getKeys(false)) {
                ConfigurationSection homeSection = section.getConfigurationSection(key);
                Home home = new Home(homeSection);
                if (!home.isValid()) {
                    System.err.println("Invalid home for key '" + key + "'");
                    continue;
                }
                homes.add(new Home(homeSection));
            }
        }

        section = config.getConfigurationSection("players");

        // If there's no section then there's no point trying to load
        if (section == null) {
            config.createSection("players");
        } else {
            for (String key : section.getKeys(false)) {
                players.put(UUID.fromString(key), section.getBoolean(key));
            }
        }
    }
}
