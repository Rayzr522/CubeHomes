
package com.rayzr522.cubehomes.command.warps;

import java.util.Arrays;

import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.cubehomes.utils.ArrayUtils;
import com.rayzr522.cubehomes.utils.Config;
import com.rayzr522.cubehomes.utils.Msg;
import com.rayzr522.cubehomes.utils.TextUtils;

public class CommandWarpIcon implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender, "only-players");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Config.PERM_WARPICON)) {
            Msg.send(p, "no-permission");
            return true;
        }

        if (args.length < 2) {
            Msg.send(p, "usage.warpicon");
            return true;
        }

        Warp warp = WarpManager.get(args[0]);
        if (warp == null) {
            Msg.send(p, "unknown-warp", args[0]);
            return true;
        }

        String itemString = ArrayUtils.concat(Arrays.copyOfRange(args, 1, args.length), " ");
        int data = 0;
        if (itemString.indexOf(':') != -1) {
            try {
                data = Integer.parseInt(itemString.split(":")[1]);
            } catch (Exception e) {
                Msg.send(p, "item.invalid-data", itemString.split(":")[1]);
                return true;
            }
            itemString = itemString.split(":")[0];
        }

        Material type = Material.getMaterial(TextUtils.enumFormat(itemString));
        if (type == null) {
            Msg.send(p, "item.invalid-type", itemString);
            return true;
        }

        warp.setIcon(type, data);
        Msg.send(p, "warp-icon-set", args[0], type.toString() + (data > 0 ? ":" + data : ""));

        return true;

    }

}
