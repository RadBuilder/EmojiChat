package io.github.radbuilder.emojichat.utils;

import io.github.radbuilder.emojichat.EmojiChat;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * EmojiChat config updater.
 *
 * @author RadBuilder
 * @since 1.5
 * @version 1.5
 */
public class EmojiChatConfigUpdater {
	/**
	 * The current config version number.
	 */
	private final int CONFIG_VERSION = 2;
	
	/**
	 * Creates the EmojiChat config updater with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public EmojiChatConfigUpdater(EmojiChat plugin) {
		int configVersion = plugin.getConfig().getInt("config-version");
		
		if (configVersion + 1 == CONFIG_VERSION) {
			plugin.getLogger().info("Updating your config now...");
			updateConfig(plugin, plugin.getConfig());
		} else if (configVersion + 1 < CONFIG_VERSION) {
			plugin.getLogger().info("Your config is too old to update...");
			plugin.getLogger().info("Your config has been renamed to 'config_old.yml' and a new one has been generated.");
		}
	}
	
	/**
	 * Updates the EmojiChat config.
	 *
	 * @param plugin The EmojiChat main class instance.
	 * @param config The EmojiChat config.
	 */
	private void updateConfig(EmojiChat plugin, FileConfiguration config) {
		// Old config values
		boolean fixEmojiColoring = config.getBoolean("fix-emoji-coloring");
		boolean verifyDisabledList = config.getBoolean("verify-disabled-list");
		List<String> disabledEmojis = config.getStringList("disabled-emojis");
		LinkedHashMap<String, List<String>> shortcuts = new LinkedHashMap<>();
		for (String key : config.getConfigurationSection("shortcuts").getKeys(false)) { // Gets all of the headers/keys in the shortcuts section
			shortcuts.put(key, config.getStringList("shortcuts." + key));
		}
		
		// Config lines
		List<String> configLines = new ArrayList<>();
		configLines.add("# Configuration file for EmojiChat by RadBuilder");
		configLines.add("");
		configLines.add("# The config version, used to be able to update your config when future versions come out.");
		configLines.add("config-version: 2");
		configLines.add("");
		configLines.add("# If you're using chat color plugins, this will remove the coloring for emojis to be displayed correctly.");
		configLines.add("fix-emoji-coloring: " + fixEmojiColoring);
		configLines.add("");
		configLines.add("# This prevents players from using the characters associated with disabled emojis.");
		configLines.add("# Disabling this will still disable the emojis listed in 'disabled-emojis', but won't check");
		configLines.add("# if the player is using it in chat.");
		configLines.add("verify-disabled-list: " + verifyDisabledList);
		configLines.add("");
		configLines.add("# If EmojiChat should auto download the ResourcePack. If you'd rather have your players manually");
		configLines.add("# download or use /emojichat resourcepack, set this to false.");
		configLines.add("download-resourcepack: true");
		configLines.add("");
		configLines.add("# Shortcuts will replace the items in the list with the correct emoji name.");
		configLines.add("# For example, :) will be replaced with :grinning:, which then will turn it into the emoji.");
		configLines.add("shortcuts:");
		for (String shortcutKey : shortcuts.keySet()) {
			configLines.add("  " + shortcutKey + ":");
			for (String shortcutListItem : shortcuts.get(shortcutKey)) {
				configLines.add("  - '" + shortcutListItem + "'");
			}
		}
		configLines.add("");
		configLines.add("# Emojis to disable. Remove them from the list to enable them.");
		configLines.add("# By default, profane and potentially offensive emojis are disabled.");
		configLines.add("disabled-emojis:");
		for (String disabledEmoji : disabledEmojis) {
			configLines.add("- '" + disabledEmoji + "'");
		}
		configLines.add("- ':sweat_drops:'");
		configLines.add("- ':banana:'");
		configLines.add("- ':cherries:'");
		configLines.add("- ':peach:'");
		configLines.add("- ':tomato:'");
		configLines.add("- ':eggplant:'");
		configLines.add("- ':cucumber:'");
		configLines.add("- ':beer:'");
		configLines.add("- ':beers:'");
		configLines.add("- ':clinking_glasses:'");
		configLines.add("- ':wine_glass:'");
		configLines.add("- ':tumbler_glass:'");
		configLines.add("- ':cocktail:'");
		
		// Update the config
		setConfig(plugin, configLines);
	}
	
	/**
	 * Sets the config to be the specified set of lines.
	 *
	 * @param plugin The EmojiChat main class instance.
	 * @param configLines The list of lines to set the config to.
	 */
	private void setConfig(EmojiChat plugin, List<String> configLines) {
		try {
			File configFile = new File(plugin.getDataFolder() + "/config.yml");
			configFile.delete(); // Delete the old config
			
			// Create the new config
			configFile = new File(plugin.getDataFolder() + "/config.yml");
			FileOutputStream fileOutputStream = new FileOutputStream(configFile);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			Writer writer = new BufferedWriter(outputStreamWriter);
			for (String configLine : configLines) {
				writer.write(configLine + "\n");
			}
			
			// Cleanup
			writer.close();
			outputStreamWriter.close();
			fileOutputStream.close();
			configLines.clear();
			plugin.getLogger().info("Config successfully updated.");
		} catch (Exception e) {
			plugin.getLogger().severe("An error occured while updating your config. More details below.");
			e.printStackTrace();
		}
	}
}
