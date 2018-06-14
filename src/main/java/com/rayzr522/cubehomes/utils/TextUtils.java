
package com.rayzr522.cubehomes.utils;

import java.util.Locale;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class TextUtils {

    public static final Pattern UNSAFE_CHARS = Pattern.compile("[^a-z0-9]");

    public static String colorize(String text) {

        return ChatColor.translateAlternateColorCodes('&', text);

    }

    public static String uncolorize(String text) {

        return text.replace(ChatColor.COLOR_CHAR, '&');

    }

    public static String stripColor(String text) {

        return ChatColor.stripColor(text);

    }

    public static String enumFormat(String text) {

        return text.trim().toUpperCase().replace(" ", "_");

    }

    // Yes, this is from Essentials. Stop judging me.
    public static String safeString(String text) {
        return UNSAFE_CHARS.matcher(text.toLowerCase(Locale.ENGLISH)).replaceAll("_");
    }

    public static String capitalize(String line) {

        if (line == null || line.trim().isEmpty()) {
            return "";
        }

        line = line.trim();

        if (line.indexOf(' ') == -1) {
            return _capitalize(line);
        }

        String[] split = line.split(" ");
        String output = _capitalize(split[0]);

        for (int i = 1; i < split.length; i++) {
            output += " " + _capitalize(split[i]);
        }

        return output;

    }

    private static String _capitalize(String word) {

        if (word == null || word.trim().isEmpty()) {
            return "";
        }
        if (word.length() == 1) {
            return word.toUpperCase();
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();

    }

}
