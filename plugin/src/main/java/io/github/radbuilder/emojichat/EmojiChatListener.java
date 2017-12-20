package io.github.radbuilder.emojichat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * EmojiChat listener class.
 *
 * @author RadBuilder
 * @since 1.0
 */
class EmojiChatListener implements Listener {
	/**
	 * EmojiChat main class instance.
	 */
	private EmojiChat plugin;
	
	/**
	 * Creates the EmojiChat listener class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatListener(EmojiChat plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	void onChat(AsyncPlayerChatEvent event) {
		if (!event.getPlayer().hasPermission("emojichat.use"))
			return; // Don't do anything if they don't have permission
		
		String message = event.getMessage();
		
		// Replace shortcuts with emojis
		for (String key : plugin.emojiMap.keySet()) {
			message = message.replace(key, plugin.emojiMap.get(key));
		}
		
		event.setMessage(message);
	}
}
