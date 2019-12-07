package io.github.radbuilder.emojichat.utils;

import io.github.radbuilder.emojichat.EmojiChat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Emoji handler class.
 *
 * @author RadBuilder
 * @version 1.8.3
 * @since 1.4
 */
public class EmojiHandler {
	/**
	 * The emojis.
	 */
	private final LinkedHashMap<String, Character> emojis;
	/**
	 * Shortcuts for the emojis, if specified.
	 */
	private final HashMap<String, String> shortcuts;
	/**
	 * Disabled emoji characters to prevent others from using them with the resource pack.
	 */
	private final List<Character> disabledCharacters;
	/**
	 * If we should fix the emoji's color (colored chat removes emoji coloring)
	 */
	private boolean fixColoring;
	/**
	 * A list of users (by UUID) who turned shortcuts off.
	 */
	private List<UUID> shortcutsOff;
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	/**
	 * The {@link EmojiPackVariant} being used.
	 */
	private EmojiPackVariant packVariant;
	
	/**
	 * Creates the emoji handler with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public EmojiHandler(EmojiChat plugin) {
		this.plugin = plugin;
		
		emojis = new LinkedHashMap<>();
		shortcuts = new HashMap<>();
		disabledCharacters = new ArrayList<>();
		shortcutsOff = new ArrayList<>();
		
		load(plugin);
	}
	
	/**
	 * Gets the {@link #emojis} map.
	 *
	 * @return {@link #emojis}.
	 */
	public LinkedHashMap<String, Character> getEmojis() {
		return emojis;
	}
	
	/**
	 * Gets the {@link #disabledCharacters} list.
	 *
	 * @return The {@link #disabledCharacters} list.
	 */
	public List<Character> getDisabledCharacters() {
		return disabledCharacters;
	}
	
	/**
	 * Gets the {@link #shortcuts} map.
	 *
	 * @return The {@link #shortcuts} map.
	 */
	public HashMap<String, String> getShortcuts() {
		return shortcuts;
	}
	
	/**
	 * Checks if the specified player has emoji shortcuts off.
	 *
	 * @param player The player to check.
	 * @return True if the player has shortcuts off, false otherwise.
	 */
	public boolean hasShortcutsOff(Player player) {
		return shortcutsOff.contains(player.getUniqueId());
	}
	
	/**
	 * Toggles emoji shortcut use on/off for the specified player.
	 *
	 * @param player The player to toggle emoji shortcuts on/off for.
	 */
	public void toggleShortcutsOff(Player player) {
		if (shortcutsOff.contains(player.getUniqueId())) {
			shortcutsOff.remove(player.getUniqueId());
		} else {
			shortcutsOff.add(player.getUniqueId());
		}
	}
	
