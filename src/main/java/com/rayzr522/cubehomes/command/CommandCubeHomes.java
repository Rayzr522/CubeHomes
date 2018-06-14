
package com.rayzr522.cubehomes.command;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandCubeHomes implements CommandExecutor {

    private CubeHomes plugin;

    public CommandCubeHomes(CubeHomes plugin) {

        this.plugin = plugin;

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(Config.PERM_CUBEHOMES)) {

            Language.send(sender, "no-permission");
            return true;

        }

        if (args.length < 1) {

            PluginDescriptionFile pdf = plugin.getDescription();
            Language.send(sender, "version-info", pdf.getName(), pdf.getVersion());
            return true;

        }

        String cmd = args[0].toLowerCase();

        if (cmd.equals("reload")) {

            plugin.reloadConfig();
            plugin.load();
            Language.send(sender, "config-reloaded");

        } else if (cmd.equals("save")) {

            plugin.save();
            Language.send(sender, "config-saved");

        } else {

            Language.send(sender, "usage.cubehomes");

        }

        return true;

    }

}
