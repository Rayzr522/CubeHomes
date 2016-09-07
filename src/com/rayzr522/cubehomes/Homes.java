
package com.rayzr522.cubehomes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Homes {

	private static List<Home> homes = new ArrayList<Home>();

	public static void load(YamlConfiguration config) {

		homes.clear();

		for (String key : config.getKeys(false)) {

			ConfigurationSection homeSection = config.getConfigurationSection(key);

			homes.add(new Home(homeSection));

		}

	}

	public static YamlConfiguration save() {

		YamlConfiguration config = new YamlConfiguration();

		for (Home home : homes) {

			config.set(TextUtils.safeString(home.getName()), home);

		}

		return config;

	}

	public static List<Home> get(Player p) {
		List<Home> homeList = new ArrayList<Home>();
		for (Home home : homes) {
			if (home.isOwner(p)) {
				homeList.add(home);
			}
		}
		return homeList;
	}

	public static Home get(String name) {
		for (Home home : homes) {
			if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(home.getName()))) { return home; }
		}
		return null;
	}

	public static void add(Home home) {
		homes.add(home);
	}

	public static void add(Player player, String name) {
		homes.add(new Home(player, name));
	}

	public static boolean update(Player player, String name) {
		Home home = get(name);
		if (home == null) { return false; }
		return update(player, name);
	}

	public static boolean update(Player player, Home home) {
		if (!home.isOwner(player)) { return false; }
		home.setLocation(player.getLocation());
		return true;
	}

	public static boolean del(String name) {

		Home home = get(name);
		if (home == null) { return false; }
		return del(home);

	}

	public static boolean del(Home home) {

		if (homes.remove(home)) { return true; }
		return false;

	}

	public static List<Home> all() {
		return homes;
	}

}
