
package com.rayzr522.cubehomes.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.Config;
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

		p.sendMessage(ChatColor.RED + "WIP, go yell at Rayzr to get it done :P");
		return true;

	}

}
