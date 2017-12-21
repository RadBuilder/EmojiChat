package io.github.radbuilder.emojichat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
 * @since 1.0
 */
public class EmojiChat extends JavaPlugin {
	/**
	 * Stores the shortcuts and the emojis with the shortcut as the key and emoji as the value.
	 */
	HashMap<String, String> emojiMap;
	/**
	 * The ResourcePack URL.
	 */
	final String PACK_URL = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.1/EmojiChat.ResourcePack.v1.1.zip";
	
	@Override
	public void onEnable() {
		emojiMap = new HashMap<>();
		
		loadList();
		
		// Register the chat listener
		Bukkit.getPluginManager().registerEvents(new EmojiChatListener(this), this);
		
		// Register the "emojichat" and "ec" commands
		EmojiChatCommand emojiChatCommand = new EmojiChatCommand(this);
		getCommand("emojichat").setExecutor(emojiChatCommand);
		getCommand("ec").setExecutor(emojiChatCommand);
	}
	
	/**
	 * Loads the emojis and their shortcuts into the {@link #emojiMap}.
	 */
	private void loadList() {
		emojiMap.put(":100:", "가");
		emojiMap.put(":1234:", "각");
		emojiMap.put(":grinning:", "갂");
		emojiMap.put(":grimacing:", "갃");
		emojiMap.put(":grin:", "간");
		emojiMap.put(":joy:", "갅");
		emojiMap.put(":rofl:", "갆");
		emojiMap.put(":smiley:", "갇");
		emojiMap.put(":smile:", "갈");
		emojiMap.put(":sweat_smile:", "갉");
		emojiMap.put(":laughing:", "갊");
		emojiMap.put(":innocent:", "갋");
		emojiMap.put(":wink:", "갌");
		emojiMap.put(":blush:", "갍");
		emojiMap.put(":slightly_smiling_face:", "갎");
		emojiMap.put(":upside_down_face:", "갏");
		emojiMap.put(":relaxed:", "감");
		emojiMap.put(":yum:", "갑");
		emojiMap.put(":relieved:", "값");
		emojiMap.put(":heart_eyes:", "갓");
		emojiMap.put(":kissing_heart:", "갔");
		emojiMap.put(":kissing:", "강");
		emojiMap.put(":kissing_smiling_eyes:", "갖");
		emojiMap.put(":kissing_closed_eyes:", "갗");
		emojiMap.put(":stuck_out_tongue_winking_eye:", "갘");
		emojiMap.put(":stuck_out_tongue_closed_eyes:", "같");
		emojiMap.put(":stuck_out_tongue:", "갚");
		emojiMap.put(":money_mouth_face:", "갛");
		emojiMap.put(":nerd_face:", "개");
		emojiMap.put(":sunglasses:", "객");
	}
}
