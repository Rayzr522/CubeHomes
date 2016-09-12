
package com.rayzr522.cubehomes.menu;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.cubehomes.Config;
import com.rayzr522.cubehomes.warps.Warp;
import com.rayzr522.cubehomes.warps.Warps;

public class MenuHolder implements InventoryHolder {

	private int			page;
	private Inventory	inv;

	public MenuHolder(int page, Player player) {
		inv = Bukkit.createInventory(this, 54, Config.GUI_NAME);
		this.page = page;
		init(player);
	}

	private void init(Player player) {

		setItem(3, 5, Menu.BUTTON_PREV);
		setItem(4, 5, Menu.BUTTON_CLOSE);
		setItem(5, 5, Menu.BUTTON_NEXT);

		List<Warp> warps = Warps.getForPage(page);

		for (int i = 0; i < warps.size(); i++) {

			Warp warp = warps.get(i);
			if (warp.getWorld() != player.getWorld()) {
				continue;
			}

			setItem(i % 7, i / 7, Menu.button(warp.getIconType(), warp.getIconData(), "&a&l" + warp.getName()));

		}

	}

	public void setItem(int x, int y, ItemStack item) {
		inv.setItem(x + y * 9, item);
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

}
