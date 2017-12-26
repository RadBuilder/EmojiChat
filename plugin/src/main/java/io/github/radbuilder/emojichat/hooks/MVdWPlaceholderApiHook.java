package io.github.radbuilder.emojichat.hooks;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import io.github.radbuilder.emojichat.EmojiChat;

/**
 * MVdWPlaceholderAPI hook.
 *
 * @author RadBuilder
 * @since 1.4
 */
public class MVdWPlaceholderApiHook implements EmojiChatHook {
	/**
	 * If this hook is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Creates the MVdWPlaceholderAPI hook with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public MVdWPlaceholderApiHook(EmojiChat plugin) {
		plugin.getLogger().info("Hooked " + getName());
		
		for (String key : plugin.getEmojiMap().keySet()) {
			PlaceholderAPI.registerStaticPlaceholders(plugin, key, plugin.getEmojiMap().get(key));
		}
		
		enabled = true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public String getName() {
		return "MVdWPlaceholderAPI";
	}
	
	@Override
	public EmojiChatHookType getHookType() {
		return EmojiChatHookType.MVDWPLACEHOLDERAPI;
	}
	
	@Override
	public void disable() {
		enabled = false;
	}
}
