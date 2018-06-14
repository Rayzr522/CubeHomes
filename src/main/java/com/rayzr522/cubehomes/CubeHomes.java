package com.rayzr522.cubehomes;

import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.command.homes.*;
import com.rayzr522.cubehomes.menu.Menu;
import com.rayzr522.cubehomes.command.warps.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CubeHomes extends JavaPlugin {

    private Logger logger;
    private ConfigManager cm;

    @Override
    public void onEnable() {

        logger = getLogger();

        System.out.println("Forcing class load: " + Warp.class.getCanonicalName());
        System.out.println("Forcing class load: " + Home.class.getCanonicalName());

        cm = new ConfigManager(this);

        load();

        getCommand("home").setExecutor(new CommandHome());
        getCommand("homes").setExecutor(new CommandHomes());
        getCommand("sethome").setExecutor(new CommandSetHome());
        getCommand("delhome").setExecutor(new CommandDelHome());

        getCommand("warp").setExecutor(new CommandWarp());
        getCommand("warpicon").setExecutor(new CommandWarpIcon());
        getCommand("setwarp").setExecutor(new CommandSetWarp());
        getCommand("delwarp").setExecutor(new CommandDelWarp());

        getCommand("cubehomes").setExecutor(new CommandCubeHomes(this));

        getServer().getPluginManager().registerEvents(new Menu(), this);

    }

    public void load() {

        Msg.load(cm.getOrCreate("messages.yml"));
        HomeManager.load(cm.getOrCreate("homes.yml"));
        WarpManager.load(cm.getOrCreate("warps.yml"));
        Config.load(this);

    }

    public void save() {

        cm.saveConfig("homes.yml", HomeManager.save());
        cm.saveConfig("warps.yml", WarpManager.save());

    }

    @Override
    public void onDisable() {

        save();
        logger.info(versionText() + " disabled");

    }

    public String versionText() {

        return getDescription().getName() + " v" + getDescription().getVersion();

    }

}
