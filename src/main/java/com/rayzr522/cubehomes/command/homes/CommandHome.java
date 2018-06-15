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

public class CommandHome implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandHome(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Settings.PERM_HOME)) {
            Language.send(player, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(player, "usage.home");
            return true;
        }

        HomeManager homeManager = plugin.getHomeManager();

        if (args[0].equalsIgnoreCase("toggle")) {
            boolean accessible = homeManager.toggleAccessibility(player);

            Language.send(sender, "home-access", accessible ? "enabled" : "disabled");
            return true;
        }

        Optional<Home> optionalHome = homeManager.findHome(args[0]);

        if (!optionalHome.isPresent()) {
            Language.send(sender, "unknown-home", args.length > 1 ? args[1] : args[0]);
            return true;
        }

        Home home = optionalHome.get();

        if (!home.isOwner(player) && !home.isAccessible()) {
            Language.send(sender, "no-access", home.getName());
            return true;
        }

        Language.send(sender, "teleporting", home.getName());
        home.tp(player);

        return true;
    }
}
