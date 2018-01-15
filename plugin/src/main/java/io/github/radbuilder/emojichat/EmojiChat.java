package io.github.radbuilder.emojichat;

import io.github.radbuilder.emojichat.hooks.DiscordSrvHook;
import io.github.radbuilder.emojichat.hooks.EmojiChatHook;
import io.github.radbuilder.emojichat.hooks.MVdWPlaceholderApiHook;
import io.github.radbuilder.emojichat.hooks.PlaceholderApiHook;
import io.github.radbuilder.emojichat.hooks.TelegramChatHook;
import io.github.radbuilder.emojichat.metrics.MetricsHandler;
import io.github.radbuilder.emojichat.utils.EmojiChatConfigUpdater;
import io.github.radbuilder.emojichat.utils.EmojiChatUpdateChecker;
import io.github.radbuilder.emojichat.utils.EmojiHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
 * @version 1.7
 * @since 1.0
 */
public class EmojiChat extends JavaPlugin {
	/**
	 * The emoji handler that stores emoji data.
	 */
	private EmojiHandler emojiHandler;
	/**
	 * The EmojiChat GUI.
	 */
	EmojiChatGui emojiChatGui;
	/**
	 * The EmojiChat update checker.
	 */
	EmojiChatUpdateChecker updateChecker;
	/**
	 * List of enabled EmojiChat hooks.
	 */
	private List<EmojiChatHook> enabledHooks;
	/**
	 * The metrics data handler.
	 */
	private MetricsHandler metricsHandler;
	
	@Override
	public void onEnable() {
		if (!new File(getDataFolder(), "config.yml").exists()) { // If there's not a config, make one
			saveDefaultConfig();
		} else {
			new EmojiChatConfigUpdater(this); // If there is a config, see if it can be updated
		}
		
		enabledHooks = new ArrayList<>();
		emojiChatGui = new EmojiChatGui(this);
		updateChecker = new EmojiChatUpdateChecker(this);
		emojiHandler = new EmojiHandler(this);
		
		loadHooks(); // Load plugin hooks
		
		metricsHandler = new MetricsHandler(this); // Creates the metrics handler for metrics gathering
		
		// Register the chat listener
		Bukkit.getPluginManager().registerEvents(new EmojiChatListener(this), this);
		
		// Register the "emojichat" and "ec" commands
		EmojiChatCommand emojiChatCommand = new EmojiChatCommand(this);
		EmojiChatTabComplete emojiChatTabComplete = new EmojiChatTabComplete();
		getCommand("emojichat").setExecutor(emojiChatCommand);
		getCommand("emojichat").setTabCompleter(emojiChatTabComplete);
		getCommand("ec").setExecutor(emojiChatCommand);
		getCommand("ec").setTabCompleter(emojiChatTabComplete);
	}
	
	@Override
	public void onDisable() {
		for (EmojiChatHook hook : enabledHooks) { // Disables enabled hooks, if any
			hook.disable();
		}
		enabledHooks.clear();
		emojiHandler.disable();
		updateChecker.cancelUpdateTask();
	}
	
	/**
	 * Hooks into available plugins.
	 */
	private void loadHooks() {
		if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) { // Hook DiscordSRV if installed
			enabledHooks.add(new DiscordSrvHook(this));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) { // Hook MVdWPlaceholderAPI if installed
			enabledHooks.add(new MVdWPlaceholderApiHook(this));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { // Hook PlaceholderAPI if installed
			enabledHooks.add(new PlaceholderApiHook(this));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("TelegramChat")) { // Hook TelegramChat if installed
			enabledHooks.add(new TelegramChatHook(this));
		}
	}
	
	/**
	 * Gets the list of enabled {@link io.github.radbuilder.emojichat.hooks.EmojiChatHook}.
	 *
	 * @return The list of enabled {@link io.github.radbuilder.emojichat.hooks.EmojiChatHook}.
	 */
	public List<EmojiChatHook> getEnabledHooks() {
		return enabledHooks;
	}
	
	/**
	 * Gets the {@link #metricsHandler}.
	 *
	 * @return The {@link #metricsHandler}.
	 */
	public MetricsHandler getMetricsHandler() {
		return metricsHandler;
	}
	
	/**
	 * Gets the emoji handler.
	 *
	 * @return The emoji handler.
	 */
	public EmojiHandler getEmojiHandler() {
		return emojiHandler;
	}
}
