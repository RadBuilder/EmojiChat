package io.github.radbuilder.emojichat.hooks;

import io.github.radbuilder.emojichat.EmojiChat;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

/**
 * PlaceholderAPI hook.
 *
 * @author RadBuilder
 * @version 1.7
 * @since 1.4
 */
public class PlaceholderApiHook implements EmojiChatHook {
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	/**
	 * If this hook is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Creates the PlaceholderAPI hook with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public PlaceholderApiHook(EmojiChat plugin) {
		this.plugin = plugin;
		PlaceholderAPI.registerPlaceholderHook(plugin, new PlaceholderHook() {
			@Override
			public String onPlaceholderRequest(Player player, String identifier) {
				if (player != null && !player.hasPermission("emojichat.use")) // Make sure the player, if specified, has permission to use emojis
					return "No Permission";
				if (plugin.getEmojiHandler().getEmojis().containsKey(":" + identifier + ":")) {
					return plugin.getEmojiHandler().getEmojis().get(":" + identifier + ":").toString();
				}
				return null;
			}
		});
		
		plugin.getLogger().info("Hooked " + getName());
		enabled = true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public String getName() {
		return "PlaceholderAPI";
	}
	
	@Override
	public EmojiChatHookType getHookType() {
		return EmojiChatHookType.PLACEHOLDERAPI;
	}
	
	@Override
	public void disable() {
		enabled = false;
		PlaceholderAPI.unregisterPlaceholderHook(plugin);
	}
}
