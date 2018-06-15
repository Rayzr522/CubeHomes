package com.rayzr522.cubehomes.command.homes;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

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

        if (!p.hasPermission(Settings.PERM_SETHOME)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(p, "usage.sethome");
            return true;
        }

        HomeManager homeManager = plugin.getHomeManager();
        Optional<Home> optionalHome = homeManager.findHome(args[0]);

        if (optionalHome.isPresent()) {
            Home home = optionalHome.get();

            if (!home.isOwner(p) && !p.hasPermission(Settings.PERM_OTHERS)) {
                Language.send(p, "not-owner", args[0]);
                return true;
            }

            if (!homeManager.updateHome(p, home)) {
                Language.send(p, "error");
            } else {
                Language.send(p, "home-set", args[0]);
            }
        } else if (numHomes(p) > 0 && (!p.hasPermission(Settings.PERM_MORE) || !p.hasPermission(Settings.PERM_MORE + "." + numHomes(p)))) {
            Language.send(p, "max-homes", "" + numHomes(p));
        } else {
            homeManager.addHome(p, args[0]);
            Language.send(p, "home-set", args[0]);
        }

        return true;
    }

    public int numHomes(Player player) {
        return plugin.getHomeManager().getHomesForPlayer(player).size();
    }

}
