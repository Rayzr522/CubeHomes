package com.rayzr522.cubehomes.command.system.exception;

import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.command.CommandSender;

/**
 * @author Rayzr522
 */
public class GenericCommandException extends RuntimeException {
    private final String key;
    private final Object[] translationArgs;

    public GenericCommandException(String key, Object... translationArgs) {
        this.key = key;
        this.translationArgs = translationArgs;
    }

    public void sendTranslatedMessage(CommandSender sender) {
        Language.send(sender, key, translationArgs);
    }
}
