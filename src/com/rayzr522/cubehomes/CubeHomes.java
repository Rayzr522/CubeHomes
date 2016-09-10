
package com.rayzr522.cubehomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.cubehomes.homes.CommandDelHome;
import com.rayzr522.cubehomes.homes.CommandHome;
import com.rayzr522.cubehomes.homes.CommandSetHome;
import com.rayzr522.cubehomes.homes.Homes;
import com.rayzr522.cubehomes.warps.Warps;

public class CubeHomes extends JavaPlugin {

	private Logger			logger;
	private ConfigManager	cm;

	@Override
	public void onEnable() {

		logger = getLogger();

		cm = new ConfigManager(this);

		load();

		getCommand("home").setExecutor(new CommandHome());
		getCommand("sethome").setExecutor(new CommandSetHome());
		getCommand("delhome").setExecutor(new CommandDelHome());
		
		getCommand("warp").setExecutor(new CommandHome());
		getCommand("setwarp").setExecutor(new CommandSetHome());
		getCommand("delwarp").setExecutor(new CommandDelHome());
		
		getCommand("cubehomes").setExecutor(new CommandCubeHomes(this));

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
