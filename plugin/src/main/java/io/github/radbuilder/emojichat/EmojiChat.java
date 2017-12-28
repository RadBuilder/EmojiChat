package io.github.radbuilder.emojichat;

import com.google.common.io.BaseEncoding;
import io.github.radbuilder.emojichat.hooks.DiscordSrvHook;
import io.github.radbuilder.emojichat.hooks.EmojiChatHook;
import io.github.radbuilder.emojichat.hooks.MVdWPlaceholderApiHook;
import io.github.radbuilder.emojichat.hooks.PlaceholderApiHook;
//import io.github.radbuilder.emojichat.hooks.TelegramChatHook;
import io.github.radbuilder.emojichat.metrics.MetricsHandler;
import io.github.radbuilder.emojichat.utils.EmojiChatConfigUpdater;
import io.github.radbuilder.emojichat.utils.EmojiChatUpdateChecker;
import io.github.radbuilder.emojichat.utils.EmojiHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
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
	 * The ResourcePack URL.
	 */
	final String PACK_URL = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.4/EmojiChat.ResourcePack.v1.4.zip";
	/**
	 * The SHA1 sum of the ResourcePack as a byte array.
	 */
	byte[] PACK_SHA1;
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
		new EmojiChatConfigUpdater(this);
		
		saveDefaultConfig();
		
		enabledHooks = new ArrayList<>();
		emojiChatGui = new EmojiChatGui(this);
		updateChecker = new EmojiChatUpdateChecker(this);
		emojiHandler = new EmojiHandler(this);
		
		loadHooks(); // Load plugin hooks
		
		metricsHandler = new MetricsHandler(this); // Creates the metrics handler for metrics gathering
		
		PACK_SHA1 = BaseEncoding.base16().lowerCase().decode("c0143b56fb568ec4787320ea1e6af245d8a8140c"); // Allows applying a cached version of the ResourcePack if available
		
		// Register the chat listener
		Bukkit.getPluginManager().registerEvents(new EmojiChatListener(this), this);
		
		// Register the "emojichat" and "ec" commands
		EmojiChatCommand emojiChatCommand = new EmojiChatCommand(this);
		getCommand("emojichat").setExecutor(emojiChatCommand);
		getCommand("ec").setExecutor(emojiChatCommand);
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
//		if (Bukkit.getPluginManager().isPluginEnabled("TelegramChat")) { // Hook TelegramChat if installed
//			enabledHooks.add(new TelegramChatHook(this));
//		}
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
