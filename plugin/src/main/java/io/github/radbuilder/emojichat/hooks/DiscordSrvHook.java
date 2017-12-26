package io.github.radbuilder.emojichat.hooks;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePostProcessEvent;
import github.scarsz.discordsrv.api.events.GameChatMessagePreProcessEvent;
import io.github.radbuilder.emojichat.EmojiChat;

/**
 * DiscordSRV hook.
 *
 * @author RadBuilder
 * @since 1.4
 */
public class DiscordSrvHook implements EmojiChatHook {
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	/**
	 * If this hook is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Creates the DiscordSRV hook with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public DiscordSrvHook(EmojiChat plugin) {
		this.plugin = plugin;
		DiscordSRV.api.subscribe(this); // Registers this class with the DiscordSRV api
		plugin.getLogger().info("Hooked " + getName());
		enabled = true;
	}
	
	@Subscribe(priority = ListenerPriority.MONITOR)
	public void onChatMessageFromInGame(GameChatMessagePreProcessEvent event) {
		if (!event.getPlayer().hasPermission("emojichat.use"))
			return; // Don't do anything if they don't have permission
		
		String message = event.getMessage();
		
		// Replace emojis with shortcuts
		for (String key : plugin.getEmojiMap().keySet()) {
			message = message.replace(plugin.getEmojiMap().get(key), key);
		}
		
		event.setMessage(message);
	}
	
	@Subscribe(priority = ListenerPriority.MONITOR)
	public void onChatMessageFromDiscord(DiscordGuildMessagePostProcessEvent event) {
		// TODO: Add permission checking for Discord
		String message = event.getProcessedMessage();
		
		// Replace shortcuts with emojis
		for (String key : plugin.getEmojiMap().keySet()) {
			message = message.replace(key, plugin.getEmojiMap().get(key));
		}
		
		event.setProcessedMessage(message);
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public String getName() {
		return "DiscordSRV";
	}
	
	@Override
	public EmojiChatHookType getHookType() {
		return EmojiChatHookType.DISCORDSRV;
	}
	
	@Override
	public void disable() {
		enabled = false;
		DiscordSRV.api.unsubscribe(this);
	}
}
