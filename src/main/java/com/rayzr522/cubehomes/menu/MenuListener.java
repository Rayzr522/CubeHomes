package com.rayzr522.cubehomes.menu;

import com.rayzr522.cubehomes.CubeHomes;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.utils.Language;
import com.rayzr522.cubehomes.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {
    static final ItemStack BUTTON_PREV = button(Material.STAINED_GLASS_PANE, 14, "&cPrevious Page", "&7&oTakes you to the previous page");
    static final ItemStack BUTTON_CLOSE = button(Material.REDSTONE_BLOCK, 0, "&cClose", "&7&oCloses the menu");
    static final ItemStack BUTTON_NEXT = button(Material.STAINED_GLASS_PANE, 14, "&cNext Page", "&7&oTakes you to the next page");

    private final CubeHomes plugin;

    public MenuListener(CubeHomes plugin) {
        this.plugin = plugin;
    }

    static ItemStack button(Material type, int data, String name, String... lore) {

        ItemStack item = new ItemStack(type, 1, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.colorize(name));
        List<String> _lore = new ArrayList<>();
        for (String str : lore) {
            if (str == null) {
                _lore.add("");
            } else {
                _lore.add(TextUtils.colorize(str));
            }
        }
        meta.setLore(_lore);
        item.setItemMeta(meta);

        return item;

    }

    public static Inventory create(Player player, int page) {
        return new MenuHolder(page, player).getInventory();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof MenuHolder)) {
            return;
        }

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getWhoClicked();

        if (e.getRawSlot() >= 54) {
            return;
        }

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        MenuHolder holder = (MenuHolder) e.getInventory().getHolder();

        if (e.getCurrentItem().equals(BUTTON_PREV)) {

            e.getWhoClicked().closeInventory();

            int page = holder.getPage();
            if (page <= 0) {
                return;
            }

            p.openInventory(MenuListener.create(p, page - 1));

        } else if (e.getCurrentItem().equals(BUTTON_CLOSE)) {

            e.getWhoClicked().closeInventory();

        } else if (e.getCurrentItem().equals(BUTTON_NEXT)) {

            e.getWhoClicked().closeInventory();

            int page = holder.getPage();
            if (page >= plugin.getWarpManager().maxPage()) {
                return;
            }

            p.openInventory(create(p, page + 1));

        } else {

            int slot = e.getRawSlot();
            int x = slot % 9 - 1;
            int y = slot / 9 - 1;
            int index = x + y * 7;

            if (index >= holder.getWarps().size()) {
                return;
            }

            Warp warp = holder.getWarps().get(index);

            warp.tp(p);
            Language.send(p, "teleporting-warp", warp.getName());
        }

    }

}
