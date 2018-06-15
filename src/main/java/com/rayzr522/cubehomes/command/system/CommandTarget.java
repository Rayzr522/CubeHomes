package com.rayzr522.cubehomes.command.system;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

/**
 * @author Rayzr522
 */
public enum CommandTarget {
    PLAYER(sender -> sender instanceof Player),
    CONSOLE(sender -> sender instanceof ConsoleCommandSender);

    private Predicate<CommandSender> predicate;

    CommandTarget(Predicate<CommandSender> predicate) {
        this.predicate = predicate;
    }

    public boolean isValid(CommandSender sender) {
        return predicate.test(sender);
    }
}
