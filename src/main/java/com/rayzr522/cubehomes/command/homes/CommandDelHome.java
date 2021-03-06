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

public class CommandDelHome implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandDelHome(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Settings.PERM_DELHOME)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(p, "usage.delhome");
            return true;
        }

        HomeManager homeManager = plugin.getHomeManager();

        Optional<Home> optionalHome = homeManager.findHome(args[0]);

        if (!optionalHome.isPresent()) {
            Language.send(sender, "unknown-home", args.length > 1 ? args[1] : args[0]);
            return true;
        }

        Home home = optionalHome.get();

        if (!home.isOwner(p) && !p.hasPermission(Settings.PERM_OTHERS)) {
            Language.send(p, "not-owner", args[0]);
            return true;
        }

        if (!homeManager.removeHome(home)) {
            Language.send(p, "error");
        }

        Language.send(sender, "home-deleted", args[0]);

        return true;
    }

}
