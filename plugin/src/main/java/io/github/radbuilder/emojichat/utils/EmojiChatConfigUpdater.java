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
 * @version 1.6
 * @since 1.5
 */
public class EmojiChatConfigUpdater {
	/**
	 * The current config version number.
	 */
	private final int CONFIG_VERSION = 3;
	
	/**
	 * Creates the EmojiChat config updater with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public EmojiChatConfigUpdater(EmojiChat plugin) {
		int configVersion = plugin.getConfig().getInt("config-version");
		
		if (configVersion < CONFIG_VERSION) {
			plugin.getLogger().info("Updating your config now (old: " + configVersion + ", new: " + CONFIG_VERSION + ")...");
			updateConfig(plugin, plugin.getConfig(), configVersion);
		}
	}
	
	/**
	 * Updates the EmojiChat config.
	 *
	 * @param plugin The EmojiChat main class instance.
	 * @param config The EmojiChat config.
	 * @param configVersion The current config version number.
	 */
	private void updateConfig(EmojiChat plugin, FileConfiguration config, int configVersion) {
		// Config v1 & v2 values
		boolean fixEmojiColoring = config.getBoolean("fix-emoji-coloring");
		boolean verifyDisabledList = config.getBoolean("verify-disabled-list");
		List<String> disabledEmojis = config.getStringList("disabled-emojis");
		LinkedHashMap<String, List<String>> shortcuts = new LinkedHashMap<>();
		for (String key : config.getConfigurationSection("shortcuts").getKeys(false)) { // Gets all of the headers/keys in the shortcuts section
			shortcuts.put(key, config.getStringList("shortcuts." + key));
		}
		
		// Config v2 values
		String metricsCollection = "FULL";
		boolean downloadResourcePack = true;
		if (configVersion == 2) {
			metricsCollection = config.getString("metrics-collection");
			downloadResourcePack = config.getBoolean("download-resourcepack");
		}
		
		// Config lines
		List<String> configLines = new ArrayList<>();
		configLines.add("# Configuration file for EmojiChat by RadBuilder");
		configLines.add("");
		configLines.add("# EmojiChat collects metrics in order to get a better understanding of what configurations are used.");
		configLines.add("# Statistics include how many servers it's being used on and what version of Bukkit/Spigot they're using,");
		configLines.add("# how many players are using it, server information such as Java version, how many emojis are used,");
		configLines.add("# and how many servers are using each feature. This data is anonymous, and is submitted to understand what");
		configLines.add("# types of server configurations are being used to build a better plugin (i.e. MC/Java versions to continue supporting,");
		configLines.add("# features to keep/remove). Data collection has very little/no impact on server performance. I'd appreciate if you");
		configLines.add("# keep this set to FULL, but I completely understand if you want to send less data or opt out.");
		configLines.add("#");
		configLines.add("# 'FULL'  collects what 'SOME' collects, and data on what config options you're using.");
		configLines.add("# 'SOME'  collects what 'BASIC' collects, and what hooks you're using.");
		configLines.add("# 'BASIC' collects data on what Java version you're using, Bukkit/Spigot version you're using, other general server");
		configLines.add("#         information like player count, how many emojis you've used, and shortcuts you've used.");
		configLines.add("# 'OFF'   collects NO DATA, however, I would appreciate it if you send at least basic data.");
		configLines.add("metrics-collection: '" + metricsCollection + "'");
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
		configLines.add("download-resourcepack: " + downloadResourcePack);
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
		if (configVersion == 1) {
			configLines.add("  crazy_face:");
			configLines.add("  - ':crazy:'");
			configLines.add("  face_with_raised_eyebrow:");
			configLines.add("  - ':hmm:'");
			configLines.add("  shushing_face:");
			configLines.add("  - ':shh:'");
		}
		configLines.add("  1st_place_medal:");
		configLines.add("  - ':first:'");
		configLines.add("  - ':1st:'");
		configLines.add("  2nd_place_medal:");
		configLines.add("  - ':second:'");
		configLines.add("  - ':2nd:'");
		configLines.add("  3rd_place_medal:");
		configLines.add("  - ':third:'");
		configLines.add("  - ':3rd:'");
		configLines.add("");
		configLines.add("# Emojis to disable. Remove them from the list to enable them.");
		configLines.add("# By default, profane and potentially offensive emojis are disabled.");
		configLines.add("disabled-emojis:");
		for (String disabledEmoji : disabledEmojis) {
			configLines.add("- '" + disabledEmoji + "'");
		}
		if (configVersion == 1) {
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
			configLines.add("- ':face_with_symbols_over_mouth:'");
			configLines.add("- ':face_vomiting:'");
		}
		configLines.add("");
		configLines.add("# The config version, used to be able to update your config when future versions come out.");
		configLines.add("# Don't change this, or you'll experience issues with EmojiChat.");
		configLines.add("config-version: " + CONFIG_VERSION);
		
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
