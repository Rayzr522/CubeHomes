package com.rayzr522.cubehomes.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Language {
    private static final Pattern KEY_PATTERN = Pattern.compile("\\[\\[([a-zA-Z-_.]+)]]");
    private static Map<String, String> messages = new HashMap<>();

    public static void load(YamlConfiguration config) {
        Map<String, String> rawMessages = config.getKeys(false).stream()
                .filter(config::isString)
                .collect(Collectors.toMap(key -> key, config::getString));

        messages.clear();
        rawMessages.forEach((key, message) -> {
            Matcher matcher = KEY_PATTERN.matcher(message);
            String output = message;

            while (matcher.find()) {
                String inputKey = matcher.group(1);
                if (messages.containsKey(inputKey)) {
                    output = output.replaceFirst(KEY_PATTERN.pattern(), rawMessages.get(inputKey));
                }
            }

            messages.put(key, output);
        });
    }

    public static void send(CommandSender sender, String key, Object... translationArgs) {
        String output = get(key);

        for (int i = 0; i < translationArgs.length; i++) {
            output = output.replace("{" + i + "}", Objects.toString(translationArgs[i]));
        }

        sender.sendMessage(TextUtils.colorize(output));
    }

    public static String get(String key) {
        return messages.getOrDefault(key, key);
    }

}
