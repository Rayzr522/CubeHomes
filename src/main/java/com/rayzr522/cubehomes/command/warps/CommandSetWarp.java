package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetWarp implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandSetWarp(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Settings.PERM_SETWARP)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(p, "usage.setwarp");
            return true;
        }

        WarpManager warpManager = plugin.getWarpManager();
        warpManager.add(p, args[0]);

        Language.send(p, "warp-set", args[0]);
        return true;
    }
}
