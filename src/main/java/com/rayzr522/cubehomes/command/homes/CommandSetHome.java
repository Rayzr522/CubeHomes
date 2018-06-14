package com.rayzr522.cubehomes.command.homes;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandSetHome(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_SETHOME)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(p, "usage.sethome");
            return true;
        }

        HomeManager homeManager = plugin.getHomeManager();
        Home home = homeManager.get(args[0]);

        if (home != null) {
            if (!home.isOwner(p) && !p.hasPermission(Config.PERM_OTHERS)) {
                Language.send(p, "not-owner", args[0]);
                return true;
            }

            if (!homeManager.update(p, home)) {
                Language.send(p, "error");
            } else {
                Language.send(p, "home-set", args[0]);
            }
        } else if (numHomes(p) > 0 && (!p.hasPermission(Config.PERM_MORE) || !p.hasPermission(Config.PERM_MORE + "." + numHomes(p)))) {
            Language.send(p, "max-homes", "" + numHomes(p));
        } else {
            homeManager.add(p, args[0]);
            Language.send(p, "home-set", args[0]);
        }

        return true;
    }

    public int numHomes(Player player) {
        return plugin.getHomeManager().get(player).size();
    }

}
