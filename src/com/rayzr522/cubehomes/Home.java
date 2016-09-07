
package com.rayzr522.cubehomes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class Home implements ConfigurationSerializable {

	private UUID		id;
	private String		name;
	private Location	location;
	private boolean		isValid		= false;
	private boolean		accessible	= true;

	public Home(Player p, String name) {

		this.id = p.getUniqueId();
		this.name = name;
		this.location = p.getLocation();

		isValid = true;

	}

	public Home(ConfigurationSection section) {

		id = UUID.fromString(section.getString("owner"));
		name = section.getString("name");
		location = ConfigUtils.location(section.getString("pos"));
		accessible = section.getBoolean("accessible");

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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public boolean toggleAccessibility() {
		return accessible = !accessible;
	}

	@Override
	public String toString() {
		return "Home [id=" + id + ", name=" + name + ", location=" + location + ", isValid=" + isValid + ", accessible=" + accessible + "]";
	}

	public boolean isOwner(Player p) {
		return p.getUniqueId() == id;
	}

	@Override
	public Map<String, Object> serialize() {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("owner", id);
		map.put("name", name);
		map.put("pos", ConfigUtils.toString(location));
		map.put("accessible", accessible);

		return map;

	}

}
