
package com.rayzr522.cubehomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.cubehomes.cmd.CommandCubeHomes;
import com.rayzr522.cubehomes.cmd.CommandDelHome;
import com.rayzr522.cubehomes.cmd.CommandHome;
import com.rayzr522.cubehomes.cmd.CommandSetHome;

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
		getCommand("cubehomes").setExecutor(new CommandCubeHomes());

		logger.info(versionText() + " enabled");

	}

	public void load() {

		Msg.load(cm.getOrCreate("messages.yml"));
		Homes.load(cm.getOrCreate("homes.yml"));

	}

	public void save() {

		cm.saveConfig("homes.yml", Homes.save());

	}

	@Override
	public void onDisable() {

		logger.info(versionText() + " disabled");

	}

	public String versionText() {

		return getDescription().getName() + " v" + getDescription().getVersion();

	}

}
