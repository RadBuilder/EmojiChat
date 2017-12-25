package io.github.radbuilder.emojichat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * EmojiChat update checker class.
 *
 * @author RadBuilder
 * @since 1.3
 */
class EmojiChatUpdateChecker {
	/**
	 * The current version of EmojiChat.
	 */
	final double currentVersion;
	/**
	 * If an update is available.
	 */
	boolean updateAvailable;
	/**
	 * The latest version of EmojiChat.
	 */
	double latestVersion;
	
	/**
	 * Creates the EmojiChat update checker class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatUpdateChecker(EmojiChat plugin) {
		currentVersion = Double.parseDouble(plugin.getDescription().getVersion());
	}
	
	/**
	 * Checks if updates are available.
	 */
	void checkForUpdates() {
		try {
			URL url = new URL("https://api.spiget.org/v2/resources/50955/versions?size=1&sort=-id");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "EmojiChat Update Checker"); // Sets the user-agent
			
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			JSONArray value = (JSONArray) JSONValue.parseWithException(reader);
			
			latestVersion = Double.parseDouble(((JSONObject) value.get(value.size() - 1)).get("name").toString());
			
			updateAvailable = currentVersion < latestVersion;
		} catch (Exception ignored) { // Something happened, not sure what (possibly no internet connection), so no updates available
			updateAvailable = false;
		}
	}
}