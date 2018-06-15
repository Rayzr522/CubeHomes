package com.rayzr522.cubehomes.command.system;

import com.rayzr522.cubehomes.command.system.exception.GenericCommandException;
import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Rayzr522
 */
public class InternalCommandHandler implements CommandExecutor {
    private final CommandManager commandManager;
    private final CommandHandler handler;

    public InternalCommandHandler(CommandManager commandManager, CommandHandler handler) {
        this.commandManager = commandManager;
        this.handler = handler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (handler.getTargets().stream().noneMatch(target -> target.isValid(sender))) {
            // TODO: Tell them they can't.
            return true;
        }

        try {
            handler.execute(new CommandContext(sender, args));
        } catch (GenericCommandException e) {
            e.sendTranslatedMessage(sender);
        } catch (Exception e) {
            Language.send(sender, "error");
            e.printStackTrace();
        }

        return true;
    }
}
