package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelWarp implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandDelWarp(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_DELWARP)) {
            Msg.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Msg.send(p, "usage.delwarp");
            return true;
        }

        WarpManager warpManager = plugin.getWarpManager();
        Warp warp = warpManager.get(args[0]);

        if (warp == null) {
            Msg.send(sender, "unknown-warp", args[0]);
            return true;
        }

        if (!warpManager.del(warp)) {
            Msg.send(p, "error");
        }

        Msg.send(sender, "warp-deleted", args[0]);
        return true;
    }
}
