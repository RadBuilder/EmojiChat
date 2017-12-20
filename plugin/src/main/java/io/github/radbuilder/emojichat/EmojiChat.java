package io.github.radbuilder.emojichat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
 * @since 1.0
 */
public class EmojiChat extends JavaPlugin {
	/**
	 * Stores the shortcuts and the emojis with the shortcut as the key and emoji as the value.
	 */
	HashMap<String, String> emojiMap;
	
	@Override
	public void onEnable() {
		emojiMap = new HashMap<>();
		
		// Save the config if there isn't one already
		saveDefaultConfig();
		
		// Load the emoji list from the config
		if (!loadList()) {
			getLogger().warning("There was an issue with your config: " + emojiMap.size() + " shortcuts were loaded.");
		} else {
			getLogger().info("Config loaded without any issues.");
		}
		
		// Register the chat listener
		Bukkit.getPluginManager().registerEvents(new EmojiChatListener(this), this);
		
		// Register the "emojichat" and "ec" commands
		EmojiChatCommand emojiChatCommand = new EmojiChatCommand(this);
		getCommand("emojichat").setExecutor(emojiChatCommand);
		getCommand("ec").setExecutor(emojiChatCommand);
	}
	
	/**
	 * Loads the emojis and their shortcuts from the config and puts them into the {@link #emojiMap}.
	 *
	 * @return True if the emojis and their shortcuts were successfully loaded, false otherwise.
	 */
	boolean loadList() {
		// Remove previous items in case the user is reloading the config
		emojiMap.clear();
		
		try {
			for (String item : getConfig().getStringList("emojis")) {
				String[] line = item.split(" "); // Split by spaces
				for (int i = 1; i < line.length; i++) {
					emojiMap.put(line[i], line[0]);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
