
package com.rayzr522.cubehomes.cmd;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.Home;
import com.rayzr522.cubehomes.Homes;
import com.rayzr522.cubehomes.Msg;

public class CommandDelHome implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			Msg.send(sender, "only-players");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission(Config.PERM_DELHOME)) {

			Msg.send(p, "no-permission");
			return true;

		}

		if (args.length < 1) {

			Msg.send(p, "usage.delhome");
			return true;

		}

		Home home;
		UUID id = p.getUniqueId();

		if (args.length > 1) {

			if (!p.hasPermission(Config.PERM_OTHERS)) {

				Msg.send(p, "no-permission");
				return true;

			}

			id = CubeHomes.pn.get(args[1]);

			if (id == null) {

				Msg.send(p, "no-player", args[1]);
				return true;

			}

			home = Homes.get(id, args[0]);

		} else {

			home = Homes.get(id, args[0]);

		}

		if (home == null) {

			Msg.send(sender, "unknown-home", args[0]);
			return true;

		}

		Homes.del(id, home);
		Msg.send(sender, "home-deleted", args[0]);

		return true;

	}

}
