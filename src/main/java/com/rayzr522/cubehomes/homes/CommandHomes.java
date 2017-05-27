package com.rayzr522.cubehomes.homes;

import com.rayzr522.cubehomes.ArrayUtils;
import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandHomes implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Config.PERM_LIST_HOMES)) {
            Msg.send(player, "no-permission");
            return true;
        }


        List<Home> homes = Homes.all();

        if (homes.size() < 1) {
            Msg.send(player, "no-homes");
        } else {
            Msg.send(player, "all-homes", ArrayUtils.concat(ArrayUtils.names(homes), ", "));
        }

        return true;
    }

}

