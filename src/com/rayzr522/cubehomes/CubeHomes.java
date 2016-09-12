
package com.rayzr522.cubehomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.cubehomes.homes.CommandDelHome;
import com.rayzr522.cubehomes.homes.CommandHome;
import com.rayzr522.cubehomes.homes.CommandSetHome;
import com.rayzr522.cubehomes.homes.Home;
import com.rayzr522.cubehomes.homes.Homes;
import com.rayzr522.cubehomes.menu.Menu;
import com.rayzr522.cubehomes.warps.CommandDelWarp;
import com.rayzr522.cubehomes.warps.CommandSetWarp;
import com.rayzr522.cubehomes.warps.CommandWarp;
import com.rayzr522.cubehomes.warps.CommandWarpIcon;
import com.rayzr522.cubehomes.warps.Warp;
import com.rayzr522.cubehomes.warps.Warps;

public class CubeHomes extends JavaPlugin {

	private Logger			logger;
	private ConfigManager	cm;

	@Override
	public void onEnable() {

		logger = getLogger();

		System.out.println("Forcing class load: " + Warp.class.getCanonicalName());
		System.out.println("Forcing class load: " + Home.class.getCanonicalName());

		cm = new ConfigManager(this);

		load();

		getCommand("home").setExecutor(new CommandHome());
		getCommand("sethome").setExecutor(new CommandSetHome());
		getCommand("delhome").setExecutor(new CommandDelHome());

		getCommand("warp").setExecutor(new CommandWarp());
		getCommand("warpicon").setExecutor(new CommandWarpIcon());
		getCommand("setwarp").setExecutor(new CommandSetWarp());
		getCommand("delwarp").setExecutor(new CommandDelWarp());

		getCommand("cubehomes").setExecutor(new CommandCubeHomes(this));

		getServer().getPluginManager().registerEvents(new Menu(), this);

		logger.info(versionText() + " enabled");

	}

	public void load() {

		Msg.load(cm.getOrCreate("messages.yml"));
		Homes.load(cm.getOrCreate("homes.yml"));
		Warps.load(cm.getOrCreate("warps.yml"));
		Config.load(this);

	}

	public void save() {

		cm.saveConfig("homes.yml", Homes.save());
		cm.saveConfig("warps.yml", Warps.save());

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
