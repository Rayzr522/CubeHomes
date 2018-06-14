package com.rayzr522.cubehomes.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtils {
    private static final Pattern UNSAFE_CHARS = Pattern.compile("[^a-z0-9]");

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
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
            return capitalizeWord(line);
        }

        return Arrays.stream(line.split(" "))
                .map(TextUtils::capitalizeWord)
                .collect(Collectors.joining(" "));
    }

    private static String capitalizeWord(String word) {
        if (word.isEmpty()) {
            return word;
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
