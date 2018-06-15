package com.rayzr522.cubehomes.command.warps;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import com.rayzr522.cubehomes.utils.ArrayUtils;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.Settings;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class CommandWarpIcon implements CommandExecutor {
    private final CubeHomes plugin;

    public CommandWarpIcon(CubeHomes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Settings.PERM_WARPICON)) {
            Language.send(p, "no-permission");
            return true;
        }

        if (args.length < 2) {
            Language.send(p, "usage.warpicon");
            return true;
        }

        WarpManager warpManager = plugin.getWarpManager();
        Optional<Warp> optionalWarp = warpManager.findWarp(args[0]);

        if (!optionalWarp.isPresent()) {
            Language.send(p, "unknown-warp", args[0]);
            return true;
        }

        Warp warp = optionalWarp.get();

        String itemString = ArrayUtils.concat(Arrays.copyOfRange(args, 1, args.length), " ");
        int data = 0;
        if (itemString.indexOf(':') != -1) {
            try {
                data = Integer.parseInt(itemString.split(":")[1]);
            } catch (Exception e) {
                Language.send(p, "item.invalid-data", itemString.split(":")[1]);
                return true;
            }
            itemString = itemString.split(":")[0];
        }

        Material type = Material.getMaterial(TextUtils.enumFormat(itemString));
        if (type == null) {
            Language.send(p, "item.invalid-type", itemString);
            return true;
        }

        warp.setIcon(type, data);
        Language.send(p, "warp-icon-set", args[0], type.toString() + (data > 0 ? ":" + data : ""));

        return true;
    }
}
