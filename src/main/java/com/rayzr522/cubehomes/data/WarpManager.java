package com.rayzr522.cubehomes.data;

import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpManager {
    private List<Warp> warps = new ArrayList<>();

    public void load(ConfigurationSection config) {
        warps.clear();

        ConfigurationSection section = config.getConfigurationSection("warps");
        // If there's no section then there's no point trying to load
        if (section == null) {
            config.createSection("warps");
        } else {
            for (String key : section.getKeys(false)) {
                ConfigurationSection warpsection = section.getConfigurationSection(key);
                Warp warp = new Warp(warpsection);
                if (!warp.isValid()) {
                    System.err.println("Invalid warp for key '" + key + "'");
                    continue;
                }
                warps.add(warp);
            }
        }
    }

    public YamlConfiguration save() {
        YamlConfiguration config = new YamlConfiguration();

        ConfigurationSection warpsSection = config.createSection("warps");

        for (Warp warp : warps) {
            warpsSection.set(TextUtils.safeString(warp.getName()), warp.serialize());
        }

        return config;
    }

    public Warp get(String name) {
        for (Warp warp : warps) {
            if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(warp.getName()))) {
                return warp;
            }
        }
        return null;
    }

    public void add(Warp warp) {
        warps.add(warp);
    }

    public void add(Player player, String name) {
        if (!update(player, name)) {
            warps.add(new Warp(name, player.getLocation()));
        }
    }

    public boolean update(Player player, String name) {
        Warp warp = get(name);
        if (warp == null) {
            return false;
        }
        return update(player, warp);
    }

    public boolean update(Player player, Warp warp) {
        warp.setLocation(player.getLocation());
        return true;
    }

    public boolean del(String name) {
        Warp warp = get(name);
        if (warp == null) {
            return false;
        }
        return del(warp);
    }

    public boolean del(Warp warp) {
        return warps.remove(warp);
    }

    public List<Warp> all() {
        return warps;
    }

    public List<Warp> getForPage(Player player, int page) {
        List<Warp> filtered = warps.stream()
                .filter(warp -> warp.hasPermission(player))
                .filter(warp -> !Settings.PER_WORLD_WARPS || warp.getWorld() == player.getWorld())
                .collect(Collectors.toList());

        int offset = page * 28;

        if (offset >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(offset, Math.min(offset + 28, filtered.size()));
    }

    public Warp getForIndex(int i) {
        return warps.get(i);
    }

    public int maxPage() {
        return warps.size() / 28;
    }
}
