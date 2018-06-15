package com.rayzr522.cubehomes.command.system;

import com.rayzr522.cubehomes.CubeHomes;

import java.util.List;

/**
 * @author Rayzr522
 */
public interface CommandHandler {
    CubeHomes getPlugin();

    String getPermission();

    List<CommandTarget> getTargets();

    CommandResult execute(CommandContext context);
}
