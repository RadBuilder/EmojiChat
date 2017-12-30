package io.github.radbuilder.emojichat.hooks;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePostProcessEvent;
import github.scarsz.discordsrv.api.events.GameChatMessagePreProcessEvent;
import io.github.radbuilder.emojichat.EmojiChat;
import org.apache.commons.lang.StringUtils;

/**
 * DiscordSRV hook.
 *
 * @author RadBuilder
 * @since 1.4
 * @version 1.5
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
	public void onChatMessageFromInGame(GameChatMessagePreProcessEvent event) { // From in-game to Discord
		if (!event.getPlayer().hasPermission("emojichat.use"))
			return; // Don't do anything if they don't have permission
		
		String message = event.getMessage();
		
		// Replace emojis with shortcuts
		for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
			// Don't count metrics as it's already counted in the normal chat listener
			message = message.replace(plugin.getEmojiHandler().getEmojis().get(key), key);
		}
		
		event.setMessage(message);
	}
	
	@Subscribe(priority = ListenerPriority.MONITOR)
	public void onChatMessageFromDiscord(DiscordGuildMessagePostProcessEvent event) { // From Discord to in-game
		// TODO: Add permission checking for Discord
		String message = event.getProcessedMessage();
		
		// Replace shortcuts with emojis
		for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
			plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(message, key)); // Count metrics
			message = message.replace(key, plugin.getEmojiHandler().getEmojis().get(key));
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
