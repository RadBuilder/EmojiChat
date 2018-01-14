package io.github.radbuilder.emojichat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * EmojiChat listener class.
 *
 * @author RadBuilder
 * @version 1.7
 * @since 1.0
 */
class EmojiChatListener implements Listener {
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	/**
	 * If EmojiChat should automatically download the ResourcePack for the player.
	 */
	private final boolean autoDownloadResourcePack;
	
	/**
	 * Creates the EmojiChat listener class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatListener(EmojiChat plugin) {
		this.plugin = plugin;
		autoDownloadResourcePack = plugin.getConfig().getBoolean("download-resourcepack");
	}
	
	@EventHandler
	void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		// Send the player an alert if there's an update available
		if (player.hasPermission("emojichat.updates") && plugin.updateChecker.updateAvailable) {
			player.sendMessage(ChatColor.AQUA + "An update for EmojiChat is available.");
			player.sendMessage(ChatColor.AQUA + "Current version: " + ChatColor.GOLD + plugin.updateChecker.currentVersion
					+ ChatColor.AQUA + ". Latest version: " + ChatColor.GOLD + plugin.updateChecker.latestVersion + ChatColor.AQUA + ".");
		}
		
		if (!autoDownloadResourcePack) // If auto downloading of the ResourcePack is disabled
			return;
		
		// Send the player the resource pack
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			if (player.hasPermission("emojichat.see")) { // If the player can see emojis
				try {
					player.setResourcePack(plugin.PACK_URL, plugin.PACK_SHA1); // If the Spigot version supports loading cached versions
				} catch (Exception | NoSuchMethodError e) {
					player.setResourcePack(plugin.PACK_URL); // If the Spigot version doesn't support loading cached versions
				}
			}
		}, 20L); // Give time for the player to join
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	void onChat(AsyncPlayerChatEvent event) {
		if (!event.getPlayer().hasPermission("emojichat.use") || !event.getPlayer().hasPermission("emojichat.use.chat"))
			return; // Don't do anything if they don't have permission
		
		String message = event.getMessage();
		
		// Checks if the user disabled shortcuts via /emojichat toggle
		if (!plugin.getEmojiHandler().hasShortcutsOff(event.getPlayer())) {
			message = plugin.getEmojiHandler().translateShorthand(message);
		}
		
		// Replace shortcuts with emojis
		message = plugin.getEmojiHandler().toEmojiFromChat(message);
		
		// If the message contains a disabled character
		if (plugin.getEmojiHandler().containsDisabledCharacter(message)) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Oops! You can't use disabled emoji characters!");
			return;
		}
		
		event.setMessage(message);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	void onSignChange(SignChangeEvent event) {
		if (!event.getPlayer().hasPermission("emojichat.use") || !event.getPlayer().hasPermission("emojichat.use.sign"))
			return; // Don't do anything if they don't have permission
		
		if (!plugin.getConfig().getBoolean("emojis-on-signs")) // Feature is disabled
			return;
		
		for (int i = 0; i < event.getLines().length; i++) {
			String line = event.getLine(i);
			
			// Checks if the user disabled shortcuts via /emojichat toggle
			if (!plugin.getEmojiHandler().hasShortcutsOff(event.getPlayer())) {
				line = plugin.getEmojiHandler().translateShorthand(line);
			}
			
			// Replace shortcuts with emojis
			line = plugin.getEmojiHandler().toEmoji(line);
			
			// If the message contains a disabled character
			if (plugin.getEmojiHandler().containsDisabledCharacter(line)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "Oops! You can't use disabled emoji characters!");
				return;
			}
			
			event.setLine(i, line);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		if (!event.getPlayer().hasPermission("emojichat.use") || !event.getPlayer().hasPermission("emojichat.use.command"))
			return; // Don't do anything if they don't have permission
		
		if (!plugin.getConfig().getBoolean("emojis-in-commands")) // Feature is disabled
			return;
		
		String command = event.getMessage();
		
		// only-command-list is enabled and the command-list doesn't contain the command being ran
		if (plugin.getConfig().getBoolean("only-command-list") && !plugin.getConfig().getStringList("command-list").contains(command.split(" ")[0].toLowerCase())) {
			return;
		}
		
		// Checks if the user disabled shortcuts via /emojichat toggle
		if (!plugin.getEmojiHandler().hasShortcutsOff(event.getPlayer())) {
			command = plugin.getEmojiHandler().translateShorthand(command);
		}
		
		// Replace shortcuts with emojis
		command = plugin.getEmojiHandler().toEmoji(command);
		
		// If the message contains a disabled character
		if (plugin.getEmojiHandler().containsDisabledCharacter(command)) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Oops! You can't use disabled emoji characters!");
			return;
		}
		
		event.setMessage(command);
	}
	
	@EventHandler
	void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("Emoji List")) {
			event.setCancelled(true);
			if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.DIAMOND && event.getCurrentItem().hasItemMeta()
					&& event.getCurrentItem().getItemMeta().hasDisplayName()) { // Make sure the item clicked is a page change item
				try {
					int currentPage = Integer.parseInt(event.getInventory().getTitle().split(" ")[3]) - 1; // Get the page number from the title
					
					if (event.getCurrentItem().getItemMeta().getDisplayName().contains("<-")) { // Back button
						event.getWhoClicked().openInventory(plugin.emojiChatGui.getInventory(currentPage - 1));
					} else { // Next button
						event.getWhoClicked().openInventory(plugin.emojiChatGui.getInventory(currentPage + 1));
					}
				} catch (Exception e) { // Something happened, not sure what, so just reset their page to 0
					event.getWhoClicked().openInventory(plugin.emojiChatGui.getInventory(0));
				}
			}
		}
	}
}
