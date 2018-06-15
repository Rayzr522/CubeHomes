package com.rayzr522.cubehomes.data;

import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WarpManager {
    private List<Warp> warps = new ArrayList<>();

    public void load(ConfigurationSection config) {
        ConfigurationSection warpsSection = config.getConfigurationSection("warps");
        if (warpsSection == null) {
            warpsSection = config.createSection("warps");
        }

        warps = warpsSection.getKeys(false).stream()
                .filter(warpsSection::isConfigurationSection)
                .map(warpsSection::getConfigurationSection)
                .map(Warp::new)
                .filter(Warp::isValid)
                .collect(Collectors.toList());
    }

    public YamlConfiguration save() {
        YamlConfiguration config = new YamlConfiguration();

        config.createSection(
                "warps",
                warps.stream()
                        .collect(Collectors.toMap(warp -> TextUtils.safeString(warp.getName()), Warp::serialize))
        );

        return config;
    }

    public Optional<Warp> findWarp(String name) {
        return warps.stream()
                .filter(warp -> TextUtils.enumFormat(warp.getName()).equals(TextUtils.enumFormat(name)))
                .findFirst();
    }

    public void addWarp(Player player, String name) {
        if (!updateWarp(player, name)) {
            addWarp(new Warp(name, player.getLocation()));
        }
    }

    public void addWarp(Warp warp) {
        warps.add(warp);
    }

    public boolean updateWarp(Player player, String name) {
        return findWarp(name).map(warp -> updateWarp(player, warp)).orElse(false);
    }

    public boolean updateWarp(Player player, Warp warp) {
        warp.setLocation(player.getLocation());
        return true;
    }

    public boolean removeWarp(Warp warp) {
        return warps.remove(warp);
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

    public int maxPage() {
        return warps.size() / 28;
    }
}