	/**
	 * Validates the config.
	 *
	 * @param config The config to validate.
	 * @return True if the config is valid, false otherwise.
	 */
	private boolean validateConfig(FileConfiguration config) {
		try {
			return config.get("shortcuts") != null && config.get("disabled-emojis") != null
					&& config.get("fix-emoji-coloring") != null && config.get("disable-emojis") != null
					&& config.get("pack-variant") != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Loads the emoji shortcuts from the config.
	 *
	 * @param config The config to load emoji shortcuts from.
	 */
	private void loadShortcuts(FileConfiguration config) {
		for (String key : config.getConfigurationSection("shortcuts").getKeys(false)) { // Gets all of the headers/keys in the shortcuts section
			for (String shortcutListItem : config.getStringList("shortcuts." + key)) { // Gets all of the shortcuts for the key
				shortcuts.put(shortcutListItem, ":" + key + ":");
			}
		}
	}
	
	/**
	 * Loads the disabled emojis from the config.
	 *
	 * @param config The config to load disabled emojis from.
	 * @param plugin The EmojiChat main class instance.
	 */
	private void loadDisabledEmojis(FileConfiguration config, EmojiChat plugin) {
		if (config.getBoolean("disable-emojis")) {
			for (String disabledEmoji : config.getStringList("disabled-emojis")) {
				if (disabledEmoji == null || !getEmojis().containsKey(disabledEmoji)) {
					plugin.getLogger().warning("Invalid emoji specified in 'disabled-emojis': '" + disabledEmoji + "'. Skipping...");
					continue;
				}
				disabledCharacters.add(getEmojis().remove(disabledEmoji)); // Remove disabled emojis from the emoji list
			}
		}
	}
	
	/**
	 * If emoji coloring should be fixed.
	 *
	 * @return True if emoji coloring should be fixed, false otherwise.
	 */
	public boolean fixColoring() {
		return fixColoring;
	}
	
	/**
	 * Loads the emojis and their shortcuts into the {@link #emojis}.
	 */
	private void loadEmojis() {
		char emojiChar = '가'; // The unicode character we start with depending on the pack variant, which gets incremented
		switch (packVariant) {
			case KOREAN:
				emojiChar = '가';
				break;
			case CHINESE:
				emojiChar = '娀';
				break;
		}
		
		try {
			InputStream listInput = getClass().getResourceAsStream("/list.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) { // Ignored lines
					continue;
				}
				emojis.put(line, emojiChar++); // Add the emoji we're currently on and switch it to the next char
			}
			bufferedReader.close();
			listInput.close();
		} catch (Exception e) {
			plugin.getLogger().warning("An error occured while loading emojis. More info below.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears the {@link #emojis}, {@link #shortcuts}, and {@link #disabledCharacters} maps.
	 */
	public void disable() {
		emojis.clear();
		shortcuts.clear();
		disabledCharacters.clear();
		shortcutsOff.clear();
	}
	
	/**
	 * Loads the emoji handler data.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public void load(EmojiChat plugin) {
		disable();
		
		// Get the pack variant we're using BEFORE loading emojis
		packVariant = EmojiPackVariant.getVariantbyId(plugin.getConfig().getInt("pack-variant"));
		
		loadEmojis(); // Loads ALL emojis
		
		if (!validateConfig(plugin.getConfig())) { // Make sure the config is valid
			plugin.getLogger().warning("Your config is invalid. No configuation data was loaded.");
			plugin.getLogger().warning("Fix your config, then use /emojichat reload");
			plugin.getLogger().warning("If you're still running into issues after fixing your config, delete it and restart your server.");
		} else { // Config is valid, load config data
			loadShortcuts(plugin.getConfig()); // Loads all of the shortcuts specified in the config
			loadDisabledEmojis(plugin.getConfig(), plugin); // Loads all of the disabled emojis specified in the config.
			fixColoring = plugin.getConfig().getBoolean("fix-emoji-coloring");
		}
	}
	
	/**
	 * Converts the specified message's shortcuts (i.e. :100:) to emoji.
	 *
	 * @param message The message to convert.
	 * @return The converted message.
	 */
	public String toEmoji(String message) {
		for (String key : getEmojis().keySet()) {
			plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(message, key));
			message = message.replace(key, getEmojis().get(key).toString());
		}
		return message;
	}
	
	/**
	 * Converts the specified line's shortcuts (i.e. :100:) to emoji from sign.
	 *
	 * @param line The line to convert from sign.
	 * @return The converted line from sign.
	 */
	public String toEmojiFromSign(String line) {
		for (String key : getEmojis().keySet()) {
			plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(line, key));
			line = line.replace(key, ChatColor.WHITE + "" + getEmojis().get(key) + ChatColor.BLACK); // Sets the emoji color to white for correct coloring
		}
		return line;
	}
	
	/**
	 * Converts the specified message's shortcuts (i.e. :100:) to emoji from chat.
	 *
	 * @param message The message to convert from chat.
	 * @return The converted message from chat.
	 */
	public String toEmojiFromChat(String message) {
		// If we're not fixing the coloring, or the message is too small to have coloring
		if (!fixColoring || message.length() < 3) {
			message = toEmoji(message);
		} else {
			String chatColor = message.substring(0, 2); // Gets the chat color of the message, i.e. §a
			boolean hasColor = chatColor.contains("§");
			for (String key : getEmojis().keySet()) {
				plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(message, key));
				message = message.replace(key, ChatColor.WHITE + "" + getEmojis().get(key) + (hasColor ? chatColor : "")); // Sets the emoji color to white for correct coloring
			}
		}
		return message;
	}
	
	/**
	 * Replaces shorthand ("shortcuts" in config) with correct emoji shortcuts.
	 *
	 * @param message The original message.
	 * @return The message with correct emoji shortcuts.
	 */
	public String translateShorthand(String message) {
		message = message.replace("http://", "http\\://").replace("https://", "https\\://");
		StringBuilder replaced = new StringBuilder();
		int previousPosition = 0;
		
		// Go through all shortcuts
		for (String key : getShortcuts().keySet()) {
			// If the message has the shortcut
			if (message.contains(key)) {
				// Find location in string of occurrences of shortcut (going forward)
				for (int i = -1; (i = message.indexOf(key, i + 1)) != -1; i++) {
					// If character before shortcut is not an escape character
					if (i - 1 < 0 || message.charAt(i - 1) != '\\') { // Then replace shortcut with emoji
						replaced.append(message.substring(previousPosition, i)).append(getShortcuts().get(key)); // Add previous text and emoji, but not anything after to prevent replacement issue later
						plugin.getMetricsHandler().addShortcutUsed(1);
						previousPosition = i + key.length();
					} else { // Otherwise remove backslash as it's cancelling an emoji
						replaced.append(message.substring(previousPosition, i - 1)).append(message.substring(i, i + key.length())); // Add previous text and remove backslash, exclude the matched key but not anything after
						plugin.getMetricsHandler().addEscapesUsed(1);
						previousPosition = i + key.length();
					}
				}
			}
			// Reset necessary variables for next shortcut
			replaced.append(message.substring(previousPosition)); // Add remaining text
			previousPosition = 0;
			message = replaced.toString();
			replaced = new StringBuilder();
		}
		return message;
	}
	
	/**
	 * Checks if the specified message contains a disabled character, if enabled.
	 *
	 * @param message The message to check.
	 * @return True if the message contains a disabled character, false otherwise.
	 */
	public boolean containsDisabledCharacter(String message) {
		for (Character disabledCharacter : disabledCharacters) {
			if (message.contains(disabledCharacter.toString())) { // Message contains a disabled character
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the {@link EmojiPackVariant} being used.
	 *
	 * @return The {@link EmojiPackVariant} being used.
	 */
	public EmojiPackVariant getPackVariant() {
		return packVariant;
	}
}
