package io.github.radbuilder.emojichat;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Emoji handler class.
 *
 * @author RadBuilder
 * @since 1.4
 */
public class EmojiHandler {
	/**
	 * The emojis.
	 */
	private TreeMap<String, String> emojis;
	/**
	 * Shortcuts for the emojis, if specified.
	 */
	private HashMap<String, String> shortcuts;
	/**
	 * Disabled emoji characters to prevent others from using them with the resource pack.
	 */
	private List<String> disabledCharacters;
	/**
	 * If we should fix the emoji's color (colored chat removes emoji coloring)
	 */
	private boolean fixColoring;
	/**
	 * If the characters associated with disabled emojis should be checked.
	 */
	private boolean verifyDisabledList;
	
	/**
	 * Creates the emoji handler with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiHandler(EmojiChat plugin) {
		emojis = new TreeMap<>();
		shortcuts = new HashMap<>();
		disabledCharacters = new ArrayList<>();
		
		loadEmojis(); // Loads ALL emojis
		
		if (!validateConfig(plugin.getConfig())) { // Make sure the config is valid
			plugin.getLogger().warning("Your config is invalid. No configuation data was loaded.");
			plugin.getLogger().warning("Fix your config, then use /emojichat reload");
			plugin.getLogger().warning("If you're still running into issues after fixing your config, delete it and restart your server.");
		} else { // Config is valid, load config data
			loadShortcuts(plugin.getConfig()); // Loads all of the shortcuts specified in the config
			loadDisabledEmojis(plugin.getConfig()); // Loads all of the disabled emojis specified in the config.
			fixColoring = plugin.getConfig().getBoolean("fix-emoji-coloring");
			verifyDisabledList = plugin.getConfig().getBoolean("verify-disabled-list");
			if (!verifyDisabledList) {
				disabledCharacters.clear();
			}
		}
	}
	
	/**
	 * Gets the {@link #emojis} map.
	 *
	 * @return {@link #emojis}.
	 */
	public TreeMap<String, String> getEmojis() {
		return emojis;
	}
	
	/**
	 * Gets the {@link #disabledCharacters} list.
	 *
	 * @return The {@link #disabledCharacters} list.
	 */
	public List<String> getDisabledCharacters() {
		return disabledCharacters;
	}
	
	/**
	 * Gets the {@link #shortcuts} map.
	 *
	 * @return The {@link #shortcuts} map.
	 */
	public HashMap<String, String> getShortcuts() {
		return shortcuts;
	}
	
	/**
	 * If the list of disabled emojis specified in the config should be verified.
	 *
	 * @return True if the disabled list should be verified, false otherwise.
	 */
	public boolean verifyDisabledList() {
		return verifyDisabledList;
	}
	
