package com.rayzr522.cubehomes.data;

import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class HomeManager {
    private List<Home> homes = new ArrayList<>();
    private Map<UUID, Boolean> players = new HashMap<>();

    public void load(ConfigurationSection config) {
        ConfigurationSection homesSection = config.getConfigurationSection("homes");
        if (homesSection == null) {
            homesSection = config.createSection("homes");
        }

        homes = homesSection.getKeys(false).stream()
                .filter(homesSection::isConfigurationSection)
                .map(homesSection::getConfigurationSection)
                .map(Home::new)
                .filter(Home::isValid)
                .collect(Collectors.toList());

        ConfigurationSection playersSection = config.getConfigurationSection("players");
        if (playersSection == null) {
            playersSection = config.createSection("players");
        }

        players = playersSection.getKeys(false).stream()
                .collect(Collectors.toMap(UUID::fromString, homesSection::getBoolean));
    }

    public YamlConfiguration save() {
        YamlConfiguration config = new YamlConfiguration();

        config.createSection(
                "homes",
                homes.stream().collect(Collectors.toMap(
                        home -> TextUtils.safeString(home.getName()),
                        Home::serialize
                ))
        );

        config.createSection(
                "players",
                players.keySet().stream().collect(Collectors.toMap(
                        UUID::toString,
                        players::get
                ))
        );

        return config;
    }

    public List<Home> getHomesForPlayer(Player player) {
        return homes.stream()
                .filter(home -> home.isOwner(player))
                .collect(Collectors.toList());
    }

    public Optional<Home> findHome(String name) {
        return homes.stream()
                .filter(home -> TextUtils.enumFormat(home.getName()).equals(TextUtils.enumFormat(name)))
                .findFirst();
    }

    public void addHome(Player player, String name) {
        addHome(new Home(player, name));
    }

    public void addHome(Home home) {
        homes.add(home);
    }

    public boolean updateHome(Player player, Home home) {
        if (!home.isOwner(player) && !player.hasPermission(Settings.PERM_OTHERS)) {
            return false;
        }

        home.setLocation(player.getLocation());
        return true;
    }

    public boolean removeHome(Home home) {
        return homes.remove(home);
    }

    public List<Home> getHomes() {
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

    public boolean toggleAccessibility(Player player) {
        return setAccessible(player.getUniqueId(), !isAccessible(player.getUniqueId()));
    }
}
