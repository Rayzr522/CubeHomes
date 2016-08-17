
package com.rayzr522.cubehomes.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Homes;
import com.rayzr522.cubehomes.Msg;

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

		Homes.set(p, args[0]);
		Msg.send(p, "home-set", args[0]);

		return true;

	}

}
