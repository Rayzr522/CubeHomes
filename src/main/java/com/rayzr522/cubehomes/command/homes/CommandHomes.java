package com.rayzr522.cubehomes.command.homes;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Home;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rayzr522
 */
public class CommandHomes implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandHomes(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Settings.PERM_LIST_HOMES)) {
            Language.send(player, "no-permission");
            return true;
        }

        List<Home> homes = plugin.getHomeManager().all();

        if (homes.size() < 1) {
            Language.send(player, "no-homes");
        } else {
            Language.send(player, "all-homes", homes.stream().map(Home::getName).collect(Collectors.joining(", ")));
        }

        return true;
    }
}

