
package com.rayzr522.cubehomes.warps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.TextUtils;

public class Warps {

	private static List<Warp>				warps	= new ArrayList<Warp>();
	private static HashMap<UUID, Boolean>	players	= new HashMap<UUID, Boolean>();

	public static void load(YamlConfiguration config) {

		warps.clear();

		ConfigurationSection section = config.getConfigurationSection("warps");
		// If there's no section then there's no point trying to load
		if (section == null) {
			config.createSection("warps");
		} else {
			for (String key : section.getKeys(false)) {
				ConfigurationSection warpsection = section.getConfigurationSection(key);
				warps.add(new Warp(warpsection));
			}
		}

		section = config.getConfigurationSection("players");
		// If there's no section then there's no point trying to load
		if (section == null) {
			config.createSection("players");
		} else {
			for (String key : section.getKeys(false)) {
				players.put(UUID.fromString(key), section.getBoolean(key));
			}
		}

	}

	public static YamlConfiguration save() {

		YamlConfiguration config = new YamlConfiguration();

		for (Warp warp : warps) {

			config.set(TextUtils.safeString(warp.getName()), warp);

		}

		return config;

	}

	public static Warp get(String name) {
		for (Warp warp : warps) {
			if (TextUtils.enumFormat(name).equals(TextUtils.enumFormat(warp.getName()))) { return warp; }
		}
		return null;
	}

	public static void add(Warp warp) {
		warps.add(warp);
	}

	public static void add(Player player, String name) {
		if (!update(player, name)) {
			warps.add(new Warp(name, player.getLocation()));
		}
	}

	public static boolean update(Player player, String name) {
		Warp warp = get(name);
		if (warp == null) { return false; }
		return update(player, name);
	}

	public static boolean update(Player player, Warp warp) {
		warp.setLocation(player.getLocation());
		return true;
	}

	public static boolean del(String name) {

		Warp warp = get(name);
		if (warp == null) { return false; }
		return del(warp);

	}

	public static boolean del(Warp warp) {

		if (warps.remove(warp)) { return true; }
		return false;

	}

	public static List<Warp> all() {
		return warps;
	}

	public static boolean isAccessible(UUID id) {
		if (!players.containsKey(id)) {
			players.put(id, true);
		}
		return players.get(id);
	}

	public static boolean setAccessible(UUID id, boolean accessible) {
		return players.put(id, accessible);
	}

	public static boolean toggleAccessibility(Player p) {
		return setAccessible(p.getUniqueId(), !isAccessible(p.getUniqueId()));
	}

}
