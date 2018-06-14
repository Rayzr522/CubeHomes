package com.rayzr522.cubehomes.utils;

import com.rayzr522.cubehomes.CubeHomes;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Rayzr522
 */
public class Settings {
    public static String PERM_MORE = "";
    public static String PERM_OTHERS = "";

    public static String PERM_HOME = "";
    public static String PERM_LIST_HOMES = "";
    public static String PERM_SETHOME = "";
    public static String PERM_DELHOME = "";

    public static String PERM_WARP = "";
    public static String PERM_SETWARP = "";
    public static String PERM_WARPICON = "";
    public static String PERM_DELWARP = "";

    public static String PERM_CUBEHOMES = "";

    public static boolean PER_WORLD_WARPS = true;
    public static String GUI_NAME = "";

    // Load all values from the config file
    public static void load(CubeHomes plugin) {

        FileConfiguration config = plugin.getConfig();

        // All the home-related permissions
        PERM_HOME = config.getString("permissions.home");
        PERM_LIST_HOMES = config.getString("permissions.homes");
        PERM_SETHOME = config.getString("permissions.sethome");
        PERM_DELHOME = config.getString("permissions.delhome");
        PERM_MORE = config.getString("permissions.more");

        // All the warp-related permissions
        PERM_WARP = config.getString("permissions.warp");
        PERM_SETWARP = config.getString("permissions.setwarp");
        PERM_WARPICON = config.getString("permissions.warpicon");
        PERM_DELWARP = config.getString("permissions.delwarp");

        // Misc. permissions
        PERM_CUBEHOMES = config.getString("permissions.cubehomes");
        PERM_OTHERS = config.getString("permissions.others");

        PER_WORLD_WARPS = config.getBoolean("per-world-warps");
        GUI_NAME = TextUtils.colorize(config.getString("gui-name"));

    }

}
