
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
		if (!homes.containsKey(p.getUniqueId())) {
			init(p);
		}
		return homes.get(p.getUniqueId());
	}

	public static void init(Player p) {
		homes.put(p.getUniqueId(), new ArrayList<Home>());
	}

	public static Home get(Player p, String name) {
		List<Home> homeList = get(p);
		for (Home home : homeList) {
			if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(home.getName()))) { return home; }
		}
		return null;
	}

}
