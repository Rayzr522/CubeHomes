package com.rayzr522.cubehomes.command.system;

import com.rayzr522.cubehomes.command.system.exception.GenericCommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Rayzr522
 */
public class CommandContext {
    private CommandSender sender;
    private List<String> args;

    public CommandContext(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = Arrays.asList(args);
    }

    public CommandSender getSender() {
        return sender;
    }

    public Player getPlayer() {
        if (!(sender instanceof Player)) {
            throw new IllegalStateException("Sender is not a player!");
        }

        return (Player) sender;
    }

    public void failNow(String key, Object... translationArgs) {
        throw new GenericCommandException(key, translationArgs);
    }

    public Supplier<Throwable> fail(String key, Object... translationArgs) {
        return () -> new GenericCommandException(key, translationArgs);
    }

    public boolean hasArgs(int number) {
        return args.size() > 1;
    }

    public boolean hasArgs() {
        return hasArgs(1);
    }

    public List<String> getArgs() {
        return args;
    }

    public String getRemaining() {
        return args.stream().collect(Collectors.joining(" "));
    }

    public <T> T shift(Function<String, T> mapper) {
        return mapper.apply(shift());
    }

    public String shift() {
        return args.remove(0);
    }

    @SuppressWarnings("deprecation")
    public Player shiftPlayer() {
        return shift(Bukkit::getPlayer);
    }
}
