package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CommandDelWarp implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandDelWarp(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Settings.PERM_DELWARP)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            Language.send(p, "usage.delwarp");
            return true;
        }

        WarpManager warpManager = plugin.getWarpManager();
        Optional<Warp> optionalWarp = warpManager.findWarp(args[0]);

        if (!optionalWarp.isPresent()) {
            Language.send(sender, "unknown-warp", args[0]);
            return true;
        }

        Warp warp = optionalWarp.get();

        if (!warpManager.removeWarp(warp)) {
            Language.send(p, "error");
        }

        Language.send(sender, "warp-deleted", args[0]);
        return true;
    }
}
