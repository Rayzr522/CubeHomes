
package com.rayzr522.cubehomes;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.cubehomes.cmd.CommandCubeHomes;
import com.rayzr522.cubehomes.cmd.CommandDelHome;
import com.rayzr522.cubehomes.cmd.CommandHome;
import com.rayzr522.cubehomes.cmd.CommandSetHome;

public class CubeHomes extends JavaPlugin {

	public static PlayerNames	pn;

	private Logger				logger;
	private ConfigManager		cm;

	@Override
	public void onEnable() {

		logger = getLogger();

		pn = new PlayerNames(this);
		cm = new ConfigManager(this);

		load();

		getCommand("home").setExecutor(new CommandHome());
		getCommand("sethome").setExecutor(new CommandSetHome());
		getCommand("delhome").setExecutor(new CommandDelHome());
		getCommand("cubehomes").setExecutor(new CommandCubeHomes(this));

		logger.info(versionText() + " enabled");

	}

	public void load() {

		Msg.load(cm.getOrCreate("messages.yml"));
		Homes.load(cm.getOrCreate("homes.yml"));
		Config.load(this);

	}

	public void save() {

		cm.saveConfig("homes.yml", Homes.save());
		pn.save();

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
