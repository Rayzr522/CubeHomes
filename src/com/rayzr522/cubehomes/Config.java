
package com.rayzr522.cubehomes;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static String	PERM_HOME		= "";
	public static String	PERM_SETHOME	= "";
	public static String	PERM_DELHOME	= "";
	public static String	PERM_CUBEHOMES	= "";

	public static void load(CubeHomes plugin) {

		FileConfiguration config = plugin.getConfig();

		PERM_HOME = config.getString("permissions.home");
		PERM_SETHOME = config.getString("permissions.sethome");
		PERM_DELHOME = config.getString("permissions.delhome");
		PERM_CUBEHOMES = config.getString("permissions.cubehomes");

	}

}
