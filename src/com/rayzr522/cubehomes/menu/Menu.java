
package com.rayzr522.cubehomes.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rayzr522.cubehomes.Color;
import com.rayzr522.cubehomes.TextUtils;

public class Menu implements Listener {

	public static final ItemStack	BUTTON_PREV		= button(Material.STAINED_GLASS_PANE, Color.LIME, "&a&lPrevious Page", "&7&oTakes you to the previous page");
	public static final ItemStack	BUTTON_CLOSE	= button(Material.REDSTONE_BLOCK, 0, "&cClose", "&7&oCloses the menu");
	public static final ItemStack	BUTTON_NEXT		= button(Material.STAINED_GLASS_PANE, Color.LIME, "&a&lNext Page", "&7&oTakes you to the next page");

	public static ItemStack button(Material type, int data, String name, String... lore) {

		ItemStack item = new ItemStack(type, 1, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(TextUtils.colorize(name));
		List<String> _lore = new ArrayList<String>();
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

		if (!(e.getInventory().getHolder() instanceof MenuHolder)) { return; }

		// MenuHolder holder = (MenuHolder) e.getInventory().getHolder();

		System.out.println(e.getSlot());
		System.out.println(e.getRawSlot());

	}

}
