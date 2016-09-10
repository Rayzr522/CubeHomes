
package com.rayzr522.cubehomes.warps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Msg;

public class CommandWarpIcon implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			Msg.send(sender, "only-players");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission(Config.PERM_WARPICON)) {

			Msg.send(p, "no-permission");
			return true;

		}

		if (args.length < 1) {

			Msg.send(p, "usage.warpicon");
			return true;

		}

		Warps.add(p, args[0]);
		Msg.send(p, "warp-icon-set", args[0]);

		return true;

	}

}
