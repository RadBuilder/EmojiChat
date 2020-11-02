package io.github.radbuilder.emojichat;

import io.github.radbuilder.emojichat.hooks.*;
import io.github.radbuilder.emojichat.metrics.MetricsHandler;
import io.github.radbuilder.emojichat.utils.EmojiChatConfigUpdater;
import io.github.radbuilder.emojichat.utils.EmojiChatUpdateChecker;
import io.github.radbuilder.emojichat.utils.EmojiHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
 * @version 1.8.3
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
		setupCommand();
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
		if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV-Staff-Chat")) { // Hook DiscordSRV Staff Chat if installed
			DiscordSrvStaffChatHook discordSrvStaffChatHook = new DiscordSrvStaffChatHook(this);
			enabledHooks.add(discordSrvStaffChatHook);
			Bukkit.getPluginManager().registerEvents(discordSrvStaffChatHook, this);
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
	 * Setting up command and aliases
	 */
	private void setupCommand() {
		// get the aliases from the config
		List<String> aliases = getConfig().getStringList("command-aliases");
		CommandMap commandMap = null;

		try {
			// constructor the new command
			Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			constructor.setAccessible(true);

			PluginCommand pluginCommand = constructor.newInstance("emojichat", this);

			// get the commandMap
			Field field = SimplePluginManager.class.getDeclaredField("commandMap");
			field.setAccessible(true);

			commandMap = (CommandMap) field.get(getServer().getPluginManager());

			// set aliases, executor, description, tab completer and usage for pluginCommand
			EmojiChatCommand emojiChatExecutor = new EmojiChatCommand(this);
			EmojiChatTabComplete emojiChatTabComplete = new EmojiChatTabComplete();

			pluginCommand.setAliases(aliases);
			pluginCommand.setExecutor(emojiChatExecutor);
			pluginCommand.setTabCompleter(emojiChatTabComplete);
			pluginCommand.setDescription("The main EmojiChat command");
			pluginCommand.setUsage("/<command>");

			// register to the command map
			commandMap.register("emojichat", pluginCommand);

		} catch(NoSuchMethodException | NoSuchFieldException | IllegalAccessException |
				InvocationTargetException | InstantiationException ex)
		{
			ex.printStackTrace();
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
