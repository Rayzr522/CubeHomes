package com.rayzr522.cubehomes.menu;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.TextUtils;
import com.rayzr522.cubehomes.data.Warp;
import com.rayzr522.cubehomes.data.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuHolder implements InventoryHolder {

    private int page;
    private List<Warp> warps;
    private Inventory inv;

    public MenuHolder(int page, Player player) {
        this.inv = Bukkit.createInventory(this, 54, Config.GUI_NAME);
        this.page = page;
        this.warps = WarpManager.getForPage(player, page);

        init(player);
    }

    private void init(Player player) {
        setItem(3, 5, Menu.BUTTON_PREV);
        setItem(4, 5, Menu.BUTTON_CLOSE);
        setItem(5, 5, Menu.BUTTON_NEXT);

        for (int i = 0; i < warps.size(); i++) {
            Warp warp = warps.get(i);

            setItem(1 + i % 7, 1 + i / 7, Menu.button(warp.getIconType(), warp.getIconData(), "&a&l" + TextUtils.capitalize(warp.getName())));
        }
    }

    public void setItem(int x, int y, ItemStack item) {
        inv.setItem(x + y * 9, item);
    }

    public int getPage() {
        return page;
    }

    public List<Warp> getWarps() {
        return warps;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
