package com.rayzr522.cubehomes;

import com.rayzr522.cubehomes.command.CommandCubeHomes;
import com.rayzr522.cubehomes.command.homes.CommandDelHome;
import com.rayzr522.cubehomes.command.homes.CommandHome;
import com.rayzr522.cubehomes.command.homes.CommandHomes;
import com.rayzr522.cubehomes.command.homes.CommandSetHome;
import com.rayzr522.cubehomes.command.warps.CommandDelWarp;
import com.rayzr522.cubehomes.command.warps.CommandSetWarp;
import com.rayzr522.cubehomes.command.warps.CommandWarp;
import com.rayzr522.cubehomes.command.warps.CommandWarpIcon;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.menu.MenuListener;
import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.ConfigManager;
import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rayzr522
 */
public class CubeHomes extends JavaPlugin {
    private static CubeHomes instance;

    private ConfigManager configManager;
    private HomeManager homeManager;
    private WarpManager warpManager;

    public static CubeHomes getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Forcing class load: " + Warp.class.getCanonicalName());
        System.out.println("Forcing class load: " + Home.class.getCanonicalName());

        configManager = new ConfigManager(this);
        homeManager = new HomeManager();
        warpManager = new WarpManager();

        load();

        getCommand("home").setExecutor(new CommandHome(this));
        getCommand("homes").setExecutor(new CommandHomes(this));
        getCommand("sethome").setExecutor(new CommandSetHome(this));
        getCommand("delhome").setExecutor(new CommandDelHome(this));

        getCommand("warp").setExecutor(new CommandWarp(this));
        getCommand("warpicon").setExecutor(new CommandWarpIcon(this));
        getCommand("setwarp").setExecutor(new CommandSetWarp(this));
        getCommand("delwarp").setExecutor(new CommandDelWarp(this));

        getCommand("cubehomes").setExecutor(new CommandCubeHomes(this));

        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
    }

    @Override
    public void onDisable() {
        save();
        instance = null;
    }

    public void load() {
        Language.load(configManager.getOrCreate("messages.yml"));
        homeManager.load(configManager.getOrCreate("homes.yml"));
        warpManager.load(configManager.getOrCreate("warps.yml"));
        Settings.load(this);
    }

    public void save() {
        configManager.saveConfig("homes.yml", homeManager.save());
        configManager.saveConfig("warps.yml", warpManager.save());
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }
}
