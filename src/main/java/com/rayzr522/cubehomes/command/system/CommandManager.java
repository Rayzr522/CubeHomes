package com.rayzr522.cubehomes.command.system;

import org.bukkit.command.PluginCommand;

/**
 * @author Rayzr522
 */
public class CommandManager {
    public void registerCommand(PluginCommand command, CommandHandler handler) {
        command.setExecutor(new InternalCommandHandler(this, handler));
    }
}
