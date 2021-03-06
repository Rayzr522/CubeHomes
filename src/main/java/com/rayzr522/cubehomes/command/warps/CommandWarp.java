package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.menu.MenuListener;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CommandWarp implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandWarp(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Settings.PERM_WARP)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 1) {
            openMenu(p);
            return true;
        }

        WarpManager warpManager = plugin.getWarpManager();
        Optional<Warp> optionalWarp = warpManager.findWarp(args[0]);

        if (!optionalWarp.isPresent() || (Settings.PER_WORLD_WARPS && optionalWarp.get().getWorld() != p.getWorld())) {
            Language.send(sender, "unknown-warp", args[0]);
            return true;
        }

        Warp warp = optionalWarp.get();

        if (!warp.hasPermission(p)) {
            Language.send(sender, "no-permission-warp");
            return true;
        }

        Language.send(sender, "teleporting-warp", warp.getName());
        warp.tp(p);

        return true;
    }

    private void openMenu(Player p) {
        p.openInventory(MenuListener.create(p, 0));
    }
}