	/**
	 * Validates the config.
	 *
	 * @param config The config to validate.
	 * @return True if the config is valid, false otherwise.
	 */
	private boolean validateConfig(FileConfiguration config) {
		try {
			return config.get("shortcuts") != null && config.get("disabled-emojis") != null
					&& config.get("fix-emoji-coloring") != null && config.get("verify-disabled-list") != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Loads the emoji shortcuts from the config.
	 *
	 * @param config The config to load emoji shortcuts from.
	 */
	private void loadShortcuts(FileConfiguration config) {
		for (String key : config.getConfigurationSection("shortcuts").getKeys(false)) { // Gets all of the headers/keys in the shortcuts section
			for (String shortcutListItem : config.getStringList("shortcuts." + key)) { // Gets all of the shortcuts for the key
				shortcuts.put(shortcutListItem, ":" + key + ":");
			}
		}
	}
	
	/**
	 * Loads the disabled emojis from the config.
	 *
	 * @param config The config to load disabled emojis from.
	 */
	private void loadDisabledEmojis(FileConfiguration config) {
		for (String disabledEmoji : config.getStringList("disabled-emojis")) {
			disabledCharacters.add(emojis.remove(disabledEmoji)); // Remove disabled emojis from the emoji list
		}
	}
	
	/**
	 * If emoji coloring should be fixed.
	 *
	 * @return True if emoji coloring should be fixed, false otherwise.
	 */
	boolean fixColoring() {
		return fixColoring;
	}
	
	/**
	 * Loads the emojis and their shortcuts into the {@link #emojis}.
	 */
	private void loadEmojis() {
		emojis.put(":100:", "가");
		emojis.put(":1234:", "각");
		emojis.put(":grinning:", "갂");
		emojis.put(":grimacing:", "갃");
		emojis.put(":grin:", "간");
		emojis.put(":joy:", "갅");
		emojis.put(":rofl:", "갆");
		emojis.put(":smiley:", "갇");
		emojis.put(":smile:", "갈");
		emojis.put(":sweat_smile:", "갉");
		emojis.put(":laughing:", "갊");
		emojis.put(":innocent:", "갋");
		emojis.put(":wink:", "갌");
		emojis.put(":blush:", "갍");
		emojis.put(":slightly_smiling_face:", "갎");
		emojis.put(":upside_down_face:", "갏");
		emojis.put(":relaxed:", "감");
		emojis.put(":yum:", "갑");
		emojis.put(":relieved:", "값");
		emojis.put(":heart_eyes:", "갓");
		emojis.put(":kissing_heart:", "갔");
		emojis.put(":kissing:", "강");
		emojis.put(":kissing_smiling_eyes:", "갖");
		emojis.put(":kissing_closed_eyes:", "갗");
		emojis.put(":stuck_out_tongue_winking_eye:", "갘");
		emojis.put(":stuck_out_tongue_closed_eyes:", "같");
		emojis.put(":stuck_out_tongue:", "갚");
		emojis.put(":money_mouth_face:", "갛");
		emojis.put(":nerd_face:", "개");
		emojis.put(":sunglasses:", "객");
		emojis.put(":clown_face:", "갞");
		emojis.put(":cowboy_hat_face:", "갟");
		emojis.put(":hugs:", "갠");
		emojis.put(":smirk:", "갡");
		emojis.put(":no_mouth:", "갢");
		emojis.put(":neutral_face:", "갣");
		emojis.put(":expressionless:", "갤");
		emojis.put(":unamused:", "갥");
		emojis.put(":roll_eyes:", "갦");
		emojis.put(":thinking:", "갧");
		emojis.put(":lying_face:", "갨");
		emojis.put(":flushed:", "갩");
		emojis.put(":disappointed:", "갪");
		emojis.put(":worried:", "갫");
		emojis.put(":angry:", "갬");
		emojis.put(":rage:", "갭");
		emojis.put(":pensive:", "갮");
		emojis.put(":confused:", "갯");
		emojis.put(":slightly_frowning_face:", "갰");
		emojis.put(":frowning_face:", "갱");
		emojis.put(":persevere:", "갲");
		emojis.put(":confounded:", "갳");
		emojis.put(":tired_face:", "갴");
		emojis.put(":weary:", "갵");
		emojis.put(":triumph:", "갶");
		emojis.put(":open_mouth:", "갷");
		emojis.put(":scream:", "갸");
		emojis.put(":fearful:", "갹");
		emojis.put(":cold_sweat:", "갺");
		emojis.put(":hushed:", "갻");
		emojis.put(":frowning:", "갼");
		emojis.put(":anguished:", "갽");
		emojis.put(":cry:", "갾");
		emojis.put(":disappointed_relieved:", "갿");
		emojis.put(":drooling_face:", "걀");
		emojis.put(":sleepy:", "걁");
		emojis.put(":sweat:", "걂");
		emojis.put(":sob:", "걃");
		emojis.put(":dizzy_face:", "걄");
		emojis.put(":astonished:", "걅");
		emojis.put(":zipper_mouth_face:", "걆");
		emojis.put(":nauseated_face:", "걇");
		emojis.put(":sneezing_face:", "걈");
		emojis.put(":mask:", "걉");
		emojis.put(":face_with_thermometer:", "걊");
		emojis.put(":face_with_head_bandage:", "걋");
		emojis.put(":sleeping:", "걌");
		emojis.put(":zzz:", "걍");
		emojis.put(":poop:", "걎");
		emojis.put(":smiling_imp:", "걏");
		emojis.put(":imp:", "걐");
		emojis.put(":japanese_ogre:", "걑");
		emojis.put(":japanese_goblin:", "걒");
		emojis.put(":skull:", "걓");
		emojis.put(":ghost:", "걔");
		emojis.put(":alien:", "걕");
		emojis.put(":robot:", "걖");
		emojis.put(":smiley_cat:", "걗");
		emojis.put(":smile_cat:", "걘");
		emojis.put(":joy_cat:", "걙");
		emojis.put(":heart_eyes_cat:", "걚");
		emojis.put(":smirk_cat:", "걛");
		emojis.put(":kissing_cat:", "걜");
		emojis.put(":scream_cat:", "걝");
		emojis.put(":crying_cat_face:", "걞");
		emojis.put(":pouting_cat:", "걟");
		emojis.put(":raised_hands:", "걠");
		emojis.put(":clap:", "걡");
		emojis.put(":wave:", "걢");
		emojis.put(":call_me_hand:", "걣");
		emojis.put(":+1:", "걤");
		emojis.put(":-1:", "걥");
		emojis.put(":facepunch:", "걦");
		emojis.put(":fist:", "걧");
		emojis.put(":fist_left:", "걨");
		emojis.put(":fist_right:", "걩");
		emojis.put(":v:", "걪");
		emojis.put(":ok_hand:", "걫");
		emojis.put(":raised_hand:", "걬");
		emojis.put(":raised_back_of_hand:", "걭");
		emojis.put(":open_hands:", "걮");
		emojis.put(":muscle:", "걯");
		emojis.put(":pray:", "거");
		emojis.put(":handshake:", "걱");
		emojis.put(":point_up:", "걲");
		emojis.put(":point_up_2:", "걳");
		emojis.put(":point_down:", "건");
		emojis.put(":point_left:", "걵");
		emojis.put(":point_right:", "걶");
		emojis.put(":fu:", "걷");
		emojis.put(":raised_hand_with_fingers_splayed:", "걸");
		emojis.put(":metal:", "걹");
		emojis.put(":crossed_fingers:", "걺");
		emojis.put(":vulcan_salute:", "걻");
		emojis.put(":writing_hand:", "걼");
		emojis.put(":selfie:", "걽");
		emojis.put(":lips:", "걾");
		emojis.put(":tongue:", "걿");
		emojis.put(":ear:", "검");
		emojis.put(":nose:", "겁");
		emojis.put(":eye:", "겂");
		emojis.put(":eyes:", "것");
		emojis.put(":womans_clothes:", "겄");
		emojis.put(":tshirt:", "겅");
		emojis.put(":jeans:", "겆");
		emojis.put(":necktie:", "겇");
		emojis.put(":dress:", "겈");
		emojis.put(":bikini:", "겉");
		emojis.put(":kimono:", "겊");
		emojis.put(":lipstick:", "겋");
		emojis.put(":kiss:", "게");
		emojis.put(":footprints:", "겍");
		emojis.put(":high_heel:", "겎");
		emojis.put(":sandal:", "겏");
		emojis.put(":boot:", "겐");
		emojis.put(":mans_shoe:", "겑");
		emojis.put(":athletic_shoe:", "겒");
		emojis.put(":womans_hat:", "겓");
		emojis.put(":tophat:", "겔");
		emojis.put(":rescue_worker_helmet:", "겕");
		emojis.put(":mortar_board:", "겖");
		emojis.put(":crown:", "겗");
		emojis.put(":school_satchel:", "겘");
		emojis.put(":pouch:", "겙");
		emojis.put(":purse:", "겚");
		emojis.put(":handbag:", "겛");
		emojis.put(":briefcase:", "겜");
		emojis.put(":eyeglasses:", "겝");
		emojis.put(":dark_sunglasses:", "겞");
		emojis.put(":ring:", "겟");
		emojis.put(":closed_umbrella:", "겠");
		emojis.put(":dog:", "겡");
		emojis.put(":cat:", "겢");
		emojis.put(":mouse:", "겣");
		emojis.put(":hamster:", "겤");
		emojis.put(":rabbit:", "겥");
		emojis.put(":fox_face:", "겦");
		emojis.put(":bear:", "겧");
		emojis.put(":panda_face:", "겨");
		emojis.put(":koala:", "격");
		emojis.put(":tiger:", "겪");
		emojis.put(":lion:", "겫");
		emojis.put(":cow:", "견");
		emojis.put(":pig:", "겭");
		emojis.put(":pig_nose:", "겮");
		emojis.put(":frog:", "겯");
		emojis.put(":squid:", "결");
		emojis.put(":octopus:", "겱");
		emojis.put(":shrimp:", "겲");
		emojis.put(":monkey_face:", "겳");
		emojis.put(":gorilla:", "겴");
		emojis.put(":see_no_evil:", "겵");
		emojis.put(":hear_no_evil:", "겶");
		emojis.put(":speak_no_evil:", "겷");
		emojis.put(":monkey:", "겸");
		emojis.put(":chicken:", "겹");
		emojis.put(":penguin:", "겺");
		emojis.put(":bird:", "겻");
		emojis.put(":baby_chick:", "겼");
		emojis.put(":hatching_chick:", "경");
		emojis.put(":hatched_chick:", "겾");
		emojis.put(":duck:", "겿");
		emojis.put(":eagle:", "곀");
		emojis.put(":owl:", "곁");
		emojis.put(":bat:", "곂");
		emojis.put(":wolf:", "곃");
		emojis.put(":boar:", "계");
		emojis.put(":horse:", "곅");
		emojis.put(":unicorn:", "곆");
		emojis.put(":honeybee:", "곇");
		emojis.put(":bug:", "곈");
		emojis.put(":butterfly:", "곉");
		emojis.put(":snail:", "곊");
		emojis.put(":beetle:", "곋");
		emojis.put(":ant:", "곌");
		emojis.put(":spider:", "곍");
		emojis.put(":scorpion:", "곎");
		emojis.put(":crab:", "곏");
		emojis.put(":snake:", "곐");
		emojis.put(":lizard:", "곑");
		emojis.put(":turtle:", "곒");
		emojis.put(":tropical_fish:", "곓");
		emojis.put(":fish:", "곔");
		emojis.put(":blowfish:", "곕");
		emojis.put(":dolphin:", "곖");
		emojis.put(":shark:", "곗");
		emojis.put(":whale:", "곘");
		emojis.put(":whale2:", "곙");
		emojis.put(":crocodile:", "곚");
		emojis.put(":leopard:", "곛");
		emojis.put(":tiger2:", "곜");
		emojis.put(":water_buffalo:", "곝");
		emojis.put(":ox:", "곞");
		emojis.put(":cow2:", "곟");
		emojis.put(":deer:", "고");
		emojis.put(":dromedary_camel:", "곡");
		emojis.put(":camel:", "곢");
		emojis.put(":elephant:", "곣");
		emojis.put(":rhinoceros:", "곤");
		emojis.put(":goat:", "곥");
		emojis.put(":ram:", "곦");
		emojis.put(":sheep:", "곧");
		emojis.put(":racehorse:", "골");
		emojis.put(":pig2:", "곩");
		emojis.put(":rat:", "곪");
		emojis.put(":mouse2:", "곫");
		emojis.put(":rooster:", "곬");
		emojis.put(":turkey:", "곭");
		emojis.put(":dove:", "곮");
		emojis.put(":dog2:", "곯");
		emojis.put(":poodle:", "곰");
		emojis.put(":cat2:", "곱");
		emojis.put(":rabbit2:", "곲");
		emojis.put(":chipmunk:", "곳");
		emojis.put(":paw_prints:", "곴");
		emojis.put(":dragon:", "공");
		emojis.put(":dragon_face:", "곶");
		emojis.put(":cactus:", "곷");
		emojis.put(":christmas_tree:", "곸");
		emojis.put(":evergreen_tree:", "곹");
		emojis.put(":deciduous_tree:", "곺");
		emojis.put(":palm_tree:", "곻");
		emojis.put(":seedling:", "과");
		emojis.put(":herb:", "곽");
		emojis.put(":shamrock:", "곾");
		emojis.put(":four_leaf_clover:", "곿");
		emojis.put(":bamboo:", "관");
		emojis.put(":tanabata_tree:", "괁");
		emojis.put(":leaves:", "괂");
		emojis.put(":fallen_leaf:", "괃");
		emojis.put(":maple_leaf:", "괄");
		emojis.put(":ear_of_rice:", "괅");
		emojis.put(":hibiscus:", "괆");
		emojis.put(":sunflower:", "괇");
		emojis.put(":rose:", "괈");
		emojis.put(":wilted_flower:", "괉");
		emojis.put(":tulip:", "괊");
		emojis.put(":blossom:", "괋");
		emojis.put(":cherry_blossom:", "괌");
		emojis.put(":bouquet:", "괍");
		emojis.put(":mushroom:", "괎");
		emojis.put(":chestnut:", "괏");
		emojis.put(":jack_o_lantern:", "괐");
		emojis.put(":shell:", "광");
		emojis.put(":spider_web:", "괒");
		emojis.put(":earth_americas:", "괓");
		emojis.put(":earth_africa:", "괔");
		emojis.put(":earth_asia:", "괕");
		emojis.put(":full_moon:", "괖");
		emojis.put(":waning_gibbous_moon:", "괗");
		emojis.put(":last_quarter_moon:", "괘");
		emojis.put(":waning_crescent_moon:", "괙");
		emojis.put(":new_moon:", "괚");
		emojis.put(":waxing_crescent_moon:", "괛");
		emojis.put(":first_quarter_moon:", "괜");
		emojis.put(":waxing_gibbous_moon:", "괝");
		emojis.put(":new_moon_with_face:", "괞");
		emojis.put(":full_moon_with_face:", "괟");
		emojis.put(":first_quarter_moon_with_face:", "괠");
		emojis.put(":last_quarter_moon_with_face:", "괡");
		emojis.put(":sun_with_face:", "괢");
		emojis.put(":crescent_moon:", "괣");
		emojis.put(":star:", "괤");
		emojis.put(":star2:", "괥");
		emojis.put(":dizzy:", "괦");
		emojis.put(":sparkles:", "괧");
		emojis.put(":comet:", "괨");
		emojis.put(":sunny:", "괩");
		emojis.put(":sun_behind_small_cloud:", "괪");
		emojis.put(":partly_sunny:", "괫");
		emojis.put(":sun_behind_large_cloud:", "괬");
		emojis.put(":sun_behind_rain_cloud:", "괭");
		emojis.put(":cloud:", "괮");
		emojis.put(":cloud_with_rain:", "괯");
		emojis.put(":cloud_with_lightning_and_rain:", "괰");
		emojis.put(":cloud_with_lightning:", "괱");
		emojis.put(":zap:", "괲");
		emojis.put(":fire:", "괳");
		emojis.put(":boom:", "괴");
		emojis.put(":snowflake:", "괵");
		emojis.put(":cloud_with_snow:", "괶");
		emojis.put(":snowman:", "괷");
		emojis.put(":snowman_with_snow:", "괸");
		emojis.put(":wind_face:", "괹");
		emojis.put(":dash:", "괺");
		emojis.put(":tornado:", "괻");
		emojis.put(":fog:", "괼");
		emojis.put(":open_umbrella:", "괽");
		emojis.put(":umbrella:", "괾");
		emojis.put(":droplet:", "괿");
	}
	
	/**
	 * Clears the {@link #emojis}, {@link #shortcuts}, and {@link #disabledCharacters} maps.
	 */
	void disable() {
		emojis.clear();
		shortcuts.clear();
		disabledCharacters.clear();
	}
}
