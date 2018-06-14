package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Msg;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.menu.Menu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarp implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_WARP)) {
            Msg.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            openMenu(p);
            return true;
        }

        Warp warp = WarpManager.get(args[0]);

        if (warp == null || (Config.PER_WORLD_WARPS && warp.getWorld() != p.getWorld())) {
            Msg.send(sender, "unknown-warp", args[0]);
            return true;
        }

        if (!warp.hasPermission(p)) {
            Msg.send(sender, "no-permission-warp");
            return true;
        }

        Msg.send(sender, "teleporting-warp", warp.getName());
        warp.tp(p);

        return true;
    }

    private void openMenu(Player p) {
        p.openInventory(Menu.create(p, 0));
    }

}
