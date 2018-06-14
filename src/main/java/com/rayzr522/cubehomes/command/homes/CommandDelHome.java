package com.rayzr522.cubehomes.command.homes;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Msg;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelHome implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_DELHOME)) {
            Msg.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Msg.send(p, "usage.delhome");
            return true;
        }

        Home home = HomeManager.get(args[0]);

        if (home == null) {
            Msg.send(sender, "unknown-home", args.length > 1 ? args[1] : args[0]);
            return true;
        }

        if (!home.isOwner(p) && !p.hasPermission(Config.PERM_OTHERS)) {
            Msg.send(p, "not-owner", args[0]);
            return true;
        }

        if (!HomeManager.del(home)) {
            Msg.send(p, "error");
        }

        Msg.send(sender, "home-deleted", args[0]);

        return true;
    }

}
