package io.github.radbuilder.emojichat.hooks;

import com.rezzedup.discordsrv.staffchat.events.DiscordStaffChatMessageEvent;
import com.rezzedup.discordsrv.staffchat.events.PlayerStaffChatMessageEvent;
import com.vdurmont.emoji.EmojiParser;
import io.github.radbuilder.emojichat.EmojiChat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * DiscordSRV Staff Chat hook.
 *
 * @author RadBuilder
 * @version 1.8.3
 * @since 1.8.3
 */
public class DiscordSrvStaffChatHook implements EmojiChatHook, Listener {
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	/**
	 * If this hook is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Creates the DiscordSRV Staff Chat hook with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public DiscordSrvStaffChatHook(EmojiChat plugin) {
		this.plugin = plugin;
		plugin.getLogger().info("Hooked " + getName());
		enabled = true;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDiscordStaffChat(DiscordStaffChatMessageEvent event) {
		// TODO: Add permission checking for Discord
		String message = EmojiParser.parseToAliases(event.getText()); // Call emoji translation before staff chat plugin does
		
		// Replace shortcuts with emojis
		for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
			plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(message, key)); // Count metrics
			message = message.replace(key, plugin.getEmojiHandler().getEmojis().get(key).toString());
		}
		event.setText(message);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMinecraftStaffChat(PlayerStaffChatMessageEvent event) {
		if (!event.getAuthor().hasPermission("emojichat.use"))
			return; // Don't do anything if they don't have permission
		
		String message = event.getText();
		
		// Checks if the user disabled shortcuts via /emojichat toggle
		if (!plugin.getEmojiHandler().hasShortcutsOff(event.getAuthor())) {
			message = plugin.getEmojiHandler().translateShorthand(message);
		}
		
		// Replace shortcuts with emojis
		for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
			// Metrics counted later
			message = message.replace(key, plugin.getEmojiHandler().getEmojis().get(key).toString());
		}
		
		// If the message contains a disabled character
		if (plugin.getEmojiHandler().containsDisabledCharacter(message)) {
			event.setCancelled(true);
			event.getAuthor().sendMessage(ChatColor.RED + "Oops! You can't use disabled emoji characters!");
			return;
		}
		
		event.setText(message); // Calls DiscordGuildMessagePostProcessEvent
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public String getName() {
		return "DiscordSRVStaffChat";
	}
	
	@Override
	public EmojiChatHookType getHookType() {
		return EmojiChatHookType.DISCORDSRVSTAFFCHAT;
	}
	
	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
		enabled = false;
	}
}
