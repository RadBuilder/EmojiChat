package io.github.radbuilder.emojichat;

import com.google.gson.Gson;

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
	final String currentVersion;
	/**
	 * If an update is available.
	 */
	boolean updatesAvailable;
	/**
	 * The latest version of EmojiChat.
	 */
	String latestVersion;
	
	/**
	 * Creates the EmojiChat update checker class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatUpdateChecker(EmojiChat plugin) {
		currentVersion = plugin.getDescription().getVersion();
	}
	
	void checkForUpdates() {
		try {
			URL url = new URL("https://api.spiget.org/v2/resources/50955/versions?size=1&sort=-id");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "EmojiChat Update Checker"); // Sets the user-agent
			
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			Gson gson = new Gson();
			Version[] version = gson.fromJson(reader, Version[].class);
			latestVersion = version[0].name;
			
			updatesAvailable = !currentVersion.equalsIgnoreCase(latestVersion);
		} catch (Exception ignored) { // Something happened, not sure what (possibly no internet connection), so no updates available
			updatesAvailable = false;
		}
	}
}

/**
 * EmojiChat version class.
 *
 * @author RadBuilder
 * @since 1.3
 */
class Version {
	/**
	 * The name of the version (e.g. 1.1).
	 */
	String name;
}