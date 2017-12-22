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
		emojiMap.put(":clown_face:", "갞");
		emojiMap.put(":cowboy_hat_face:", "갟");
		emojiMap.put(":hugs:", "갠");
		emojiMap.put(":smirk:", "갡");
		emojiMap.put(":no_mouth:", "갢");
		emojiMap.put(":neutral_face:", "갣");
		emojiMap.put(":expressionless:", "갤");
		emojiMap.put(":unamused:", "갥");
		emojiMap.put(":roll_eyes:", "갦");
		emojiMap.put(":thinking:", "갧");
		emojiMap.put(":lying_face:", "갨");
		emojiMap.put(":flushed:", "갩");
		emojiMap.put(":disappointed:", "갪");
		emojiMap.put(":worried:", "갫");
		emojiMap.put(":angry:", "갬");
		emojiMap.put(":rage:", "갭");
		emojiMap.put(":pensive:", "갮");
		emojiMap.put(":confused:", "갯");
		emojiMap.put(":slightly_frowning_face:", "갰");
		emojiMap.put(":frowning_face:", "갱");
		emojiMap.put(":persevere:", "갲");
		emojiMap.put(":confounded:", "갳");
		emojiMap.put(":tired_face:", "갴");
		emojiMap.put(":weary:", "갵");
		emojiMap.put(":triumph:", "갶");
		emojiMap.put(":open_mouth:", "갷");
		emojiMap.put(":scream:", "갸");
		emojiMap.put(":fearful:", "갹");
		emojiMap.put(":cold_sweat:", "갺");
		emojiMap.put(":hushed:", "갻");
		emojiMap.put(":frowning:", "갼");
		emojiMap.put(":anguished:", "갽");
		emojiMap.put(":cry:", "갾");
		emojiMap.put(":disappointed_relieved:", "갿");
		emojiMap.put(":drooling_face:", "걀");
		emojiMap.put(":sleepy:", "걁");
		emojiMap.put(":sweat:", "걂");
		emojiMap.put(":sob:", "걃");
		emojiMap.put(":dizzy_face:", "걄");
		emojiMap.put(":astonished:", "걅");
		emojiMap.put(":zipper_mouth_face:", "걆");
		emojiMap.put(":nauseated_face:", "걇");
		emojiMap.put(":sneezing_face:", "걈");
		emojiMap.put(":mask:", "걉");
		emojiMap.put(":face_with_thermometer:", "걊");
		emojiMap.put(":face_with_head_bandage:", "걋");
		emojiMap.put(":sleeping:", "걌");
		emojiMap.put(":zzz:", "걍");
		emojiMap.put(":poop:", "걎");
		emojiMap.put(":smiling_imp:", "걏");
		emojiMap.put(":imp:", "걐");
		emojiMap.put(":japanese_ogre:", "걑");
		emojiMap.put(":japanese_goblin:", "걒");
		emojiMap.put(":skull:", "걓");
		emojiMap.put(":ghost:", "걔");
		emojiMap.put(":alien:", "걕");
		emojiMap.put(":robot:", "걖");
		emojiMap.put(":smiley_cat:", "걗");
		emojiMap.put(":smile_cat:", "걘");
		emojiMap.put(":joy_cat:", "걙");
		emojiMap.put(":heart_eyes_cat:", "걚");
		emojiMap.put(":smirk_cat:", "걛");
		emojiMap.put(":kissing_cat:", "걜");
		emojiMap.put(":scream_cat:", "걝");
		emojiMap.put(":crying_cat_face:", "걞");
		emojiMap.put(":pouting_cat:", "걟");
		emojiMap.put(":raised_hands:", "걠");
		emojiMap.put(":clap:", "걡");
		emojiMap.put(":wave:", "걢");
		emojiMap.put(":call_me_hand:", "걣");
		emojiMap.put(":+1:", "걤");
		emojiMap.put(":-1:", "걥");
		emojiMap.put(":facepunch:", "걦");
		emojiMap.put(":fist:", "걧");
		emojiMap.put(":fist_left:", "걨");
		emojiMap.put(":fist_right:", "걩");
		emojiMap.put(":v:", "걪");
		emojiMap.put(":ok_hand:", "걫");
		emojiMap.put(":raised_hand:", "걬");
		emojiMap.put(":raised_back_of_hand:", "걭");
		emojiMap.put(":open_hands:", "걮");
		emojiMap.put(":muscle:", "걯");
		emojiMap.put(":pray:", "거");
		emojiMap.put(":handshake:", "걱");
		emojiMap.put(":point_up:", "걲");
		emojiMap.put(":point_up_2:", "걳");
		emojiMap.put(":point_down:", "건");
		emojiMap.put(":point_left:", "걵");
		emojiMap.put(":point_right:", "걶");
		// emojiMap.put(":fu:", "걷");
		emojiMap.put(":raised_hand_with_fingers_splayed:", "걸");
		emojiMap.put(":metal:", "걹");
		emojiMap.put(":crossed_fingers:", "걺");
		emojiMap.put(":vulcan_salute:", "걻");
		emojiMap.put(":writing_hand:", "걼");
		emojiMap.put(":selfie:", "걽");
	}
}
