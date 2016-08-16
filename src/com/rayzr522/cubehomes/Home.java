
package com.rayzr522.cubehomes;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Home {

	private UUID		id;
	private String		name;
	private Location	location;
	private boolean		isValid	= false;

	public Home(Player p, String name) {

		this.id = p.getUniqueId();
		this.name = name;
		this.location = p.getLocation();

		isValid = true;

	}

	public Home(UUID id, ConfigurationSection section) {

		this.id = id;

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

	@Override
	public String toString() {
		return "Home [id=" + id + ", name=" + name + ", location=" + location + ", isValid=" + isValid + "]";
	}

	public ConfigurationSection save(ConfigurationSection playerSection) {

		ConfigurationSection homeSection = playerSection.createSection(TextUtils.safeString(name));

		homeSection.set("name", name);
		homeSection.set("pos", ConfigUtils.toString(location));

		return homeSection;

	}

}
