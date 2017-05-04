package com.rayzr522.cubehomes.warps;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Warp implements ConfigurationSerializable {

    private String name;
    private Location location;
    private boolean isValid = false;
    private Material iconType = Material.EMERALD;
    private int iconData = 0;

    public Warp(String name, Location location) {

        this.name = name;
        this.location = location;

        isValid = true;

    }

    public Warp(ConfigurationSection section) {

        name = section.getString("name");
        location = ConfigUtils.location(section.getString("pos"));
        iconType = Material.getMaterial(section.getString("item-type"));
        if (iconType == null) {
            iconType = Material.EMERALD;
        }
        iconData = section.getInt("item-data");

        if (name != null && location != null) {
            isValid = true;
        }

    }

    public static Class<?> forceClassLoad() {
        return Warp.class;
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

    public void setIcon(Material iconType, int iconData) {
        this.iconType = iconType;
        this.iconData = iconData;
    }

    public Material getIconType() {
        return iconType;
    }

    public int getIconData() {
        return iconData;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", name);
        map.put("pos", ConfigUtils.toString(location));
        map.put("item-type", iconType.toString());
        map.put("item-data", iconData);

        return map;

    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(String.format("%s.%s", Config.PERM_WARP, name));
    }
}
