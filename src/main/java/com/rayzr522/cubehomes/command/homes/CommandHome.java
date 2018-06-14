package com.rayzr522.cubehomes.command.homes;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.data.HomeManager;
import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHome implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandHome(CubeHomes plugin) {
        this.plugin = plugin;
    }

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

        HomeManager homeManager = plugin.getHomeManager();

        if (args[0].equalsIgnoreCase("toggle")) {
            boolean accessible = homeManager.toggleAccessibility(player);

            Msg.send(sender, "home-access", accessible ? "enabled" : "disabled");
            return true;
        }

        Home home = homeManager.get(args[0]);

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
