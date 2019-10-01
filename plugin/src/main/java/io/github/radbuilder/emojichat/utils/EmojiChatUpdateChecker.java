package io.github.radbuilder.emojichat.utils;

import io.github.radbuilder.emojichat.EmojiChat;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
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
 * @version 1.8.3
 * @since 1.3
 */
public class EmojiChatUpdateChecker {
	/**
	 * The current version of EmojiChat.
	 */
	public final int currentVersion;
	/**
	 * If an update is available.
	 */
	public boolean updateAvailable;
	/**
	 * The latest version of EmojiChat.
	 */
	public int latestVersion;
	/**
	 * The update checker {@link org.bukkit.scheduler.BukkitTask}, which runs every 4 hours after 10 seconds.
	 */
	private final BukkitTask updateTask;
	
	/**
	 * Creates the EmojiChat update checker class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public EmojiChatUpdateChecker(EmojiChat plugin) {
		String version = plugin.getDescription().getVersion().replaceAll("[^0-9]", "");
		// Make versions 3 characters long for int comparison, i.e. 1.8 -> 180, 1.8.1 -> 181, so 1.9 -> 190 not 19
		currentVersion = Integer.parseInt(version.length() < 3 ? version + "0" : version);
		
		updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::checkForUpdates, 20L * 10L, 20L * 60L * 60L * 4L); // Start checking for updates after 10 seconds, then every 4 hours
	}
	
	/**
	 * Checks if updates are available.
	 */
	private void checkForUpdates() {
		try {
			URL url = new URL("https://api.spiget.org/v2/resources/50955/versions?size=1&sort=-releaseDate");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "EmojiChat Update Checker"); // Sets the user-agent
			
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			JSONArray value = (JSONArray) JSONValue.parseWithException(reader);
			
			String version = ((JSONObject) value.get(value.size() - 1)).get("name").toString().replaceAll("[^0-9]", "");
			// Make versions 3 characters long for int comparison, i.e. 1.8 -> 180, 1.8.1 -> 181, so 1.9 -> 190 not 19
			latestVersion = Integer.parseInt(version.length() < 3 ? version + "0" : version);
			
			updateAvailable = currentVersion < latestVersion;
		} catch (Exception ignored) { // Something happened, not sure what (possibly no internet connection), so no updates available
			updateAvailable = false;
		}
	}
	
	/**
	 * Cancel the {@link #updateTask}.
	 */
	public void cancelUpdateTask() {
		updateTask.cancel();
	}
}