package io.github.radbuilder.emojichat.hooks;

import de.Linus122.TelegramChat.API;
import de.Linus122.TelegramChat.Telegram;
import de.Linus122.TelegramChat.TelegramActionListener;
import de.Linus122.TelegramComponents.Chat;
import de.Linus122.TelegramComponents.ChatMessageToMc;
import de.Linus122.TelegramComponents.ChatMessageToTelegram;
import io.github.radbuilder.emojichat.EmojiChat;
import org.apache.commons.lang.StringUtils;

/**
 * TelegramChat hook.
 *
 * @author RadBuilder
 * @version 1.8.3
 * @since 1.7
 */
public class TelegramChatHook implements EmojiChatHook {
	/**
	 * If this hook is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Creates the DiscordSRV hook with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public TelegramChatHook(EmojiChat plugin) {
		Telegram telegram = API.getTelegramHook();
		telegram.addListener(new TelegramActionListener() {
			@Override
			public void onSendToTelegram(ChatMessageToTelegram chat) {
				// Replace emojis with shortcuts
				for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
					// Don't count metrics as it's already counted in the normal chat listener
					chat.text = chat.text.replace(plugin.getEmojiHandler().getEmojis().get(key).toString(), key);
				}
			}
			
			@Override
			public void onSendToMinecraft(ChatMessageToMc chatMessageToMc) {
				String message = chatMessageToMc.getContent();
				
				// Replace shortcuts with emojis
				for (String key : plugin.getEmojiHandler().getEmojis().keySet()) {
					plugin.getMetricsHandler().addEmojiUsed(StringUtils.countMatches(message, key)); // Count metrics
					message = message.replace(key, plugin.getEmojiHandler().getEmojis().get(key).toString());
				}
				
				chatMessageToMc.setContent(message);
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
		return "TelegramChat";
	}
	
	@Override
	public EmojiChatHookType getHookType() {
		return EmojiChatHookType.TELEGRAMCHAT;
	}
	
	@Override
	public void disable() {
		enabled = false;
	}
}