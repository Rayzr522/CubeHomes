
package com.rayzr522.cubehomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Homes {

	private static HashMap<UUID, List<Home>> homes = new HashMap<>();

	public static void load(YamlConfiguration config) {

		homes.clear();

		for (String key : config.getKeys(false)) {

			UUID id = UUID.fromString(key);

			ConfigurationSection playerSection = config.getConfigurationSection(key);

			List<Home> playerHomes = new ArrayList<Home>();

			for (String key2 : playerSection.getKeys(false)) {

				Home home = new Home(id, playerSection.getConfigurationSection(key2));
				if (!home.isValid()) {
					System.err.println("Failed to load home: " + home.toString());
					continue;
				}

				playerHomes.add(home);

			}

			homes.put(id, playerHomes);

		}

	}

	public static YamlConfiguration save() {

		YamlConfiguration config = new YamlConfiguration();

		for (Entry<UUID, List<Home>> entry : homes.entrySet()) {

			ConfigurationSection playerSection = config.createSection(entry.getKey().toString());

			for (Home home : entry.getValue()) {

				playerSection.set(home.getName(), home.save(playerSection));

			}

		}

		return config;

	}

	public static List<Home> get(Player p) {
		return get(p.getUniqueId());
	}

	public static List<Home> get(UUID id) {

		if (!homes.containsKey(id)) {
			init(id);
		}
		return homes.get(id);

	}

	public static void init(UUID id) {
		homes.put(id, new ArrayList<Home>());
	}

	public static Home get(Player p, String name) {
		return get(p.getUniqueId(), name);
	}

	public static Home get(UUID id, String name) {
		List<Home> homeList = get(id);
		for (Home home : homeList) {
			if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(home.getName()))) { return home; }
		}
		return null;
	}

	public static void set(Player p, String name) {

		List<Home> homeList = get(p);
		Home home = get(p, name);

		if (home == null) {
			home = new Home(p, name);
		} else {
			homeList.remove(home);
			home.setLocation(p.getLocation());
		}

		homeList.add(home);
		homes.put(p.getUniqueId(), homeList);

	}

	public static boolean del(UUID id, String name) {

		Home home = get(id, name);
		if (home == null) { return false; }
		return del(id, home);

	}

	public static boolean del(UUID id, Home home) {

		List<Home> homeList = get(id);

		if (homeList.remove(home)) {
			homes.put(id, homeList);
			return true;
		}

		return false;

	}

}
