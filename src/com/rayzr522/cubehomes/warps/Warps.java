
package com.rayzr522.cubehomes.warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.TextUtils;

public class Warps {

    private static List<Warp> warps = new ArrayList<Warp>();

    public static void load(YamlConfiguration config) {

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

    public static YamlConfiguration save() {

        YamlConfiguration config = new YamlConfiguration();

        ConfigurationSection warpsSection = config.createSection("warps");

        for (Warp warp : warps) {

            warpsSection.set(TextUtils.safeString(warp.getName()), warp.serialize());

        }

        return config;

    }

    public static Warp get(String name) {
        for (Warp warp : warps) {
            if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(warp.getName()))) {
                return warp;
            }
        }
        return null;
    }

    public static void add(Warp warp) {
        warps.add(warp);
    }

    public static void add(Player player, String name) {
        if (!update(player, name)) {
            warps.add(new Warp(name, player.getLocation()));
        }
    }

    public static boolean update(Player player, String name) {
        Warp warp = get(name);
        if (warp == null) {
            return false;
        }
        return update(player, warp);
    }

    public static boolean update(Player player, Warp warp) {
        warp.setLocation(player.getLocation());
        return true;
    }

    public static boolean del(String name) {

        Warp warp = get(name);
        if (warp == null) {
            return false;
        }
        return del(warp);

    }

    public static boolean del(Warp warp) {

        if (warps.remove(warp)) {
            return true;
        }
        return false;

    }

    public static List<Warp> all() {
        return warps;
    }

    public static List<Warp> getForPage(int page) {
        int offset = page * 28;
        List<Warp> _warps = new ArrayList<Warp>();
        if (offset >= warps.size()) {
            return _warps;
        }

        for (int i = 0; i < 28; i++) {

            if (offset + i >= warps.size()) {
                break;
            }

            _warps.add(warps.get(i));

        }

        return _warps;

    }

    public static Warp getForIndex(int i) {
        return warps.get(i);
    }

    public static int maxPage() {
        return warps.size() / 28;
    }

}
