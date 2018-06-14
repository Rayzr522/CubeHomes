package com.rayzr522.cubehomes.homes;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Msg;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_SETHOME)) {
            Msg.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Msg.send(p, "usage.sethome");
            return true;
        }

        Home home = HomeManager.get(args[0]);
        if (home != null) {
            if (!home.isOwner(p) && !p.hasPermission(Config.PERM_OTHERS)) {
                Msg.send(p, "not-owner", args[0]);
                return true;
            }

            if (!HomeManager.update(p, home)) {
                Msg.send(p, "error");
            } else {
                Msg.send(p, "home-set", args[0]);
            }

        } else if (numHomes(p) > 0 && (!p.hasPermission(Config.PERM_MORE) || !p.hasPermission(Config.PERM_MORE + "." + numHomes(p)))) {
            Msg.send(p, "max-homes", "" + numHomes(p));
        } else {
            HomeManager.add(p, args[0]);
            Msg.send(p, "home-set", args[0]);
        }

        return true;

    }

    public int numHomes(Player player) {
        return HomeManager.get(player).size();
    }

}
