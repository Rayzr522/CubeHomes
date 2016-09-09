
package com.rayzr522.cubehomes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class Warp implements ConfigurationSerializable {

	private String		name;
	private Location	location;
	private boolean		isValid	= false;

	public Warp(String name, Location location) {

		this.name = name;
		this.location = location;

		isValid = true;

	}

	public Warp(ConfigurationSection section) {

		name = section.getString("name");
		location = ConfigUtils.location(section.getString("pos"));

		if (location != null) {
			isValid = true;
		}

	}

	public boolean isValid() {
		return isValid;
	}

	public void tp(Player player) {
		player.teleport(location);
	}

	public World getWorld() {
		return location.getWorld();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Map<String, Object> serialize() {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("pos", ConfigUtils.toString(location));

		return map;

	}

}
