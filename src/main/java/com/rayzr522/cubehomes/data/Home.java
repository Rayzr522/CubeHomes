package com.rayzr522.cubehomes.data;

import com.rayzr522.cubehomes.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Home implements ConfigurationSerializable {

    private UUID id;
    private String name;
    private Location location;
    private boolean isValid = false;

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
        return HomeManager.isAccessible(id);
    }

    @Override
    public String toString() {
        return "Home [id=" + id + ", name=" + name + ", location=" + location + ", isValid=" + isValid + "]";
    }

    public boolean isOwner(Player p) {
        return p.getUniqueId() == id;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("owner", id.toString());
        map.put("name", name);
        map.put("pos", ConfigUtils.toString(location));

        return map;
    }

}
