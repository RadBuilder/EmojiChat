package io.github.radbuilder.emojichat.metrics;

import io.github.radbuilder.emojichat.EmojiChat;
import io.github.radbuilder.emojichat.hooks.EmojiChatHook;

import java.util.HashMap;
import java.util.Map;

/**
 * Metrics handler class.
 *
 * @author RadBuilder
 * @version 1.8.3
 * @since 1.4
 */
public class MetricsHandler {
	/**
	 * The number of emojis used.
	 */
	private int emojisUsed;
	/**
	 * The number of shortcuts used.
	 */
	private int shortcutsUsed;
	/**
	 * The number of escapes used.
	 */
	private int escapesUsed;
	/**
	 * The {@link MetricsLevel} being used.
	 */
	private MetricsLevel metricsLevel;
	
	/**
	 * Creates the metrics handler class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public MetricsHandler(EmojiChat plugin) {
		emojisUsed = 0;
		shortcutsUsed = 0;
		escapesUsed = 0;
		
		try {
			metricsLevel = MetricsLevel.valueOf(plugin.getConfig().getString("metrics-collection"));
		} catch (Exception e) { // If metrics-collection is invalid, set to the default option
			metricsLevel = MetricsLevel.FULL;
		}
		
		if (metricsLevel == MetricsLevel.OFF) {
			return; // Don't start metrics if off
		}
		
		Metrics metrics = new Metrics(plugin); // Start Metrics
		
		switch (metricsLevel) {
			case FULL:
				// If fix-emoji-coloring is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingFixEmojiColoring", () -> Boolean.toString(plugin.getConfig().getBoolean("fix-emoji-coloring"))));
				
				// If disable-emojis is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingDisableEmojis", () -> Boolean.toString(plugin.getConfig().getBoolean("disable-emojis"))));
				
				// If download-resourcepack is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingDownloadResourcePack", () -> Boolean.toString(plugin.getConfig().getBoolean("download-resourcepack"))));
				
				// If emojis-on-signs is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingEmojisOnSigns", () -> Boolean.toString(plugin.getConfig().getBoolean("emojis-on-signs"))));
				
				// If emojis-in-commands is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingEmojisInCommands", () -> Boolean.toString(plugin.getConfig().getBoolean("emojis-in-commands"))));
				
				// If only-command-list is being used
				metrics.addCustomChart(new Metrics.SimplePie("usingOnlyCommandList", () -> Boolean.toString(plugin.getConfig().getBoolean("only-command-list"))));
				
				// What commands are listed under command-list, if any
				metrics.addCustomChart(new Metrics.AdvancedPie("commandList", () -> {
					Map<String, Integer> commandList = new HashMap<>();
					for (String command : plugin.getConfig().getStringList("command-list")) {
						commandList.put(command.toLowerCase(), 1);
					}
					if (commandList.isEmpty()) {
						commandList.put("None", 1);
					}
					return commandList;
				}));
				
				// What emojis are listed under disabled-emojis, if any
				metrics.addCustomChart(new Metrics.AdvancedPie("disabledEmojis", () -> {
					Map<String, Integer> disabledEmojis = new HashMap<>();
					if (!plugin.getConfig().getBoolean("disable-emojis") || plugin.getConfig().getStringList("disabled-emojis").isEmpty()) { // If there aren't any disabled emojis, add "None"
						disabledEmojis.put("None", 1);
					} else {
						plugin.getConfig().getStringList("disabled-emojis").forEach(s -> disabledEmojis.put(s, 1));
					}
					return disabledEmojis;
				}));
			case SOME:
				// Which hooks are being used (value of 1 per hook, or "None" if no hooks)
				metrics.addCustomChart(new Metrics.AdvancedPie("usedHooks", () -> {
					Map<String, Integer> usedHooksMap = new HashMap<>();
					if (plugin.getEnabledHooks().size() < 1) {
						usedHooksMap.put("None", 1);
					} else {
						for (EmojiChatHook hook : plugin.getEnabledHooks()) {
							usedHooksMap.put(hook.getName(), 1);
						}
					}
					return usedHooksMap;
				}));
			case BASIC:
				// The number of emojis used
				metrics.addCustomChart(new Metrics.SingleLineChart("emojisUsed", () -> {
					int temp = emojisUsed;
					emojisUsed = 0; // Reset the number of emojis used when this is called
					return temp;
				}));
				
				// The number of shortcuts used
				metrics.addCustomChart(new Metrics.SingleLineChart("shortcutsUsed", () -> {
					int temp = shortcutsUsed;
					shortcutsUsed = 0; // Reset the number of shortcuts used when this is called
					return temp;
				}));

				// The number of escapes used
				metrics.addCustomChart(new Metrics.SingleLineChart("escapesUsed", () -> {
					int temp = escapesUsed;
					escapesUsed = 0; // Reset the number of escapes used when this is called
					return temp;
				}));

				// Which pack variant is being used
				metrics.addCustomChart(new Metrics.SimplePie("packVariant", () -> String.valueOf(plugin.getConfig().getInt("pack-variant"))));
				
				// Which pack quality is being used
				metrics.addCustomChart(new Metrics.SimplePie("packQuality", () -> plugin.getConfig().getString("pack-quality")));
			default:
				metrics.addCustomChart(new Metrics.SimplePie("metricsCollection", () -> metricsLevel.name().toLowerCase()));
				break;
		}
	}
	
	/**
	 * Adds the specified number of emojis used to {@link #emojisUsed}.
	 *
	 * @param emojisUsed The number of emojis used to add to {@link #emojisUsed}.
	 */
	public void addEmojiUsed(int emojisUsed) {
		this.emojisUsed += emojisUsed;
	}
	
	/**
	 * Adds the specified number of shortcuts used to {@link #shortcutsUsed}.
	 *
	 * @param shortcutsUsed The number of shortcuts used to add to {@link #shortcutsUsed}.
	 */
	public void addShortcutUsed(int shortcutsUsed) {
		this.shortcutsUsed += shortcutsUsed;
	}

	/**
	 * Adds the specified number of escapes used to {@link #escapesUsed}.
	 *
	 * @param escapesUsed The number of escapes used to add to {@link #escapesUsed}.
	 */
    public void addEscapesUsed(int escapesUsed) {
    	this.escapesUsed += escapesUsed;
    }
}

/**
 * The different levels of metrics data collection.
 *
 * @author RadBuilder
 * @version 1.5
 * @since 1.5
 */
enum MetricsLevel {
	/**
	 * Collects what {@link #SOME} collects, and data on what config options you're using, and what hooks you're using.
	 */
	FULL,
	/**
	 * Collects what {@link #BASIC} collects, and data on which hooks ({@link io.github.radbuilder.emojichat.hooks.EmojiChatHook}) you're using.
	 */
	SOME,
	/**
	 * Collects data on what Java version you're using, Bukkit/Spigot version you're using, other general server
	 * information like player count, how many emojis you've used, and shortcuts you've used.
	 */
	BASIC,
	/**
	 * Collects NO DATA, however, I would appreciate it if you send at least {@link #BASIC} data.
	 */
	OFF;
}