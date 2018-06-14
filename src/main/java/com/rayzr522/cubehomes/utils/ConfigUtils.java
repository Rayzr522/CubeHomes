package com.rayzr522.cubehomes.utils;

import com.rayzr522.cubehomes.CubeHomes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

/**
 * @author Rayzr522
 */
public class ConfigUtils {
    public static String toString(World world) {
        return world.getUID().toString();
    }

    public static String toString(Location loc) {
        return toString(loc.getWorld()) + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public static World parseWorld(String world) {
        return Bukkit.getWorld(UUID.fromString(world));
    }

    public static Location parseLocation(String loc) {
        String[] split = loc.split(":");
        if (split.length != 6) {
            return null;
        }

        if (parseWorld(split[0]) == null) {
            CubeHomes.getInstance().getLogger().severe(String.format("'%s' is an invalid world UUID!", split[0]));
            return null;
        }

        return new Location(
                parseWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5])
        );
    }
}
