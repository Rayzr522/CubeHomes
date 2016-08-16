
package com.rayzr522.cubehomes.cmd;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.ArrayUtils;
import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.Home;
import com.rayzr522.cubehomes.Homes;
import com.rayzr522.cubehomes.Msg;

public class CommandHome implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			Msg.send(sender, "only-players");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission(Config.PERM_HOME)) {

			Msg.send(p, "no-permission");
			return true;

		}

		if (args.length < 1) {

			listHomes(p);
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

	private void listHomes(Player player) {

		List<Home> homes = Homes.get(player);

		if (homes.size() < 1) {

			Msg.send(player, "no-homes");

		} else {

			Msg.send(player, "your-homes", ArrayUtils.concat(ArrayUtils.names(homes), ", "));

		}

	}

}
