package io.github.radbuilder.emojichat.hooks;

import io.github.radbuilder.emojichat.EmojiChat;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderApiHook implements EmojiChatHook {
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
		PlaceholderAPI.registerPlaceholderHook(plugin, new PlaceholderHook() {
			@Override
			public String onPlaceholderRequest(Player player, String identifier) {
				if (player != null && !player.hasPermission("emojichat.use")) // Make sure the player, if specified, has permission to use emojis
					return "No Permission";
				if (plugin.getEmojiMap().containsKey(identifier)) {
					return plugin.getEmojiMap().get(identifier);
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
}
