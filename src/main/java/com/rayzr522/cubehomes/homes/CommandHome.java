
package com.rayzr522.cubehomes.homes;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.ArrayUtils;
import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Msg;

public class CommandHome implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Config.PERM_HOME)) {

            Msg.send(player, "no-permission");
            return true;

        }

        if (args.length < 1) {
            Msg.send(player, "usage.home");
            return true;
        }

        if (args[0].equalsIgnoreCase("toggle")) {

            boolean accessible = Homes.toggleAccessibility(player);

            Msg.send(sender, "home-access", accessible ? "enabled" : "disabled");
            return true;

        }

        Home home = Homes.get(args[0]);

        if (home == null) {
            Msg.send(sender, "unknown-home", args.length > 1 ? args[1] : args[0]);
            return true;
        }

        if (!home.isOwner(player) && !home.isAccessible()) {

            Msg.send(sender, "no-access", home.getName());
            return true;

        }

        Msg.send(sender, "teleporting", home.getName());
        home.tp(player);

        return true;
    }

}
