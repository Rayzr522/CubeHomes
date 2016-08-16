
package com.rayzr522.cubehomes;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 
 * This is a simple class which keeps tracks of associations between player
 * names and UUIDs
 * 
 * @author Rayzr
 *
 */
public class PlayerNames {

	private HashMap<UUID, String>	uuidToString	= new HashMap<UUID, String>();
	private HashMap<String, UUID>	stringToUUID	= new HashMap<String, UUID>();

	private JavaPlugin				plugin;

	private String					dataFilePath	= "playerNames.db";

	public PlayerNames(JavaPlugin plugin) {

		this.plugin = plugin;

		dataFilePath = plugin.getDataFolder() + File.separator + dataFilePath;

		plugin.getServer().getPluginManager().registerEvents(new PlayerNameListener(this), plugin);

		new PlayerNamesSaveTask(this).runTaskTimer(plugin, 0, 6000);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(getDBFile());

		for (String key : config.getKeys(false)) {

			set(UUID.fromString(key), config.getString(key));

		}

	}

	private File getDBFile() {

		File file = new File(dataFilePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.err.println("Failed to create playerNames.db for the PlayerNames util.");
				System.err.println("Please report this to Rayzr on the Bukkit or Spigot forums along with the following stacktrace:");
				e.printStackTrace();
			}
		}

		return file;

	}

	/**
	 * Saves the stored UUID-name associations to the disk. This is
	 * automatically run every 5 minutes. <br>
	 * <br>
	 * NOTE: This runs in a separate thread, so it shouldn't ever affect server
	 * performance.
	 */
	public void save() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				YamlConfiguration config = new YamlConfiguration();

				for (Entry<UUID, String> pair : uuidToString.entrySet()) {

					config.set(pair.getKey().toString(), pair.getValue());

				}

				File file = getDBFile();

				try {
					config.save(file);
				} catch (Exception e) {
					System.err.println("Failed to save to the playerNames.db file for the PlayerNames util.");
					System.err.println("Please report this to Rayzr on the Bukkit or Spigot forums along with the following stacktrace:");
					e.printStackTrace();
				}

			}

		}).start();

	}

	/**
	 * Alias of {@code exists(p.getUniqueId());}
	 * 
	 * @param p
	 *            the player
	 * @return If there is a name stored for the given player
	 */
	public boolean exists(Player p) {

		return exists(p.getUniqueId());

	}

	/**
	 * Whether or not there is a name stored for the given player
	 * 
	 * @param id
	 *            the UUID of the player
	 * @return If there is a name stored for the given UUID
	 */
	public boolean exists(UUID id) {

		return uuidToString.containsKey(id);

	}

	/**
	 * Sets the given UUID-name association
	 * 
	 * @param id
	 *            the UUID
	 * @param name
	 *            the name
	 */
	public void set(UUID id, String name) {

		uuidToString.put(id, name);
		stringToUUID.put(name, id);

	}

	/**
	 * Sets the value for the player. Alias of
	 * {@code set(p.getUniqueId(), p.getName());}
	 * 
	 * @param p
	 *            the player
	 */
	public void set(Player p) {

		set(p.getUniqueId(), p.getName());

	}

	/**
	 * Gets the UUID of a player with the given name, case-sensitive. If you
	 * want to ignore case then use {@link PlayerNames#getIgnoreCase(String)}
	 * 
	 * @param name
	 *            the name of the player, case sensitive
	 * @return The UUID of the player with the given name, or null
	 */
	public UUID get(String name) {

		return stringToUUID.get(name);

	}

	/**
	 * Get the last known name of the player with the given UUID
	 * 
	 * @param id
	 *            the UUID of the player
	 * @return The last known name, or null if the player has never been on the
	 *         server
	 */
	public String get(UUID id) {

		return uuidToString.get(id);

	}

	/**
	 * Gets the UUID of a player with the given name, ignoring case (might be
	 * slower)
	 * 
	 * @param name
	 *            the name of the player, ignoring case
	 * @return The UUID of the player with the given name, or null
	 */
	public UUID getIgnoreCase(String name) {

		for (Entry<UUID, String> entry : uuidToString.entrySet()) {
			if (entry.getValue().toLowerCase().equals(name.toLowerCase())) { return entry.getKey(); }
		}

		return null;

	}

	public class PlayerNameListener implements Listener {

		private PlayerNames pn;

		public PlayerNameListener(PlayerNames pn) {
			this.pn = pn;
		}

		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent e) {
			if (!pn.exists(e.getPlayer())) {
				pn.set(e.getPlayer());
			}
		}

	}

	public class PlayerNamesSaveTask extends BukkitRunnable {

		private PlayerNames pn;

		public PlayerNamesSaveTask(PlayerNames pn) {
			this.pn = pn;
		}

		@Override
		public void run() {
			pn.save();
		}

	}

}
