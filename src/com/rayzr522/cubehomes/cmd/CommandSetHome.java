
package com.rayzr522.cubehomes.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.Home;
import com.rayzr522.cubehomes.Homes;
import com.rayzr522.cubehomes.Msg;

public class CommandSetHome implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			Msg.send(sender, "only-players");
			return true;
		}

		Player p = (Player) sender;

		if (args.length < 1) {

			Msg.send(sender, "usage.home");
			return true;

		}

		Home home = Homes.get(p, args[0]);

		if (home == null) {

			Msg.send(sender, "unknown-home", args[0]);
			return true;

		}

		home.tp(p);

		return true;

	}

}
